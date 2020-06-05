package com.neopragma.dbc;

import java.lang.reflect.Parameter;
import java.util.Map;

public class MustNotContainKeysRule implements Rule {

    @Override
    public void check(Parameter parameter, Object value) {
        if (parameter.isAnnotationPresent(MustNotContainKeys.class)) {
            MustNotContainKeys annotation = parameter.getDeclaredAnnotation(MustNotContainKeys.class);
            Map<Object, Object> thing = (Map) value;
            for (MustNotContainKey notThisKeyPlease : annotation.value()) {
                if (thing.containsKey(notThisKeyPlease.key())) {
                    throw new ConstraintViolationException("Prohibited key <" + notThisKeyPlease.key() + " found in Map");
                }
            }
        }
    }
}
