package dc.domino;

import static org.junit.Assert.*;

import dc.domino.error.ParserException;
import dc.domino.parser.Parser;
import dc.domino.parser.Record;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserTest {

    private static final String FILE_TEXT = "id,name,surname\n" +
            "1,Janek,Kowalski\n" +
            "2,Maciej,Krystianowicz";

    private static final Map<String, Integer> headers = new HashMap<>();

    private static final List<Record> expectedRecords = new ArrayList<>();

    private File tmpFile;

    @Before
    public void setUp() throws IOException {
        String[] records = FILE_TEXT.split("\n");

        headers.put("id", 0);
        headers.put("name", 1);
        headers.put("surname", 2);

        expectedRecords.add(new Record(headers, records[1].split(",")));
        expectedRecords.add(new Record(headers, records[2].split(",")));

        tmpFile = File.createTempFile("test", ".tmp");
        Files.write(tmpFile.toPath(), FILE_TEXT.getBytes());
    }


    @Test
    public void testRightParserInput() throws IOException, ParserException {
        List<Record> parsedRecords = new Parser(tmpFile, true).parseFile();

        assertEquals(expectedRecords, parsedRecords);
    }

}
