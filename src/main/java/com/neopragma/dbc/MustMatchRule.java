package com.neopragma.dbc;

import java.lang.reflect.Parameter;

public class MustMatchRule implements Rule {

    @Override
    public void check(Parameter parameter, Object argumentValue) {
        if (parameter.isAnnotationPresent(MustMatch.class)) {
            MustMatch annotation = parameter.getDeclaredAnnotation(MustMatch.class);
            if (argumentValue != null && !((String) argumentValue).matches(annotation.regex())) {
                throw new ConstraintViolationException("String argument does not match regex of /"
                    + annotation.regex() + "/");
            }
        }
    }
}
