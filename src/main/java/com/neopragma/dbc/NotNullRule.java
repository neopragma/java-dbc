package com.neopragma.dbc;

import java.lang.reflect.Parameter;

public class NotNullRule implements Rule {

    @Override
    public void check(Parameter parameter, Object value) {
        if (parameter.isAnnotationPresent(NotNull.class)) {
            if (value == null) {
                throw new ConstraintViolationException("Null reference when null not allowed");
            }
        }
    }
}
