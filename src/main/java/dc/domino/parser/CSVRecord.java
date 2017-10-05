package dc.domino.parser;

import dc.domino.parser.fieldparsers.FieldParser;
import dc.domino.parser.util.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CSVRecord {

    private final Map<String, Integer> headersMappings;

    private String[] recordValues;

    public CSVRecord(final Map<String, Integer> headersMappings, String[] recordValues) {
        this.headersMappings = headersMappings;
        this.recordValues = recordValues;
    }

//    private List<T> parseToObjects(Class<T> clazz) throws ReflectiveOperationException {
//        List<T> parsedObjects = new ArrayList<>();
//
//        for (int i = 0; i < recordValues.length; i++) {
//            parsedObjects.add(parseToObject(clazz, csvFields, i));
//        }
//
//        return parsedObjects;
//    }

    public <T> T parseToObject(Class<T> clazz) throws ReflectiveOperationException {

        List<Field> csvFields = ReflectionHelper.getCsvAnnotatedFields(clazz);
        T obj = ReflectionHelper.newInstance(clazz);

        for (Field f : csvFields) {
            FieldParser fieldParser = ReflectionHelper.getCSVFieldParserInstanceForField(f);
            Object value = fieldParser.parse(recordValues[getIndexForField(f)]);

            if (!ReflectionHelper.setValueBySetterMethod(obj, f, value)) {
                ReflectionHelper.setFieldValueDirectly(obj, f, value);
            }
        }

        return obj;
    }

    private int getIndexForField(Field field) {
        return headersMappings.get(field.getName());//TODO exception if not found
    }

    public String[] getRecordValues() {
        return recordValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CSVRecord CSVRecord = (CSVRecord) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(headersMappings, CSVRecord.headersMappings)
                .append(recordValues, CSVRecord.recordValues)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(headersMappings)
                .append(recordValues)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "CSVRecord{" +
                "recordValues=" + Arrays.toString(recordValues) +
                '}';
    }
}
