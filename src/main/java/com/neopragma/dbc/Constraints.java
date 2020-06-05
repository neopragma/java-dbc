package com.neopragma.dbc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class Constraints {

    private static Class[] constraints = new Class[] {
            NotNullRule.class,
            NullRule.class,
            NotBlankRule.class,
            BlankRule.class,
            MaximumLengthRule.class,
            MinimumLengthRule.class,
            MustMatchRule.class,
            MustNotMatchRule.class,
            MaximumValueRule.class,
            MinimumValueRule.class,
            MustBeInRangeInclusiveRule.class,
            MustBeInRangeExclusiveRule.class,
            MustBeOutsideRangeRule.class,
            MustContainKeyRule.class,
            MustContainKeysRule.class,
            MustNotContainKeyRule.class,
            MustNotContainKeysRule.class,
            EmptyRule.class,
            NotEmptyRule.class,
            MinimumEntriesRule.class,
            CustomConstraintRule.class
    };

    public static void check(Object... args) {
        try {
            String theClassName = new Throwable().getStackTrace()[1].getClassName();
            Class theClass = Class.forName(theClassName);
            String theMethodName = new Throwable().getStackTrace()[1].getMethodName();
            Method[] allMethods = theClass.getDeclaredMethods();
            Method theMethod = null;
            for (int i = 0 ; i < allMethods.length ; i++) {
                if (allMethods[i].getName().equals(theMethodName)) {
                    theMethod = allMethods[i];
                    break;
                }
            }
            Parameter[] parameters = theMethod.getParameters();
            for (int i = 0 ; i < parameters.length ; i++) {
                for (Class constraint : constraints) {
                    ((Rule) constraint.getConstructor().newInstance()).check(parameters[i], args[i]);
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
