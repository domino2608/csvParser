package dc.domino.parser;

import dc.domino.parser.error.ParserException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVParser {

    //TODO more delims
    private static final String COMMA_DELIM = ",";

    private Map<String, Integer> headerMap = new HashMap<>(); //TODO change it - maybe constructor?

    private File file;

    private int columnNumber;

    public CSVParser(File file) {
        this.file = file;
    }

    /**
     * Parse records from file.
     *
     * @return parsed records from file
     */
    public List<CSVRecord> parseFile() throws IOException {
        List<String> lines = Files.readAllLines(file.toPath()); //TODO one time read of file?
        List<CSVRecord> parsedRecords = new ArrayList<>();

        headerMap = parseHeaders(lines.get(0)); //TODO one time read

        for (int i = 1; i < lines.size(); i++) {
            try {
                parsedRecords.add(parseRecord(lines.get(i)));
            } catch (ParserException e) {
                throw new ParserException(e.getMessage() + "\nOn line: " + (i + 1), e);
            }
        }

        return parsedRecords;
    }

    public <T> List<T> parseFile(Class<T> toClass) throws IOException, ReflectiveOperationException { //TODO proper exception handling
        List<CSVRecord> csvRecords = parseFile();
        List<T> parsedRecords = new ArrayList<>();

        for (CSVRecord record: csvRecords) {
            parsedRecords.add(record.parseToObject(toClass));
        }

        return parsedRecords;
    }

    //TODO make it mutable and able to parse more records from one string
    private CSVRecord parseRecord(String recordString) throws ParserException {
        return new CSVRecord(headerMap, parseRecordLine(recordString));
    }

    private Map<String, Integer> parseHeaders(String headersLine) {
        Map<String, Integer> headerMap = new HashMap<>();

        String[] headers = headersLine.split(COMMA_DELIM);

        for (int i = 0; i < headers.length; i++) {
            headerMap.put(headers[i], i);
            columnNumber++;
        }

        return headerMap;
    }

    private String[] parseRecordLine(String recordLine) throws ParserException {
        String[] recordValues = recordLine.split(COMMA_DELIM);

        if (columnNumber != recordValues.length) {
            throw new ParserException("CSV file structure error. Number of values in record do not match.");
        }

        return recordLine.split(COMMA_DELIM);
    }

}
