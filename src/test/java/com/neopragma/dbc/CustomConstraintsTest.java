package com.neopragma.dbc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomConstraintsTest {

    @Test
    void given_a_custom_constraint_rule_it_does_not_throw_when_the_rule_succeeds() {
        assertEquals(176, add(44, 132));
    }

    @Test
    void given_a_custom_constraint_rule_it_throws_when_the_rule_fails() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            add(111, 95);
        });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass());
    }

    int add(@CustomConstraint(className="com.neopragma.dbc.EvenNumbersOnlyRule") int val1, int val2) {
        Constraints.check(val1);
        return val1 + val2;
    }
}
