package dc.domino.parser.util;

import static org.junit.Assert.*;

import dc.domino.parser.fieldparsers.FieldParser;
import dc.domino.parser.fieldparsers.StringFieldParser;
import dc.domino.parsing_classes.TestClass;
import org.junit.Test;

import java.lang.reflect.Field;

public class ReflectionHelperTest {

    private static final String INT_TEST_FIELD_NAME = "intTestField";
    private static final String STRING_TEST_FIELD_NAME = "stringTestField";

    private static final int NEW_TEST_FIELD_VALUE = 2;

    @Test
    public void shouldCreateNewInstance() throws ReflectiveOperationException {
       assertNotNull(ReflectionHelper.newInstance(Object.class));
    }

    @Test
    public void shouldSetValueUsingSetter() throws ReflectiveOperationException {
        TestClass testClass = new TestClass();
        Class clazz = testClass.getClass();
        Field testField = clazz.getDeclaredField(INT_TEST_FIELD_NAME);

        ReflectionHelper.setValueBySetterMethod(testClass, testField, NEW_TEST_FIELD_VALUE);

        assertEquals(NEW_TEST_FIELD_VALUE, testClass.getIntTestField());
    }

    @Test
    public void shouldSetValueDirectly() throws NoSuchFieldException {
        TestClass testClass = new TestClass();
        Class clazz = testClass.getClass();
        Field testField = clazz.getDeclaredField(INT_TEST_FIELD_NAME);

        ReflectionHelper.setFieldValueDirectly(testClass, testField, NEW_TEST_FIELD_VALUE);

        assertEquals(NEW_TEST_FIELD_VALUE, testClass.getIntTestField());
    }

    @Test
    public void shouldCreateCSVFieldParserInstance() throws ReflectiveOperationException {
        Class clazz = TestClass.class;
        Field testField = clazz.getDeclaredField(STRING_TEST_FIELD_NAME);

        FieldParser fieldParser = ReflectionHelper.getCSVFieldParserInstanceForField(testField);

        assertNotNull(fieldParser);
        assertEquals(StringFieldParser.class.getSimpleName(), fieldParser.getClass().getSimpleName());
    }

}
