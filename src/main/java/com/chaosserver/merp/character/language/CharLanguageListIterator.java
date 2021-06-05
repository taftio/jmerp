package com.chaosserver.merp.character.language;

import java.util.Iterator;

/**
 * Iterator for a CharLanguageList.
 * <p>
 * Type safe iterates over a CharLanguageList or BaseCharLanguageList.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class CharLanguageListIterator {
    /** Internal reference to iterator. */
    protected Iterator charLanguageIterator;

    /**
     * Constructor takes in a language list.
     *
     * @param charLanguageList The language list to iterate over
     */
    public CharLanguageListIterator(CharLanguageList charLanguageList) {
        charLanguageIterator = charLanguageList.getCharLanguageList().iterator();
    }

    /**
     * Gets the next element.
     *
     * @return Next element
     * @see java.util.Iterator#next()
     */
    public CharLanguage next() {
        return (CharLanguage) charLanguageIterator.next();
    }

    /**
     * Checks if there is a next element.
     *
     * @return If there is a next element
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
        return charLanguageIterator.hasNext();
    }

}