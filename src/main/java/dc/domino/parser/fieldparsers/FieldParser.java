package dc.domino.parser.fieldparsers;

public interface FieldParser<T> {

    T parse(String toParse);

}
