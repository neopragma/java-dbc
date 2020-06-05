package com.neopragma.dbc;

import java.lang.reflect.Parameter;

public class BlankRule implements Rule {

    @Override
    public void check(Parameter parameter, Object value) {
        if (parameter.isAnnotationPresent(Blank.class)) {
            if (value != null && !value.equals("")) {
                throw new ConstraintViolationException("Non-blank value when blank/null required");
            }
        }
    }
}
