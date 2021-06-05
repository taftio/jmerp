package com.chaosserver.merp.character.magic;

import java.util.Iterator;

/**
 * Iterates over character spells lists.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class CharSpellListIterator {
    /** Internal iterator object. */
    protected Iterator iterator;

    /**
     * Constructor.
     *
     * @param charSpellLists The lists to iterate over
     */
    public CharSpellListIterator(CharSpellLists charSpellLists) {
        this.iterator = charSpellLists.getCharSpellLists().iterator();
    }

    /**
     * Tests if there are more elements in the iterator.
     *
     * @return if there are more elements
     */
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * Returns the next object in the iterator.
     *
     * @return the next object in the iterator
     */
    public CharSpellList next() {
        return (CharSpellList) iterator.next();
    }

}