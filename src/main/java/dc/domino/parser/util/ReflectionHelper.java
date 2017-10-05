package dc.domino.parser.util;

import dc.domino.parser.annotation.CSVField;
import dc.domino.parser.fieldparsers.FieldParser;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Set of static reflection helper methods.
 * @author Dominik Ciborowski
 */
public class ReflectionHelper {

    //TODO private constructor access?
    public static <T> T newInstance(Class<T> clazz) throws ReflectiveOperationException {
        return clazz.getDeclaredConstructor().newInstance();
    }

    public static List<Field> getCsvAnnotatedFields(Class clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(
                        f -> f.isAnnotationPresent(CSVField.class))
                .collect(Collectors.toList());
    }

    /**
     * Sets value for a field using it setter method.
     * If setting value by setter method succeeds it will return true.
     * If there is no setter method in a class it will return false.
     *
     * @return true - if succeeds, false otherwise
     */
    public static boolean setValueBySetterMethod(Object obj, Field field, Object value) throws ReflectiveOperationException {
        String setterMethodName = createSetterMethodNameForField(field);

        boolean succeeded = true;
        try {
            Method setter = obj.getClass().getMethod(setterMethodName, field.getType());
            setter.invoke(obj, value);

        } catch (NoSuchMethodException e) {
            System.out.println(e);
            succeeded = false;

            //TODO logger
        }

        return succeeded;
    }

    public static void setFieldValueDirectly(Object obj, Field field, Object value) {
        field.setAccessible(true);

        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();

            // Security manager?
            //TODO logger - should never happen
        } finally {
            field.setAccessible(false);
        }
    }

    public static FieldParser<?> getCSVFieldParserInstanceForField(Field field) throws ReflectiveOperationException {
        CSVField csvFieldAnnotation = field.getAnnotation(CSVField.class); //TODO NullPointerException!!!
        Class parserClass = csvFieldAnnotation.parser();

        return (FieldParser<?>) parserClass.getDeclaredConstructor().newInstance(); //TODO factory?
    }

    private static String createSetterMethodNameForField(Field field) {
        String fieldName = field.getName();

        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

}
