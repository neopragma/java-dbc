package com.neopragma.dbc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Map;

public class MustNotContainKeyRule implements Rule {

    @Override
    public void check(Parameter parameter, Object value) {
        if (parameter.isAnnotationPresent(MustNotContainKey.class)) {
            MustNotContainKey annotation = parameter.getDeclaredAnnotation(MustNotContainKey.class);
            Map<Object, Object> thing = (Map) value;
            if (thing.containsKey(annotation.key())) {
                throw new ConstraintViolationException("Prohibited key <" + annotation.key() + "> found in Map");
            }
        }
    }
}
