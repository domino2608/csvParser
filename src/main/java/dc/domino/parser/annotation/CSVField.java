package dc.domino.parser.annotation;

import dc.domino.parser.fieldparsers.FieldParser;
import dc.domino.parser.fieldparsers.StringFieldParser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CSVField {
    Class<? extends FieldParser> parser() default StringFieldParser.class;

//    int columnNo() default -1;
}
