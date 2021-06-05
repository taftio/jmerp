package com.chaosserver.merp.rules.stat;

import java.util.Iterator;

import com.chaosserver.exception.MethodNotImplementedRX;

/**
 * Iterator over a list of stats.
 *
 * @author Jordan Reed
 * @version 1.1
 * @since The Beginning
 */
public class StatListIterator {
    /** The internal iterator. */
    private Iterator m_statListIter;

    /**
     * Constructor.
     *
     * @param statListIter The statlist iterator to wrap
     */
    public StatListIterator(Iterator statListIter) {
        m_statListIter = statListIter;
    }

    /**
     * Checks if the iterator has another element.
     *
     * @return if the iterator has another element
     */
    public boolean hasNext() {
        return m_statListIter.hasNext();
    }

    /**
     * Gets the next element from the iterator.
     *
     * @return the next element in the iterator
     */
    public Stat next() {
        return (Stat) m_statListIter.next();
    }
}