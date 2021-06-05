package com.chaosserver.merp.character.magic;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This is a list of the spell lists that character has.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class CharSpellLists {
    /** Holds the collectionof SpellLists for character. */
    protected Collection charSpellLists;

    /**
     * Constructor.
     */
    public CharSpellLists() {
        setCharSpellLists(new ArrayList(23));
    }

    /**
     * Setter for charSpellLists.
     *
     * @param charSpellLists The value of charSpellLists
     * @see #getCharSpellLists
     */
    public void setCharSpellLists(Collection charSpellLists) {
        this.charSpellLists = charSpellLists;
    }

    /**
     * Getter for charSpellLists.
     *
     * @return The value of charSpellLists
     * @see #setCharSpellLists
     */
    public Collection getCharSpellLists() {
        return this.charSpellLists;
    }

    /**
     * Adds a character spell list to the character.
     *
     * @param charSpellList The spell list to add
     */
    public void addCharSpellList(CharSpellList charSpellList) {
        getCharSpellLists().add(charSpellList);
    }

    /**
     * Gets an iterator over the character spell lists.
     *
     * @return An iterator over the character spell lists
     */
    public CharSpellListIterator iterator() {
        return new CharSpellListIterator(this);
    }

}