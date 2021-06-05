package com.chaosserver.merp.rules.magic.realm;

import java.util.Iterator;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.exception.MethodNotImplementedRX;

/**
 * Iterator over the realm list.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class RealmListIterator {
    /** Internal reference to the list being iterated over. */
    protected Iterator m_realmListIter;

    /**
     * Constructor takes in the realm list to iterate over.
     *
     * @param realmList the list to iterate over
     */
    protected RealmListIterator(RealmList realmList) {
        Assertion.isNotNull(realmList, "Realm list cannot be null");
        m_realmListIter = realmList.getRealms().iterator();

    }

    /**
     * Checks if the iterator has another element.
     *
     * @return if the iterator has another element
     */
    public boolean hasNext() {
        return m_realmListIter.hasNext();
    }

    /**
     * Gets the next element from the iterator.
     *
     * @return the next element in the iterator
     */
    public Realm next() {
        return (Realm) m_realmListIter.next();
    }
}