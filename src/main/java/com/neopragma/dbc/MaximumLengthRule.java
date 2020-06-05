package com.neopragma.dbc;

import java.lang.reflect.Parameter;

public class MaximumLengthRule implements Rule {

    @Override
    public void check(Parameter parameter, Object argumentValue) {
        if (parameter.isAnnotationPresent(MaximumLength.class)) {
            MaximumLength annotation = parameter.getDeclaredAnnotation(MaximumLength.class);
            if (argumentValue != null && ((String) argumentValue).length() > annotation.value()) {
                throw new ConstraintViolationException("String length exceeds maximum allowed length of <"
                    + annotation.value() + ">");
            }
        }
    }
}
