package com.neopragma.dbc;

import java.lang.reflect.Parameter;

public class MaximumValueRule implements Rule {

    @Override
    public void check(Parameter parameter, Object argumentValue) {
        if (parameter.isAnnotationPresent(MaximumValue.class)) {
            MaximumValue annotation = parameter.getDeclaredAnnotation(MaximumValue.class);
            if (argumentValue != null && (((Number) argumentValue).doubleValue() > annotation.value())) {
                throw new ConstraintViolationException("Argument value greater than maximum allowed value of <"
                    + annotation.value() + ">");
            }
        }
    }
}
