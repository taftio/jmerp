package com.chaosserver.merp.rules.specialabilities;

import java.util.ArrayList;
import java.util.Collection;

import com.chaosserver.logging.CategoryCache;
import com.chaosserver.assertion.Assertion;
import com.chaosserver.data.JavaBeanLoader;
import com.chaosserver.data.JavaBeanLoaderExceptionX;
import com.chaosserver.exception.LoadErrorRX;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.data.FileNameGetter;
import com.chaosserver.merp.rules.race.Race;

/**
 * Holds the system list of all of the specialAbilitys.
 *
 * This class is a singleton method.  It will rarely be used on its own.
 * In stead it can be used to get a reference to an iterator or to
 * use the <code>SpecialAbilityFinder</code> object to get a particular specialAbility.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class SpecialAbilities {
    /** Singleton self reference. */
    private static SpecialAbilities s_self;

    /** The specialAbilityList holds the list of all the specialAbilitys in the system. */
    protected Collection specialAbilities;

    /**
     * Empty Constructor.
     */
    public SpecialAbilities() {
    }

    /**
     * Getter of specialAbilityList.
     *
     * @return The specialAbility list
     * @see #setSpecialAbilities
     */
    public Collection getSpecialAbilities() {
        return specialAbilities;
    }

    /**
     * Setter of the specialAbilityList.
     *
     * @param specialAbilities the new specialAbilitylist
     * @see #getSpecialAbilities
     */
    public void setSpecialAbilities(Collection specialAbilities) {
        CategoryCache.getInstance(this).debug("Setting special abilities with size: " + specialAbilities.size());
        this.specialAbilities = specialAbilities;
    }

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static SpecialAbilities instance() {
        if(s_self == null) {
            try {
                s_self = (SpecialAbilities) JavaBeanLoader.getBean(FileNameGetter.XML_SPECIAL_ABILITY_LIST);
            } catch (JavaBeanLoaderExceptionX e) {
                throw new LoadErrorRX("Problem loading: '" + FileNameGetter.XML_RACELIST + "', " + e.toString());
            }
        }

        return s_self;
    }

    /**
     * Returns an iterator over the elements in this collection.
     *
     * @return an iterator over the elements in this collection
     */
    public SpecialAbilityIterator iterator() {
        return new SpecialAbilityIterator(this);
    }

    /**
     * Returns an iterator over the elements in this collection.
     *
     * @param race A race filter on the special abilities list
     * @return an iterator over the elements in this collection
     */
    public SpecialAbilityIterator iterator(Race race) {
        return iterator();
    }

}