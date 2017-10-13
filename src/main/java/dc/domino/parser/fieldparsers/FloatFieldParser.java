package dc.domino.parser.fieldparsers;

import dc.domino.parser.error.ParserException;

public class FloatFieldParser implements FieldParser<Float> {
    @Override
    public Float parse(String toParse) {
        Float result;

        try {
            result = Float.parseFloat(toParse);
        } catch (NumberFormatException e) {
            throw new ParserException("Wrong input! Expected float and got: " + toParse);
        }

        return result;
    }
}
