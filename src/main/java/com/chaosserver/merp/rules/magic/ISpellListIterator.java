package com.chaosserver.merp.rules.magic;

/**
 * Interface for an iterator over a list of Spell Lists.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public interface ISpellListIterator {
    /**
     * Checks if the iterator has another SpellList.
     *
     * @return If there is another element
     */
    public boolean hasNext();

    /**
     * Gets the next element from the iterator.
     *
     * @return The next element
     */
    public SpellList next();
}