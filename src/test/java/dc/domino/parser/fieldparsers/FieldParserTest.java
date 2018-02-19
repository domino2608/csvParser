package dc.domino.parser.fieldparsers;

import dc.domino.parser.error.ParserException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FieldParserTest {

    @Test
    public void testStringFieldParser() {
        FieldParser<String> stringFieldParser = new StringFieldParser();

        String toParse = "test";

        String parsed = stringFieldParser.parse(toParse);

        assertEquals(toParse, parsed);
    }

    @Test
    public void testIntegerFieldParser() {
        FieldParser<Integer> integerFieldParser = new IntegerFieldParser();

        String toParse = "12";
        int parsed = integerFieldParser.parse(toParse);

        assertEquals(12, parsed);
    }

    @Test(expected = ParserException.class)
    public void shouldThrowParserExceptionOnWrongIntegerFieldParserInput() {
        new IntegerFieldParser().parse("1a2");
    }

    @Test
    public void testDoubleFieldParser() {
        FieldParser<Double> doubleFieldParser = new DoubleFieldParser();

        String toParse = "12.12";
        double parsed = doubleFieldParser.parse(toParse);

        assertEquals(12.12, parsed, 0);
    }

    @Test(expected = ParserException.class)
    public void shouldThrowParserExceptionOnWrongDoubleFieldParserInput() {
        new DoubleFieldParser().parse("12q12");
    }

}
