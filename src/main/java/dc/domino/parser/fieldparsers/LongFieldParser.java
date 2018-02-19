package dc.domino.parser.fieldparsers;

import dc.domino.parser.error.ParserException;

public class LongFieldParser implements FieldParser<Long> {
    @Override
    public Long parse(String toParse) {
        Long result;

        try {
            result = Long.parseLong(toParse);
        } catch (NumberFormatException e) {
            throw new ParserException("Wrong input! Expected long and got: " + toParse);
        }

        return result;
    }
}
