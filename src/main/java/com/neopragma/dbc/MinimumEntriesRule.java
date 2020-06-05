package com.neopragma.dbc;

import java.lang.reflect.Parameter;
import java.util.Map;

public class MinimumEntriesRule implements Rule {

    @Override
    public void check(Parameter parameter, Object value) {
        if (parameter.isAnnotationPresent(MinimumEntries.class)) {
            MinimumEntries annotation = parameter.getDeclaredAnnotation(MinimumEntries.class);
            if (value.getClass().isArray()) {
                Object[] arr = (Object[]) value;
                if (arr.length < annotation.value()) {
                    throw new ConstraintViolationException(
                            "Array contains fewer than minimum required entries (" + annotation.value() + ")");
                }
            }
        }
    }
}
