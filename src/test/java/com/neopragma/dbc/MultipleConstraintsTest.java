package com.neopragma.dbc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MultipleConstraintsTest {

    @Test
    void given_valid_arguments_of_various_types_it_does_not_throw() {
        Map<String, String> testMap = new HashMap();
        testMap.put("key1", "value1");
        processMultipleConstraints(
                "Happy",
                testMap,
                55,
                14.89
        );
    }
    void processMultipleConstraints(
            @NotNull @MinimumLength(value=5) @MaximumLength(value=20) @MustMatch(regex="^[a-zA-Z]+$") String someString,
            @NotNull @NotEmpty @MustContainKey(key="key1") @MustNotContainKey(key="key2") Map<String, String> testMap,
            @NotNull @MustBeInRangeExclusive(min=50, max=60) Integer someNumber,
            @NotNull @MinimumValue(value=10) Double someDouble
    ) {
        Constraints.check(someString, testMap, someNumber, someDouble);
    }
}
