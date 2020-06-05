package com.neopragma.dbc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NumberConstraintsTest {

    @ParameterizedTest
    @MethodSource("generateNumbersUpTo100")
    void given_maximum_allowed_value_it_does_not_throw_when_number_is_at_the_maximum_limit(Number arg) {
        checkNumbersUpTo100(arg);
    }
    @ParameterizedTest
    @MethodSource("generateNumbersOver100")
    void given_maximum_allowed_value_it_throws_when_number_exceeds_the_maximum_limit(Number arg) {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            checkNumbersUpTo100(arg);
        });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass(), "argument: " + arg);
    }
    void checkNumbersUpTo100(@MaximumValue(value=100) Number arg) {
        Constraints.check(arg);
    }

    @ParameterizedTest
    @MethodSource("generateNumbersUpTo100")
    void given_minimum_allowed_value_it_does_not_throw_when_number_is_at_the_minimum_threshold(Number arg) {
        checkNumbersUpTo100_MinimumValue(arg);
    }
    void checkNumbersUpTo100_MinimumValue(@MinimumValue(value=Integer.MIN_VALUE) Number arg) {
        Constraints.check(arg);
    }

    @ParameterizedTest
    @MethodSource("generateNumbersUnder100")
    void given_minimum_allowed_value_it_throws_when_number_is_below_the_minimum_threshold(Number arg) {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            checkNumbersUnder100_MinimumValue(arg);
        });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass(), "argument: " + arg);
    }
    void checkNumbersUnder100_MinimumValue(@MinimumValue(value=100) Number arg) {
        Constraints.check(arg);
    }
    private static Stream<Arguments> generateNumbersUpTo100() {
        return Stream.of(
                Arguments.of(100),
                Arguments.of(99),
                Arguments.of(99.99),
                Arguments.of(0),
                Arguments.of(Integer.MIN_VALUE));
    }
    private static Stream<Arguments> generateNumbersOver100() {
        return Stream.of(
                Arguments.of(101),
                Arguments.of(3457.874),
                Arguments.of(Integer.MAX_VALUE));
    }
    private static Stream<Arguments> generateNumbersUnder100() {
        return Stream.of(
                Arguments.of(99.99),
                Arguments.of(0),
                Arguments.of(Integer.MIN_VALUE));
    }

    @Test
    void given_inclusive_range_it_does_not_throw_when_number_is_within_the_range() {
        checkInclusiveRange(100);
    }
    @Test
    void given_inclusive_range_it_throws_when_number_is_below_the_range() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            checkInclusiveRange(98);
        });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass());
    }
    @Test
    void given_inclusive_range_it_throws_when_number_is_above_the_range() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            checkInclusiveRange(102);
        });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass());
    }
    void checkInclusiveRange(@MustBeInRangeInclusive(min=99, max=101) Number arg) {
        Constraints.check(arg);
    }

    @Test
    void given_exclusive_range_it_does_not_throw_when_number_is_within_the_range() {
        checkExclusiveRange(100);
    }
    @Test
    void given_exclusive_range_it_throws_when_number_is_below_the_range() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            checkExclusiveRange(98);
        });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass());
    }
    @Test
    void given_exclusive_range_it_throws_when_number_is_above_the_range() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            checkExclusiveRange(102);
        });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass());
    }
    void checkExclusiveRange(@MustBeInRangeExclusive(min=99, max=101) Number arg) {
        Constraints.check(arg);
    }


    @Test
    void given_value_must_be_outside_range_it_does_not_throw_when_number_is_below_the_range() {
        checkOutsideRange(98);
    }
    @Test
    void given_value_must_be_outside_range_it_does_not_throw_when_number_is_above_the_range() {
        checkOutsideRange(102);
    }
    @Test
    void given_value_must_be_outside_range_it_throws_when_number_is_within_the_range() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            checkOutsideRange(100);
        });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass());
    }
    void checkOutsideRange(@MustBeOutsideRange(min=99, max=101) Number arg) {
        Constraints.check(arg);
    }


}
