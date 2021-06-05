package com.chaosserver.merp.rules.specialabilities;

import java.util.Iterator;

/**
 * Iterates over a list of special abiltities.
 *
 * @author Jordan Reed
 */
public class SpecialAbilityIterator {
    /** The iterator being wrapped for casting. */
    protected Iterator iterator;

    /**
     * Constructor taking is the list to iterate over.
     *
     * @param specialAbilities The list to iterate over
     */
    public SpecialAbilityIterator(SpecialAbilities specialAbilities) {
        this.iterator = specialAbilities.getSpecialAbilities().iterator();
    }

    /**
     * Checks if the iterator has another element.
     *
     * @return if the iterator has another element
     */
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * Gets the next element from the iterator.
     *
     * @return the next element in the iterator
     */
    public SpecialAbility next() {
        return (SpecialAbility) iterator.next();
    }
}