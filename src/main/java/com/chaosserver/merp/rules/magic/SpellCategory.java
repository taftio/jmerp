package com.chaosserver.merp.rules.magic;

import com.chaosserver.logging.CategoryCache;

/**
 * A cateogry of spell lists.
 * <p>
 * This includes things like Open Essence and Ranger
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class SpellCategory {
    /** Spell List belongs to a particular profession. */
    public static String PROFESSION_CATEGORY = "Profession";

    /** Spell List belong to a particular realm. */
    public static String REALM_CATEGORY = "Realm";

    /** The name of the spell category. */
    protected String name;

    /** The type of category (Profession or Realm). */
    protected String categoryType;

    /**
     * Emptry constructor for bean loader.
     */
    public SpellCategory() {
    }

    /**
     * Setter for the name.
     *
     * @param name The name of the spell category
     * @see #getName
     */
    public void setName(String name) {
        CategoryCache.getInstance(this).info("Created Spell Category: " + name);
        this.name = name;
    }

    /**
     * Getter for the name.
     *
     * @return THe name of the spell category
     * @see #setName
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for the category type.
     *
     * @param categoryType The type of category
     * @see #getCategoryType
     */
    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    /**
     * Getter for the category type.
     *
     * @return The category type
     * @see #setCategoryType
     */
    public String getCategoryType() {
        return this.categoryType;
    }

    /**
     * Converts the object into a string.
     *
     * @return a string representation of the object
     */
    public String toString() {
        return getName();
    }
}