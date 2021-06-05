package com.chaosserver.merp.rules.magic;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.data.JavaBeanLoader;
import com.chaosserver.data.JavaBeanLoaderExceptionX;
import com.chaosserver.exception.LoadErrorRX;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.data.FileNameGetter;

import java.util.Collection;

/**
 * Represents all of the spell lists in the MERP system.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class SpellLists {
    /** Singleton self reference. */
    protected static SpellLists self;

    /** Collection of SpellLists in the system. */
    protected Collection SpellLists;

    /**
     * Sets the collection of spell lists.
     * <p>
     * This should only be used by the bean loader when the
     * object is initialized.
     * </p>
     *
     * @param SpellLists The list of spells for the system
     * @see #getSpellLists
     */
    public void setSpellLists(Collection SpellLists) {
        this.SpellLists = SpellLists;

    }

    /**
     * Gets the collection of spells in the system.
     *
     * @return The collection of system spells
     * @see #setSpellLists
     */
    public Collection getSpellLists() {
        return this.SpellLists;
    }

    /**
     * Public constructor for use by the bean loader only.  All other
     * classes should use the instance() method to get an instance of
     * this object.
     */
    public SpellLists() {
        Assertion.isTrue(self == null, "Should not call constructor of "
            + "a singleton after it has been constructed");
    }

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static SpellLists instance() {
        if(self == null) {
            try {
                self = (SpellLists) JavaBeanLoader.getBean(FileNameGetter.XML_SPELLLIST);
            } catch (JavaBeanLoaderExceptionX e) {
                throw new LoadErrorRX("Problem loading: '" + FileNameGetter.XML_SPELLLIST + "', " + e.toString());
            }
        }

        return self;
    }

    /**
     * Returns the number of elements in this collection.
     *
     * @return the number of elements in this collection
     */
    public int size() {
        return this.SpellLists.size();
    }

    /**
     * Returns an iterator over the elements in this collection.
     *
     * @return an iterator over the elements in this collection
     */
    public SpellListIterator iterator() {
        return new SpellListIterator(this);
    }

    /**
     * Returns a valid iterator over the elements in this collection.
     *
     * @param character Character to validate each spell list against
     * @return a valid iterator over the elements in this collection
     */
    public SpellListIterator validIterator(MerpCharacter character) {
        return new ValidSpellListIterator(this, character);
    }
}