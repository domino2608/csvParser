package dc.domino.parser.fieldparsers;

import dc.domino.parser.error.ParserException;

public class DoubleFieldParser implements FieldParser<Double> {
    @Override
    public Double parse(String toParse) {
        Double result = null;

        try {
            result = Double.parseDouble(toParse);
        } catch (NumberFormatException e) {
            throw new ParserException("Wrong input! Expected double and got: " + toParse);
        }

        return result;
    }
}
