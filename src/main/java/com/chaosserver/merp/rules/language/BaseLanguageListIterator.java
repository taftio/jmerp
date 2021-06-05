package com.chaosserver.merp.rules.language;

import java.util.Iterator;

/**
 * Iterates over base languages.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class BaseLanguageListIterator {
    /** Iterator over the wrapped collection. */
    protected Iterator languageIterator;

    /**
     * Base constructor takes in the list to iterate over.
     *
     * @param baseLanguageList The list to iterate over
     */
    public BaseLanguageListIterator(BaseLanguageList baseLanguageList) {
        languageIterator = baseLanguageList.getLanguages().iterator();
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
    public BaseLanguageValue next() {
        return (BaseLanguageValue) languageIterator.next();
    }
}