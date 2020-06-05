package com.neopragma.dbc;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Repeatable(MustNotContainKeys.class)
public @interface MustNotContainKey {
    String key();
}
