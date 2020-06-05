package com.neopragma.dbc;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MapConstraintsTest {

    Map<String, String> testMap;

    public MapConstraintsTest() {
        this.testMap = new HashMap();
        testMap.put("key1", "value1");
        testMap.put("key2", "value2");
        testMap.put("key3", "value3");
    }

    @Test
    void given_a_mandatory_key_for_a_map_it_does_not_throw_when_the_key_is_present() {
        processGoodMapWithOneMandatoryKey(testMap);
    }
    private void processGoodMapWithOneMandatoryKey(@MustContainKey(key="key3") Map<String, String> testMap) {
        Constraints.check(testMap);
    }

    @Test
    void given_a_mandatory_key_for_a_map_it_throws_when_the_key_is_not_present() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            processMapWithOneMandatoryKey(testMap);
        });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass());
    }
    private void processMapWithOneMandatoryKey(@MustContainKey(key="blork") Map<String, String> testMap) {
        Constraints.check(testMap);
    }

    @Test
    void given_three_mandatory_keys_for_a_map_it_throws_when_a_key_is_not_present() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            processMapWithThreeMandatoryKeys(testMap);
        });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass());
    }
    private void processMapWithThreeMandatoryKeys(
            @MustContainKey(key="blork")
            @MustContainKey(key="key2")
            @MustContainKey(key="key3") Map<String, String> testMap) {
        Constraints.check(testMap);
    }

    @Test
    void given_a_prohibited_key_for_a_map_it_throws_when_the_key_is_present() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            processMapWithOneProhibitedKey(testMap);
        });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass());
    }
    private void processMapWithOneProhibitedKey(@MustNotContainKey(key="key2") Map<String, String> testMap) {
        Constraints.check(testMap);
    }

    @Test
    void given_three_prohibited_keys_for_a_map_it_throws_when_any_key_is_present() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            processMapWithThreeProhibitedKeys(testMap);
        });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass());
    }
    private void processMapWithThreeProhibitedKeys(
            @MustNotContainKey(key="blork")
            @MustNotContainKey(key="key2")
            @MustNotContainKey(key="spleen") Map<String, String> testMap) {
        Constraints.check(testMap);
    }

    @Test
    void given_a_mixture_of_mandatory_and_prohibited_keys_it_throws_appropriately() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            processMapWithMandatoryAndProhibitedKeys(testMap);
        });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass());
    }
    private void processMapWithMandatoryAndProhibitedKeys(
            @MustNotContainKey(key="blork")
            @MustContainKey(key="key2")
            @MustNotContainKey(key="key1") Map<String, String> testMap) {
        Constraints.check(testMap);
    }

}
