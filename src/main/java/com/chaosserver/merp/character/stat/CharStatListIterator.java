package com.chaosserver.merp.character.stat;

import java.util.ListIterator;

import com.chaosserver.exception.MethodNotImplementedRX;

/**
 * Iterates over a <code>ICharStatList</code>.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class CharStatListIterator implements ListIterator {
    /** Holds a reference to the underlying iterator being worked with. */
    private ListIterator m_statListIter;

    /**
     * Takes in the CharStatList that will iterator over.
     *
     * @param charStatList The CharStatList to iterate over
     */
    public CharStatListIterator(ICharStatList charStatList) {
        m_statListIter = charStatList.getCharStatList().listIterator();

    }

    // Inherits from interface
    public boolean hasNext() {
        return m_statListIter.hasNext();
    }

    // Inherits from interface
    public boolean hasPrevious() {
        return m_statListIter.hasPrevious();
    }

    // Inherits from interface
    public Object next() {
        return m_statListIter.next();
    }

    // Inherits from interface
    public Object previous() {
        return m_statListIter.previous();
    }

    // Inherits from interface
    public int nextIndex() {
        return m_statListIter.nextIndex();
    }

    // Inherits from interface
    public int previousIndex() {
        return m_statListIter.previousIndex();
    }

    /**
     * You are not allowed to remove from the <code>CharStatList</code>
     * since it is based on the system <code>StatList</code> which
     * cannot be changed.
     *
     * @throws MethodNotImplementedRX Cannot call this method
     */
    public void remove() {
        throw new MethodNotImplementedRX("Cannot remove from a statlist, based on system");
    }

    /**
     * You are not allowed to set the entire object in the
     * <code>CharStatList</code> since it is based on the system
     * <code>StatList</code> which cannot be changed.
     *
     * @throws MethodNotImplementedRX Cannot call this method
     */
    public void set(Object obj) {
        throw new MethodNotImplementedRX("Cannot reset a value, based on system");
    }

    /**
     * You are not allowed to add to the <code>CharStatList</code>
     * since it is based on the system <code>StatList</code> which
     * cannot be changed.
     *
     * @throws MethodNotImplementedRX Cannot call this method
     */
    public void add(Object obj) {
        throw new MethodNotImplementedRX("Cannot add, based on system");
    }
}