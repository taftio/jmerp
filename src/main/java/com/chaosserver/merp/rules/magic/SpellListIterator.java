package com.chaosserver.merp.rules.magic;

import java.util.Iterator;

import com.chaosserver.logging.CategoryCache;
import com.chaosserver.assertion.Assertion;

/**
 * Iterator over the spell categories.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class SpellListIterator implements ISpellListIterator {
    /** Internal reference to the list being iterated over. */
    protected Iterator iterator;

    /**
     * Constructor.
     *
     * @param spellLists The Spell List collection to iterate over
     */
    protected SpellListIterator(SpellLists spellLists) {
        Assertion.isNotNull(spellLists, "Spell categories cannot be null");
        CategoryCache.getInstance(this).debug("Creating a new SpellListIterator");
        iterator = spellLists.getSpellLists().iterator();
    }

    // Inherits javadoc from interface
    public boolean hasNext() {
        return iterator.hasNext();
    }

    // Inherits javadoc from interface
    public SpellList next() {
        return (SpellList) iterator.next();
    }
}