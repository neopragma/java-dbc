package com.neopragma.dbc;

import java.lang.reflect.Parameter;

public class MinimumLengthRule implements Rule {

    @Override
    public void check(Parameter parameter, Object argumentValue) {
        if (parameter.isAnnotationPresent(MinimumLength.class)) {
            MinimumLength annotation = parameter.getDeclaredAnnotation(MinimumLength.class);
            if (argumentValue != null && ((String) argumentValue).length() < annotation.value()) {
                throw new ConstraintViolationException("String length less than minimum required length of <"
                    + annotation.value() + ">");
            }
        }
    }
}
