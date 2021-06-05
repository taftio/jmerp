package com.chaosserver.assertion;

import com.chaosserver.exception.RuntimeExceptionBaseRX;

/**
 * This exception is thrown by the Assertion class.
 * <p>
 * Whenever an assertion has failed, this exception is thrown with a description as to what went wrong.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class AssertionFailedRX extends RuntimeExceptionBaseRX {
    /**
     * Default constructor.
     *
     * @param s A description of why the assertion failed
     */
    public AssertionFailedRX(String s) {
        super(s);
    }
}