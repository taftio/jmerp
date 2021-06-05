package com.chaosserver.exception;

/**
 * Thrown by a method that has yet to be implemented.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class MethodNotImplementedRX extends RuntimeExceptionBaseRX {
    /**
     * Creates a new exception with a descriptor.
     *
     * @param s The description
     */
    public MethodNotImplementedRX(String s) {
        super(s);
    }

    /**
     * Creates a new exception around an old one with description.
     *
     * @param s The description
     * @param e The exception being wrapped
     */
    public MethodNotImplementedRX(String s, Throwable e) {
        super(s, e);
    }
}