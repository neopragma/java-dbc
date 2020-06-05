package com.neopragma.dbc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Map;

public class MustContainKeyRule implements Rule {

    @Override
    public void check(Parameter parameter, Object value) {
        if (parameter.isAnnotationPresent(MustContainKey.class)) {
            MustContainKey annotation = parameter.getDeclaredAnnotation(MustContainKey.class);
            Map<Object, Object> thing = (Map) value;
            if (!thing.containsKey(annotation.key())) {
                throw new ConstraintViolationException("Mandatory key <" + annotation.key() + "> not found in Map");
            }
        }
    }
}
