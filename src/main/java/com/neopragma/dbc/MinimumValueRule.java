package com.neopragma.dbc;

import java.lang.reflect.Parameter;

public class MinimumValueRule implements Rule {

    @Override
    public void check(Parameter parameter, Object argumentValue) {
        if (parameter.isAnnotationPresent(MinimumValue.class)) {
            MinimumValue annotation = parameter.getDeclaredAnnotation(MinimumValue.class);
            if (argumentValue != null && (((Number) argumentValue).doubleValue() < annotation.value())) {
                throw new ConstraintViolationException("Argument value less than minimum required value of <"
                    + annotation.value() + ">");
            }
        }
    }
}
