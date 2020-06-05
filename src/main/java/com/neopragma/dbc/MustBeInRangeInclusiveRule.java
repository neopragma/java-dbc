package com.neopragma.dbc;

import java.lang.reflect.Parameter;

public class MustBeInRangeInclusiveRule implements Rule {

    @Override
    public void check(Parameter parameter, Object arg) {
        if (parameter.isAnnotationPresent(MustBeInRangeInclusive.class)) {
            MustBeInRangeInclusive annotation = parameter.getDeclaredAnnotation(MustBeInRangeInclusive.class);
            if (arg != null) {
                double argValue = ((Number) arg).doubleValue();
                if ( argValue < annotation.min() || argValue > annotation.max() ) {
                    throw new ConstraintViolationException("Argument value <" + argValue
                            + "> is outside range from <"
                            + annotation.min()
                            + "> to <"
                            + annotation.max() + "> inclusive");
                }
            }
        }
    }
}
