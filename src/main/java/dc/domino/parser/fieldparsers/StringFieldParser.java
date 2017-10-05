package dc.domino.parser.fieldparsers;

public class StringFieldParser implements FieldParser<String> {
    @Override
    public String parse(String toParse) {
        return toParse;
    }
}
