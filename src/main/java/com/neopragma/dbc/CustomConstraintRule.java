package com.neopragma.dbc;

import java.lang.reflect.Parameter;

public class CustomConstraintRule implements Rule {

    @Override
    public void check(Parameter parameter, Object arg) {
        if (parameter.isAnnotationPresent(CustomConstraint.class)) {
            CustomConstraint annotation = parameter.getDeclaredAnnotation(CustomConstraint.class);
            String customClassName = annotation.className();
            try {
                Class customClass = Class.forName(customClassName);
                Rule rule = (Rule) customClass.getConstructor().newInstance();
                rule.check(parameter, arg);
            } catch (ConstraintViolationException cve) {
                throw cve;
            } catch (Exception e) {
                throw new RuntimeException("Unable to instantiate custom constraint class " + customClassName, e);
            }
        }
    }
}
