package com.chaosserver.merp.rules.language;

import java.util.Iterator;

/**
 * Iterates over a language list.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class LanguageListIterator {
    /** Internal iterator over the collection. */
    protected Iterator languageIterator;

    /**
     * Constructor takes in the language list.
     *
     * @param languageList The list to iterate over
     */
    public LanguageListIterator(LanguageList languageList) {
        languageIterator = languageList.getLanguages().iterator();
    }

    /**
     * Determines if there is a next element in the list.
     *
     * @return If there is a next
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
        return languageIterator.hasNext();
    }

    /**
     * Gets the next value of the collection.
     *
     * @return The next value
     * @see java.util.Iterator#next()
     */
    public Language next() {
        return (Language) languageIterator.next();
    }
}