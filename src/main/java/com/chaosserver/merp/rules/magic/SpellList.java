package com.chaosserver.merp.rules.magic;

import com.chaosserver.data.find.Findable;
import com.chaosserver.logging.CategoryCache;

/**
 * Represents a Spell List.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class SpellList implements Findable {
    /** The spell category (Essense, Ranger, etc). */
    protected SpellCategory spellCategory;

    /** The name of this spell list. */
    protected String name;

    /**
     * Empty constructor for bean loading.
     */
    public SpellList() {
    }

    // Inherits javadocs from interface
    public String getPkName() {
        return "Name";
    }

    // Inherits javadocs from interface
    public Object getFinder() {
        //return SpellListFinder.instance();
        return null;
    }

    /**
     * Setter for the spell category.
     *
     * @param spellCategory The spell category
     * @see #getSpellCategory
     */
    public void setSpellCategory(SpellCategory spellCategory) {
        this.spellCategory = spellCategory;
    }

    /**
     * Getter for the spell category.
     *
     * @return The spell category
     * @see #setSpellCategory
     */
    public SpellCategory getSpellCategory() {
        return this.spellCategory;
    }

    /**
     * Setter for the name of the spell lsit.
     *
     * @param name The name of the spell list
     * @see #getName
     */
    public void setName(String name) {
        CategoryCache.getInstance(this).info("Created Spell List: " + name);
        this.name = name;
    }

    /**
     * Getter for the name of the spell list.
     *
     * @return the name of the spell list
     * @see #setName
     */
    public String getName() {
        return this.name;
    }

    /**
     * Converts the object to a string.
     *
     * @return a string version of the object
     */
    public String toString() {
        return getName();
    }



}