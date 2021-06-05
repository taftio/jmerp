package com.chaosserver.exception;

import java.io.StringWriter;
import java.io.PrintWriter;

/**
 * The base Exception class allows for exception wrapping.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public abstract class RuntimeExceptionBaseRX extends RuntimeException {
    /** Wrapped exception. */
    protected Throwable wrappedException;

    /**
     * Creates a new exception with a descriptor.
     *
     * @param s The description
     */
    public RuntimeExceptionBaseRX(String s) {
        super(s);
    }

    /**
     * Creates a new exception around an old one with description.
     *
     * @param s The description
     * @param e The exception being wrapped
     */
    public RuntimeExceptionBaseRX(String s, Throwable e) {
        super(s);
        setWrappedException(e);
    }

    /**
     * Setter for the wrapped exception.
     *
     * @param wrappedException Exception to be wrapped
     * @see #getWrappedException
     */
    public void setWrappedException(Throwable wrappedException) {
        this.wrappedException = wrappedException;
    }

    /**
     * Getter for the wrapped exception.
     *
     * @return The wrapped exception
     * @see #setWrappedException
     */
    public Throwable getWrappedException() {
        return this.wrappedException;
    }

    /**
     * Check if the instance has a wrapped exception.
     *
     * @return Does it have a wrapped exception
     */
    public boolean hasWrappedException() {
        if (getWrappedException() != null) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Recursively converts wrapped exceptions to string
     * with stack trace.
     *
     * @return Description and stack traces of exceptions
     */
    public String toString() {
        StringBuffer sb = new StringBuffer(1000);

        if(hasWrappedException()) {
            sb.append(getWrappedException().toString());
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            getWrappedException().printStackTrace(printWriter);
            sb.append(stringWriter.getBuffer());
        }

        sb.append(super.toString());
        return sb.toString();
    }
}