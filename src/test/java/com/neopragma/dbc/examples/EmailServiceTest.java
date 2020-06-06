package com.neopragma.dbc.examples;

import com.neopragma.dbc.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This is the test driver for the sample class, com.neopragma.dbc.examples.EmailService.
 */
public class EmailServiceTest {

    private EmailService emailService;

    @BeforeEach
    void setup_emailServiceExample() {
        emailService = new EmailService();
    }

    @Test
    void first_implementation_returns_success_when_all_fields_in_the_request_are_valid() {
        Map<String, String> response =
                emailService.saveEmailAddress(
                        "Name",
                        "Work",
                        "jsmith@somewhere.com");
        assertEquals("OK", response.get("status"));
    }

    @Test
    void first_implementation_returns_error_status_when_any_field_in_the_request_is_invalid() {
        Map<String, String> response =
                emailService.saveEmailAddress(
                        "Name",
                        "Work",
                        "jsmith@somewhere");
        assertEquals("Service contract violation", response.get("status"));
    }

    @Test
    void second_implementation_returns_success_when_all_fields_in_the_request_are_valid() {
        Map<String, String> response =
                emailService.saveEmailAddress2(
                        "Name",
                        "Work",
                        "jsmith@somewhere.com");
        assertEquals("OK", response.get("status"));
    }

    @Test
    void second_implementation_throws_when_any_field_in_the_request_is_invalid() {
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            emailService.saveEmailAddress2(
                    "Name",
                    "Work",
                    "jsmith@somewhere");
        });
        assertEquals(ConstraintViolationException.class, exception.getCause().getClass());
    }

}
