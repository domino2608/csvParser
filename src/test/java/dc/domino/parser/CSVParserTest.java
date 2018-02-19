package dc.domino.parser;

import dc.domino.parser.annotation.CSVField;
import dc.domino.parser.error.ParserException;
import dc.domino.parser.fieldparsers.IntegerFieldParser;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CSVParserTest {

    private static final String FILE_TEXT = "id,name,surname\n" +
            "1,Janek,Kowalski\n" +
            "2,Maciej,Krystianowicz";

    private static final String FILE_TEXT_ERROR = "id,name,surname\n" +
            "1,Janek\n" +
            "2,Maciej,Krystianowicz";

    private static final Map<String, Integer> headers = new HashMap<>();

    private static final List<CSVRecord> EXPECTED_CSV_RECORDS = new ArrayList<>();
    private static final List<ParseToObjectTestClass> EXPECTED_OBJECTS = new ArrayList<>();

    private File tmpFile;

    @BeforeClass
    public static void setUpClass() {
        String[] records = FILE_TEXT.split("\n");

        headers.put("id", 0);
        headers.put("name", 1);
        headers.put("surname", 2);

        EXPECTED_CSV_RECORDS.add(new CSVRecord(headers, records[1].split(",")));
        EXPECTED_CSV_RECORDS.add(new CSVRecord(headers, records[2].split(",")));

        EXPECTED_OBJECTS.add(new ParseToObjectTestClass(1, "Janek", "Kowalski"));
        EXPECTED_OBJECTS.add(new ParseToObjectTestClass(2, "Maciej", "Krystianowicz"));
    }

    @Before
    public void setUp() throws IOException {
        tmpFile = File.createTempFile("test", ".tmp");
        Files.write(tmpFile.toPath(), FILE_TEXT.getBytes());
    }


    @Test
    public void testRightParserInput() throws IOException, ParserException {
        List<CSVRecord> parsedCSVRecords = new CSVParser(tmpFile).parseFile();

        assertEquals(EXPECTED_CSV_RECORDS, parsedCSVRecords);
    }

    @Test
    public void testParseToObject() throws Exception {
        List<ParseToObjectTestClass> parsedRecords = new CSVParser(tmpFile).parseFile(ParseToObjectTestClass.class);

        assertEquals(EXPECTED_OBJECTS, parsedRecords);
    }

    @Test
    public void testParseString() {
        List<CSVRecord> parsedRecords = new CSVParser().parseString(FILE_TEXT);

        assertEquals(EXPECTED_CSV_RECORDS, parsedRecords);
    }

    @Test(expected = IOException.class)
    public void shouldThrowIOExceptionOnFileParse() throws IOException {
        new CSVParser(new File("")).parseFile();
    }

    @Test(expected = ParserException.class)
    public void testParserExceptionOnWrongFileStructure() throws IOException {
        File errorFile = File.createTempFile("err", ".tmp");
        Files.write(errorFile.toPath(), FILE_TEXT_ERROR.getBytes());

        new CSVParser(errorFile).parseFile();
    }

    public static class ParseToObjectTestClass {

        @CSVField(parser = IntegerFieldParser.class)
        private int id;

        @CSVField
        private String name;

        @CSVField
        private String surname;

        public ParseToObjectTestClass() {
        }

        public ParseToObjectTestClass(int id, String name, String surname) {
            this.id = id;
            this.name = name;
            this.surname = surname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            ParseToObjectTestClass that = (ParseToObjectTestClass) o;

            return new EqualsBuilder()
                    .append(id, that.id)
                    .append(name, that.name)
                    .append(surname, that.surname)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(id)
                    .append(name)
                    .append(surname)
                    .toHashCode();
        }
    }

}
