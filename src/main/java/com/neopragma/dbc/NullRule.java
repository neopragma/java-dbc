package com.neopragma.dbc;

import java.lang.reflect.Parameter;

public class NullRule implements Rule {

    @Override
    public void check(Parameter parameter, Object value) {
        if (parameter.isAnnotationPresent(Null.class)) {
            if (value != null) {
                throw new ConstraintViolationException("Non-null reference when null required");
            }
        }
    }
}
