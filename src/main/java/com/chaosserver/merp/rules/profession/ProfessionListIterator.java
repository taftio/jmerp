package com.chaosserver.merp.rules.profession;

import java.util.Iterator;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.exception.MethodNotImplementedRX;

/**
 * Iterates over a collection of professions.
 *
 * @author Jordan Reed
 * @version 1.1
 * @since The Beginning
 */
public class ProfessionListIterator {
    /** Reference to the iterator. */
    protected Iterator m_professionListIter;

    /**
     * Constructor.
     *
     * @param professionList The collection of professions to iterate over
     */
    protected ProfessionListIterator(ProfessionList professionList) {
        Assertion.isNotNull(professionList, "Profession list cannot be null");
        m_professionListIter = professionList.getProfList().iterator();
    }

    /**
     * Checks if the iterator has another element.
     *
     * @return if the iterator has another element
     */
    public boolean hasNext() {
        return m_professionListIter.hasNext();
    }

    /**
     * Gets the next element from the iterator.
     *
     * @return the next element in the iterator
     */
    public Profession next() {
        return (Profession) m_professionListIter.next();
    }

}