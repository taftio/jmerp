package com.chaosserver.merp.rules.profession;

import java.util.ArrayList;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.data.FileNameGetter;
import com.chaosserver.data.JavaBeanLoader;
import com.chaosserver.data.JavaBeanLoaderExceptionX;
import com.chaosserver.exception.LoadErrorRX;
import com.chaosserver.merp.rules.magic.realm.Realm;
import com.chaosserver.merp.rules.magic.realm.RealmFinder;
import com.chaosserver.merp.rules.magic.realm.restriction.RealmRestriction;
import com.chaosserver.merp.rules.magic.realm.restriction.RealmLevelRestriction;
import com.chaosserver.merp.rules.restriction.RestrictionList;

/**
 * Holds the list of profession in the system.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class ProfessionList {
    /** Singleton self reference. */
    private static ProfessionList s_self;

    /** The list of profession. */
    protected ArrayList m_profList;

    /**
     * Getter for the internal proflist.  This is friendly since
     * only the iterator should use it.
     *
     * @return the ArrayList storing the profession objects
     */
    ArrayList getProfList() {
        return m_profList;
    }

    /**
     * Setter for the profession list for use by the bean loader.
     *
     * @param profList The profession list
     */
    public void setProfessionList(ArrayList profList) {
        m_profList = profList;
    }

    /**
     * Public constructor for use by the bean loader only.  All other
     * classes should use the instance() method to get an instance of
     * this object.
     */
    public ProfessionList() {
    }

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static ProfessionList instance() {
        if(s_self == null) {
            try {
                s_self = (ProfessionList) JavaBeanLoader.getBean(FileNameGetter.XML_PROFESSIONLIST);
            } catch(JavaBeanLoaderExceptionX e) {
                throw new LoadErrorRX("Failed to load Profession list: " + e.toString());
            }
        }

        return s_self;
    }


    /**
     * Returns a valid iterator over the elements in this collection.
     *
     * @param mChar The character to validate the professions against
     * @return a valid iterator over the elements in this collection
     */
    public ProfessionListIterator validIterator(MerpCharacter mChar) {
        return new ValidProfessionListIterator(this, mChar);
    }

    /**
     * Returns an iterator over the elements in this collection.
     *
     * @return an iterator over the elements in this collection
     */
    public ProfessionListIterator iterator() {
        return new ProfessionListIterator(this);
    }
}