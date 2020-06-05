package com.neopragma.dbc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringConstraintsTest {

    @ParameterizedTest
    @MethodSource("generateValidStringArguments")
    void given_notblank_constraint_on_string_arguments_it_does_not_throw_when_valid_values_are_passed(String val1, String val2) {
        blend(val1, val2);
    }

    @ParameterizedTest
    @MethodSource("generateValidStringArguments")
    void given_blank_constraint_on_string_arguments_it_throws_when_non_blank_string_is_passed(String val1, String val2) {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> { blendBlank(val1, val2); });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass(), "arguments: " + val1 + ", " + val2);
    }
    private static Stream<Arguments> generateValidStringArguments() {
        return Stream.of(
                Arguments.of("alpha", "beta"),
                Arguments.of("delta", "gamma"),
                Arguments.of("epsilon", "omega"));
    }

    @ParameterizedTest
    @MethodSource("generateBlankValuesForStrings")
    void given_notblank_constraint_on_string_arguments_it_throws_when_null_or_empty_string_is_passed(String val1, String val2) {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> { blend(val1, val2); });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass(), "arguments: " + val1 + ", " + val2);
    }
    @ParameterizedTest
    @MethodSource("generateBlankValuesForStrings")
    void given_blank_constraint_on_string_arguments_it_does_not_throw_when_blank_values_are_passed(String val1, String val2) {
        blendBlank(val1, val2);
    }
    private static Stream<Arguments> generateBlankValuesForStrings() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of("", null),
                Arguments.of(null, ""),
                Arguments.of("", ""));
    }

    String blend(@NotBlank String val1, @NotBlank String val2) {
        Constraints.check(val1, val2);
        return val1 + val2;
    }

    String blendBlank(@Blank String val1, @Blank String val2) {
        Constraints.check(val1, val2);
        return val1 + val2;
    }

    @Test
    void given_a_maximum_allowed_length_it_throws_when_argument_length_exceeds_value() {
        String val1 = "xxxxxxxxxxxxxxxxxxxx";
        String val2 = "x";
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            maximumLengthFail(val1, val2);
        });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass(),
                "arguments: " + val1 + ", " + val2);
    }
    void maximumLengthFail(@MaximumLength(value = 19) String val1, String val2) {
        Constraints.check(val1, val2);
    }

    @Test
    void given_a_minimum_required_length_it_throws_when_argument_length_is_less_than_value() {
        String val1 = "xxxxxxxxxxxxxxxxxxxx";
        String val2 = "x";
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            minimumLengthFail(val1, val2);
        });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass(),
                "arguments: " + val1 + ", " + val2);
    }
    void minimumLengthFail(String val1, @MinimumLength(value = 2) String val2) {
        Constraints.check(val1, val2);
    }

    @ParameterizedTest
    @ValueSource(strings = { "nobody.yahoo.com", "@gmail.com", "notavalidemailaddress" })
    void given_a_regex_it_throws_when_the_argument_value_does_not_match(String emailAddress) {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            regexEmailAddressCheck(emailAddress);
        });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass());
    }
    @ParameterizedTest
    @ValueSource(strings = { "someone@somewhere.com", "one.good.address@mailer.net" })
    void given_a_regex_it_does_not_throw_when_the_argument_value_matches(String emailAddress) {
        regexEmailAddressCheck(emailAddress);
    }
    // Regex for email address found at https://stackoverflow.com/questions/201323/how-to-validate-an-email-address-using-a-regular-expression
    void regexEmailAddressCheck(@MustMatch(regex="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])") String emailAddress) {
        Constraints.check(emailAddress);
    }

    @ParameterizedTest
    @ValueSource(strings = { "someone@somewhere.com", "one.good.address@mailer.net" })
    void given_a_regex_it_throws_when_the_argument_value_matches(String emailAddress) {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            nonMatchingRegexCheck(emailAddress);
        });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass());
    }
    @ParameterizedTest
    @ValueSource(strings = { "nobody.yahoo.com", "@gmail.com", "notavalidemailaddress" })
    void given_a_regex_it_does_not_throw_when_the_argument_value_does_not_match(String emailAddress) {
        nonMatchingRegexCheck(emailAddress);
    }
    void nonMatchingRegexCheck(@MustNotMatch(regex="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])") String emailAddress) {
        Constraints.check(emailAddress);
    }






}
