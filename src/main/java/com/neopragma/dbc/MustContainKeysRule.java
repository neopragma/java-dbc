package com.neopragma.dbc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Map;

public class MustContainKeysRule implements Rule {

    @Override
    public void check(Parameter parameter, Object value) {
        if (parameter.isAnnotationPresent(MustContainKeys.class)) {
            MustContainKeys annotation = parameter.getDeclaredAnnotation(MustContainKeys.class);
            Map<Object, Object> thing = (Map) value;
            for (MustContainKey thisKeyPlease : annotation.value()) {
                if (!thing.containsKey(thisKeyPlease.key())) {
                    throw new ConstraintViolationException("Mandatory key <" + thisKeyPlease.key() + " not found in Map");
                }
            }
        }
    }
}
