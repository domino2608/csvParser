package dc.domino.parser;

import dc.domino.error.ParserException;

import java.util.Arrays;
import java.util.Map;

public class Record/*<T>*/ {

    private final Map<String, Integer> headersMappings;

    private String[] recordValues;

    public Record(final Map<String, Integer> headersMappings, String[] recordValues) {
        this.headersMappings = headersMappings;
        this.recordValues = recordValues;
    }

    public <T> Object getParsedObject(Class<T> clazz) throws ParserException {



        return null;
    }

    public String[] getRecordValues() {
        return recordValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(headersMappings, record.headersMappings)
                .append(recordValues, record.recordValues)
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
        return "Record{" +
                "recordValues=" + Arrays.toString(recordValues) +
                '}';
    }
}
