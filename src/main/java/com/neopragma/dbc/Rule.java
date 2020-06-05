package com.neopragma.dbc;

import java.lang.reflect.Parameter;

public interface Rule {
    void check(Parameter parameter, Object value);
}
