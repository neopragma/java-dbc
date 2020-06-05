package com.neopragma.dbc;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface MinimumEntries {
    int value();
}
