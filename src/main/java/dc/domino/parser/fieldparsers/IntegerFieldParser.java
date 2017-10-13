package dc.domino.parser.fieldparsers;

import dc.domino.parser.error.ParserException;

public class IntegerFieldParser implements FieldParser<Integer> {
    @Override
    public Integer parse(String toParse) {
        Integer result;

        try {
            result = Integer.parseInt(toParse);
        } catch (NumberFormatException e) {
            throw new ParserException("Wrong input! Expected integer and got: " + toParse);
        }

        return result;
    }
}
