package com.neopragma.dbc;

import java.lang.reflect.Array;
import java.lang.reflect.Parameter;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;

/**
 * The concept of "empty" applies to Collections and Arrays.
 */
public class NotEmptyRule implements Rule {

    @Override
    public void check(Parameter parameter, Object value) {
        if (parameter.isAnnotationPresent(NotEmpty.class)) {
            Collection collection = null;
            if (Collection.class.isAssignableFrom(value.getClass())) {
                collection = (Collection) value;
            } else if (AbstractMap.class.isAssignableFrom(value.getClass())) {
                collection = ((AbstractMap) value).keySet();
            }
            if (collection.isEmpty()) {
                throw new ConstraintViolationException("Empty collection when collection is required to be non-empty");
            }
        }
    }

}
