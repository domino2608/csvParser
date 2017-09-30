package dc.domino.parser;

import dc.domino.error.ParserException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

    //TODO more delims
    private static final String COMMA_DELIM = ",";

    private Map<String, Integer> headerMap = new HashMap<>(); //TODO change it - maybe constructor?

    private File file;

    private boolean parseHeaders = true; //TODO make it work also without headers

    private int columnNumber;

    public Parser(File file, boolean parseHeaders) {
        this.file = file;
        this.parseHeaders = parseHeaders;
    }

    /**
     * Parse records from file.
     *
     * @return parsed records from file
     */
    public List<Record> parseFile() throws IOException, ParserException {
        List<String> lines = Files.readAllLines(file.toPath());
        List<Record> records = new ArrayList<>();

        if (parseHeaders) {
            headerMap = parseHeaders(lines.get(0));

            for (int i = 1; i < lines.size(); i++) {
                try {
                    records.add(parseRecord(lines.get(i)));
                } catch (ParserException e) {
                    throw new ParserException(e.getMessage() + "\nOn line: " + (i+1), e);
                }
            }
        }

        return records;
    }

    //TODO make it mutable and able to parse more records from one string
    private Record parseRecord(String recordString) throws ParserException {
        return new Record(headerMap, parseRecordLine(recordString));
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
