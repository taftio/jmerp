package com.chaosserver.merp.rules.restriction;

import java.util.Iterator;

import com.chaosserver.exception.MethodNotImplementedRX;

/**
 * Iterator over a list of restrictions.
 *
 * @author Jordan Reed
 * @version 1.1
 * @since The Beginning
 */
public class RestrictionListIterator {
    /** Reference to iterator */
    private Iterator m_resListIter;

    /**
     * Constructor.
     *
     * @param rl The restriction list to iterate over
     */
    RestrictionListIterator(RestrictionList rl) {
        m_resListIter = rl.getRestrictionList().iterator();
    }

    /**
     * Checks if the iterator has another element.
     *
     * @return if the iterator has another element
     */
    public boolean hasNext() {
        return m_resListIter.hasNext();
    }

    /**
     * Gets the next element from the iterator.
     *
     * @return the next element in the iterator
     */
    public Object next() {
        return m_resListIter.next();
    }
}