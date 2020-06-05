package com.neopragma.dbc;

import java.lang.reflect.Parameter;

public class NotBlankRule implements Rule {

    @Override
    public void check(Parameter parameter, Object value) {
        if (parameter.isAnnotationPresent(NotBlank.class)) {
            if (value == null || value.equals("")) {
                throw new ConstraintViolationException("Blank/null value when blank/null not allowed");
            }
        }
    }
}
