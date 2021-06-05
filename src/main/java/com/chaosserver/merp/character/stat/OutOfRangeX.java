package com.chaosserver.merp.character.stat;

import com.chaosserver.exception.ExceptionBaseX;

/**
 * This stat cannot be adjusted by the amount requested.
 *
 * This should be throw when an attempt is made to adjust a stat by
 * a value that is more than is allowed.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class OutOfRangeX extends ExceptionBaseX {
    /**
     * Creates a new exception with a descriptor.
     *
     * @param s The description
     */
    public OutOfRangeX(String s) {
        super(s);
    }
}