package com.neopragma.dbc;

import java.lang.reflect.Parameter;

public class MustBeInRangeExclusiveRule implements Rule {

    @Override
    public void check(Parameter parameter, Object arg) {
        if (parameter.isAnnotationPresent(MustBeInRangeExclusive.class)) {
            MustBeInRangeExclusive annotation = parameter.getDeclaredAnnotation(MustBeInRangeExclusive.class);
            if (arg != null) {
                double argValue = ((Number) arg).doubleValue();
                if ( argValue <= annotation.min() || argValue >= annotation.max() ) {
                    throw new ConstraintViolationException("Argument value <" + argValue
                            + "> is outside range from <"
                            + annotation.min()
                            + "> to <"
                            + annotation.max() + "> exclusive");
                }
            }
        }
    }
}
