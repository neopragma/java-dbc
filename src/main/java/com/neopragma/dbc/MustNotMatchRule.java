package com.neopragma.dbc;

import java.lang.reflect.Parameter;

public class MustNotMatchRule implements Rule {

    @Override
    public void check(Parameter parameter, Object argumentValue) {
        if (parameter.isAnnotationPresent(MustNotMatch.class)) {
            MustNotMatch annotation = parameter.getDeclaredAnnotation(MustNotMatch.class);
            if (argumentValue != null && ((String) argumentValue).matches(annotation.regex())) {
                throw new ConstraintViolationException("String argument matches regex of /"
                    + annotation.regex() + "/");
            }
        }
    }
}
