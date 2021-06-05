package com.chaosserver.merp.dice;

import com.chaosserver.exception.RuntimeExceptionBaseRX;

/**
 * Indicates that a value was pulled off of a die before it was ever rolled.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class DieNotRolledRX extends RuntimeExceptionBaseRX {
    /**
     * Creates a new exception with a descriptor.
     *
     * @param s The description
     */
    public DieNotRolledRX(String s) {
        super(s);
    }

}