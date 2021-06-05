package com.chaosserver.merp.character.stat;

import com.chaosserver.exception.ExceptionBaseX;

/**
 * Two stats are not swappable.
 * <p>
 * An excpetion to be throw if an attempt is made to swap two stats
 * that are no swappable.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class NotSwappableX extends ExceptionBaseX {
    /**
     * Creates a new exception with a descriptor.
     *
     * @param s The description
     */
    public NotSwappableX(String s) {
        super(s);
    }
}