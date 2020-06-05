package com.neopragma.dbc;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Repeatable(MustContainKeys.class)
public @interface MustContainKey {
    String key();
}
