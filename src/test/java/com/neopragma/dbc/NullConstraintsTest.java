package com.neopragma.dbc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NullConstraintsTest {

    @ParameterizedTest
    @MethodSource("generateValidNumericArguments")
    void given_notnull_constraint_on_numeric_arguments_it_does_not_throw_when_valid_values_are_passed(Number val1, Number val2) {
        add(val1, val2);
    }
    @ParameterizedTest
    @MethodSource("generateValidNumericArguments")
    void given_null_constraint_on_numeric_arguments_it_throws_when_non_null_values_are_passed(Number val1, Number val2) {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> { addNull(val1, val2); });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass());
    }
    private static Stream<Arguments> generateValidNumericArguments() {
        return Stream.of(
                Arguments.of(345.607, 70869.0948374),
                Arguments.of(1.0, -2.5 ),
                Arguments.of(-16.0, 32.0 ));
    }

    @ParameterizedTest
    @MethodSource("generateNullValuesForNumbers")
    void given_notnull_constraint_on_numeric_arguments_it_throws_when_null_reference_is_passed(Number val1, Number val2) {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> { add(val1, val2); });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass());
    }
    private static Stream<Arguments> generateNullValuesForNumbers() {
        return Stream.of(
                Arguments.of(null, 5),
                Arguments.of(17, null),
                Arguments.of(null, null));
    }

    @Test
    void given_null_constraint_on_numeric_arguments_it_does_not_throw_when_null_reference_is_passed() {
        addNull(null, null);
    }

    Number add(@NotNull Number val1, @NotNull Number val2) {
        Constraints.check(val1, val2);
        return val1.doubleValue() + val2.doubleValue();
    }

    Number addNull(@Null Number val1, @Null Number val2) {
        Constraints.check(val1, val2);
        return 0.0;
    }
}
