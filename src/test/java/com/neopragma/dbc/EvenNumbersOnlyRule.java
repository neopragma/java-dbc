package com.neopragma.dbc;

import java.lang.reflect.Parameter;

public class EvenNumbersOnlyRule implements Rule {

    @Override
    public void check(Parameter parameter, Object arg) {
        if ( (int) arg % 2 != 0 ) {
            throw new ConstraintViolationException("Only even numbers are accepted. Input argument was " + (int) arg);
        }
    }
}
