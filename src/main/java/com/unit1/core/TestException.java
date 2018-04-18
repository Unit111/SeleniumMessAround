package com.unit1.core;

/**
 * Created by Unit 1 on 02-Jul-17.
 */
public class TestException extends RuntimeException {

    public TestException(final String string) {
        super(string);
    }

    public TestException(final Throwable cause) {
        super(cause);
    }

    public TestException(final String string, final Throwable cause) {
        super(string, cause);
    }
}
