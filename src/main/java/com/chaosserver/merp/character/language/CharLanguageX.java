package com.chaosserver.merp.character.language;

import com.chaosserver.exception.ExceptionBaseX;

/**
 * Abstract package level exception.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public abstract class CharLanguageX extends ExceptionBaseX {
    /**
     * Base constructor.
     *
     * @param s Description
     */
    public CharLanguageX(String s) {
        super(s);
    }

    /**
     * Base constructor.
     *
     * @param s Description
     * @param t Wrapped exception
     */
    public CharLanguageX(String s, Throwable t) {
        super(s,t);
    }
}