package com.chaosserver.merp.rules.race;

import java.util.Iterator;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.exception.MethodNotImplementedRX;

/**
 * Iterator over a Race Collection.
 *
 * @author Jordan Reed
 * @version 1.1
 * @since The Beginning
 */
public class RaceListIterator {
    /** Reference to iterator. */
    protected Iterator m_raceListIter;

    /**
     * Constructor.
     *
     * @param raceListIter The iterator to go over
     */
    protected RaceListIterator(Iterator raceListIter) {
        Assertion.isNotNull(raceListIter, "Race list cannot be null");
        m_raceListIter = raceListIter;

    }

    /**
     * Checks if the iterator has another element.
     *
     * @return if the iterator has another element
     */
    public boolean hasNext() {
        return m_raceListIter.hasNext();
    }

    /**
     * Gets the next element from the iterator.
     *
     * @return the next element in the iterator
     */
    public Race next() {
        return (Race) m_raceListIter.next();
    }
}