package dc.domino.parser;

import dc.domino.parser.error.ParserException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVParser {

    //TODO more delims
    private static final String COMMA_DELIM = ",";

    private File file;

    private int columnNumber;

    /**
     * Creates new CSVParser with empty file - for example to parse strings.
     */
    public CSVParser() {
    }

    /**
     * Creates new CSVParser with file to parse from.
     * @param file
     */
    public CSVParser(File file) {
        this.file = file;
    }

    /**
     * Parse records from file.
     *
     * @return parsed records from file
     */
    public List<CSVRecord> parseFile() throws IOException {
        String fileContent = new String(Files.readAllBytes(file.toPath()));
        List<CSVRecord> parsedRecords = parseString(fileContent);

        return parsedRecords;
    }

    public <T> List<T> parseFile(Class<T> toClass) throws IOException {
        String fileContent = new String(Files.readAllBytes(file.toPath()));
        List<T> parsedRecords = parseString(fileContent, toClass);

        return parsedRecords;
    }

    public List<CSVRecord> parseString(String stringToParse) {
        List<String> lines = Arrays.asList(stringToParse.split("\n"));
        List<CSVRecord> parsedRecords = new ArrayList<>();

        Map<String, Integer> headerMap = parseHeaders(lines.get(0));

        for (int i = 1; i < lines.size(); i++) {
            try {
                parsedRecords.add(parseRecord(headerMap, lines.get(i)));
            } catch (ParserException e) {
                throw new ParserException(e.getMessage() + "\nOn line: " + (i + 1), e);
            }
        }

        return parsedRecords;
    }

    public <T> List<T> parseString(String stringToParse, Class<T> toClass) {
        List<CSVRecord> csvRecords = parseString(stringToParse);
        List<T> parsedRecords = new ArrayList<>();

        for (CSVRecord record : csvRecords) {
            parsedRecords.add(record.parseToObject(toClass));
        }

        return parsedRecords;
    }

    private CSVRecord parseRecord(Map<String, Integer> headerMap, String recordString) throws ParserException {
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

    public void setFile(File file) {
        this.file = file;
    }
}
