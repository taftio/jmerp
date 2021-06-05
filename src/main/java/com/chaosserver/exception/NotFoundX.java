package com.chaosserver.exception;

/**
 * This class is thrown by finders when the value is not found.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class NotFoundX extends ExceptionBaseX {
    /**
     * Creates a new exception with a descriptor.
     *
     * @param s The description
     */
    public NotFoundX(String s) {
        super(s);
    }

    /**
     * Creates a new exception around an old one with description.
     *
     * @param s The description
     * @param e The exception being wrapped
     */
    public NotFoundX(String s, Throwable e) {
        super(s, e);
    }
}