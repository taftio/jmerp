package com.chaosserver.exception;

/**
 * Exception thrown when there is an error loading a object.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class LoadErrorRX extends RuntimeExceptionBaseRX {
    /**
     * Creates a new exception with a descriptor.
     *
     * @param s The description
     */
    public LoadErrorRX(String s) {
        super(s);
    }

    /**
     * Creates a new exception around an old one with description.
     *
     * @param s The description
     * @param e The exception being wrapped
     */
    public LoadErrorRX(String s, Throwable e) {
        super(s, e);
    }
}