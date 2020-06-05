package com.neopragma.dbc;

import java.lang.reflect.Parameter;

public class MustBeOutsideRangeRule implements Rule {

    @Override
    public void check(Parameter parameter, Object arg) {
        if (parameter.isAnnotationPresent(MustBeOutsideRange.class)) {
            MustBeOutsideRange annotation = parameter.getDeclaredAnnotation(MustBeOutsideRange.class);
            if (arg != null) {
                double argValue = ((Number) arg).doubleValue();
                if ( argValue >= annotation.min() && argValue <= annotation.max() ) {
                    throw new ConstraintViolationException("Argument value <" + argValue
                            + "> is within excluded range from <"
                            + annotation.min()
                            + "> to <"
                            + annotation.max() + ">");
                }
            }
        }
    }
}
