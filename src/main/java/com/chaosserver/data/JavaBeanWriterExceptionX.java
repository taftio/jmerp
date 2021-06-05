package com.chaosserver.data;

import com.chaosserver.exception.ExceptionBaseX;

/**
 * This exception is thrown indicating that there was an error
 * writing a JavaBean to an XML object.
 * <p>
 * This object is usually used to wrap another exception that
 * was thrown by one of the underlying objects.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
 public class JavaBeanWriterExceptionX extends ExceptionBaseX {
    /**
     * Creates a new exception with a descriptor.
     *
     * @param s The description
     */
     public JavaBeanWriterExceptionX(String s) {
        super(s);
    }

    /**
     * Creates a new exception around an old one with description.
     *
     * @param s The description
     * @param t The exception being wrapped
     */
    public JavaBeanWriterExceptionX(String s, Throwable t) {
        super(s,t);
    }
}