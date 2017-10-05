package dc.domino.parsing_classes;

import dc.domino.parser.annotation.CSVField;
import dc.domino.parser.fieldparsers.IntegerFieldParser;

public class TestClass {

    @CSVField(parser = IntegerFieldParser.class)
    private int intTestField;

    @CSVField
    private String stringTestField;

    public int getIntTestField() {
        return intTestField;
    }

    public void setIntTestField(int intTestField) {
        this.intTestField = intTestField;
    }

    public String getStringTestField() {
        return stringTestField;
    }

    public void setStringTestField(String stringTestField) {
        this.stringTestField = stringTestField;
    }
}
