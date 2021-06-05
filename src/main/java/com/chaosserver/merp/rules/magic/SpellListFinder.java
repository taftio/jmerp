package com.chaosserver.merp.rules.magic;

import com.chaosserver.assertion.Assertion;

/**
 * User this package to find a Spell List.
 *
 * @author Jordan Reed
 */
public class SpellListFinder {
    /** Singleton self reference. */
    private static SpellListFinder self;

    /** Protected constructor to force all access through the instance method. */
    protected SpellListFinder() {
        Assertion.isTrue(self == null, "Should not call constructor of "
            + "a singleton after it has been constructed");
    }

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static SpellListFinder instance() {
        if(self == null) {
            self = new SpellListFinder();
        }

        return self;
    }

    /**
     * Finds all Spell Lists in the system spell list.
     *
     * @return Iterator over all spell lists
     */
    public ISpellListIterator findAll() {
        return findAll(SpellLists.instance());
    }

    /**
     * Finds all Spell Lists in a given spell list.
     *
     * @param spellLists The a list of spells
     * @return Iterator over all the spells in the list
     */
    public ISpellListIterator findAll(SpellLists spellLists) {
        return new SpellListIterator(spellLists);
    }
}