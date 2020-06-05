package com.neopragma.dbc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CollectionConstraintsTest {

    @Test
    void given_a_map_must_be_empty_it_throws_when_the_map_is_not_empty() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            Map<String, String> testMap = new HashMap();
            testMap.put("key1", "value1");
            processNonEmptyMap(testMap);
        });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass());
    }
    void processNonEmptyMap(@Empty Map<String, String> testMap) {
        Constraints.check(testMap);
    }

    @Test
    void given_a_map_must_be_empty_it_does_not_throw_when_the_map_is_empty() {
        processValidEmptyMap(new HashMap());
    }
    void processValidEmptyMap(@Empty Map<String, String> testMap) {
        Constraints.check(testMap);
    }

    @Test
    void given_a_map_must_be_empty_it_throws_NPE_when_map_is_null() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            processNonEmptyMap(null);
        });
        assertEquals(NullPointerException.class, exception.getCause().getClass());
    }
    void processNullMap(@Empty Map<String, String> testMap) {
        Constraints.check(testMap);
    }

    @Test
    void given_a_map_must_not_be_empty_it_throws_when_the_map_is_empty() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            processEmptyMap(new HashMap());
        });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass());
    }
    void processEmptyMap(@NotEmpty Map<String, String> testMap) {
        Constraints.check(testMap);
    }

    @Test
    void given_a_map_must_not_be_empty_it_does_not_throw_when_the_map_is_not_empty() {
        Map<String, String> testMap = new HashMap();
        testMap.put("key1", "value1");
        processValidNonEmptyMap(testMap);
    }
    void processValidNonEmptyMap(@NotEmpty Map<String, String> testMap) {
        Constraints.check(testMap);
    }

    @Test
    void given_a_list_must_not_be_empty_it_throws_when_the_list_is_empty() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            processEmptyList(new ArrayList());
        });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass());
    }
    void processEmptyList(@NotEmpty List<String> testList) {
        Constraints.check(testList);
    }

    @Test
    void given_a_set_must_not_be_empty_it_throws_when_the_set_is_empty() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            processEmptySet(new HashSet());
        });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass());
    }
    void processEmptySet(@NotEmpty Set<String> testSet) {
        Constraints.check(testSet);
    }

    @Test
    void given_a_queue_must_not_be_empty_it_throws_when_the_queue_is_empty() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            processEmptyQueue(new LinkedList());
        });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass());
    }
    void processEmptyQueue(@NotEmpty Queue<String> testQueue) {
        Constraints.check(testQueue);
    }

}
