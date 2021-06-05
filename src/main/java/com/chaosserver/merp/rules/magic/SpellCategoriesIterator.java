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
public class SpellCategoriesIterator {
    /** Internal reference to the list being iterated over. */
    protected Iterator iterator;

    /**
     * Constructor.
     *
     * @param spellCategories Spell Categories to iterate over
     */
    protected SpellCategoriesIterator(SpellCategories spellCategories) {
        Assertion.isNotNull(spellCategories, "Spell categories cannot be null");
        CategoryCache.getInstance(this).debug("Creating a new SpellCategoriesIterator");
        iterator = spellCategories.getSpellCategories().iterator();
    }

    /**
     * Validates if the iterator has another element.
     *
     * @return If the iterator has another element
     */
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * Return the next element in the iterator.
     *
     * @return The next element
     */
    public SpellCategory next() {
        return (SpellCategory) iterator.next();
    }
}