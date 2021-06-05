package com.chaosserver.merp.rules.magic;

import java.util.Collection;

import com.chaosserver.logging.CategoryCache;
import com.chaosserver.assertion.Assertion;
import com.chaosserver.data.JavaBeanLoader;
import com.chaosserver.data.JavaBeanLoaderExceptionX;
import com.chaosserver.exception.LoadErrorRX;
import com.chaosserver.merp.data.FileNameGetter;

/**
 * Singleton list of the spell categories in the system.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class SpellCategories {
    /** Singleton self reference. */
    protected static SpellCategories self;

    /** Holds the collection of spell categories. */
    protected Collection spellCategories;

    /**
     * Setter for the spell categories.
     *
     * @param spellCategories The spell categories
     * @see #getSpellCategories
     */
    public void setSpellCategories(Collection spellCategories) {
        this.spellCategories = spellCategories;
    }

    /**
     * Getter for the spell categories.
     *
     * @return The spell categories
     * @see #setSpellCategories
     */
    public Collection getSpellCategories() {
        return spellCategories;
    }

    /**
     * Public constructor for use by the bean loader only.  All other
     * classes should use the instance() method to get an instance of
     * this object.
     */
    public SpellCategories() {
        Assertion.isTrue(self == null, "Should not call constructor of "
            + "a singleton after it has been constructed");
    }

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static SpellCategories instance() {
        if(self == null) {
            CategoryCache.getInstance(SpellCategories.class).info("Creating instance of SpellCategories");
            try {
                self = (SpellCategories) JavaBeanLoader.getBean(FileNameGetter.XML_SPELL_CATEGORIES);
            } catch (JavaBeanLoaderExceptionX e) {
                throw new LoadErrorRX("Problem loading: '" + FileNameGetter.XML_SPELL_CATEGORIES + "', " + e.toString());
            }

        }

        return self;
    }

    /**
     * Returns an iterator over the elements in this collection.
     *
     * @return an Iterator over the elements in this collection
     */
    public SpellCategoriesIterator iterator() {
        return new SpellCategoriesIterator(this);
    }
}