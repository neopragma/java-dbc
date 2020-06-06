package com.neopragma.dbc.examples;

import com.neopragma.dbc.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Example of using the Java DbC annotations to enforce a contract on input data.
 *
 * This is a make-believe method on a make-believe service that stores a person's
 * name, email address, and category of email address in a make-believe database.
 * Such a method might be invoked from a RESTful service wrapper or called directly
 * by client code written in any JVM language.
 *
 * This file contains two examples of the email service. The reason is that there are
 * a couple of common ways services are designed.
 *
 * First, a service might be "right-sized"  * or part of a "monolith-in-the-cloud"
 * solution. You probably don't want to restart server instances as frequently with
 * this sort of solution as you would for a microservice. The first example demonstrates
 * how you might use the Constraints class in that sort of service. It calls Constraints
 * in a try/catch block and doesn't let the server die if a client submits an input
 * value that violates the contract.
 *
 * Second, a service might be a "microservice", sometimes called "serverless" or
 * "function-as-a-service" (FaaS). In this case, it's normal for server instances to
 * be destroyed and created frequently; in the ultimate case, a new instance is created
 * for each request. The second example shows how you might use the Constraints class
 * in a microservice. It allows the ConstraintViolationException to kill the instance.
 * The advantage of this strategy is that it denies usable information about weaknesses
 * in the API to any interested hacker. A downside is it can enable DDOS attacks unless
 * you use the facilities offered by the cloud provider to mitigate that risk.
 *
 * Try this with the EmailServiceTest class in the test source directory tree.
 */
public class EmailService {

    private static final String STATUS_KEY = "status";
    private static final int MAXIMUM_INPUT_STRING_LENGTH_ALLOWED = 256;
    private static final String EMAIL_ADDRESS_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    public Map<String, String> saveEmailAddress(
            @NotNull @MaximumLength(value = MAXIMUM_INPUT_STRING_LENGTH_ALLOWED) String name,
            @NotNull @MaximumLength(value = MAXIMUM_INPUT_STRING_LENGTH_ALLOWED) String category,
            @NotNull @MustMatch(regex = EMAIL_ADDRESS_PATTERN) String emailAddress) {
        Map<String, String> responseData = new HashMap();
        try {
            Constraints.check(name, category, emailAddress);
            // perform application functionality here
            responseData.put(STATUS_KEY, "OK");
        } catch (RuntimeException rte) {
            if (rte.getCause().getClass().getSimpleName().equals("ConstraintViolationException")) {
                responseData.put(STATUS_KEY, "Service contract violation");
            } else {
                responseData.put(STATUS_KEY, "Internal error");
            }
        } catch (Exception e) {
            responseData.put(STATUS_KEY, "Internal error");
        } finally {
            return responseData;
        }
    }

    public Map<String, String> saveEmailAddress2(
            @NotNull @MaximumLength(value = MAXIMUM_INPUT_STRING_LENGTH_ALLOWED) String name,
            @NotNull @MaximumLength(value = MAXIMUM_INPUT_STRING_LENGTH_ALLOWED) String category,
            @NotNull @MustMatch(regex = EMAIL_ADDRESS_PATTERN) String emailAddress) {
        Map<String, String> responseData = new HashMap();
        Constraints.check(name, category, emailAddress);
        // perform application functionality here
        responseData.put(STATUS_KEY, "OK");
        return responseData;
    }
}
