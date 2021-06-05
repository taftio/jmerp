package com.chaosserver.merp.rules.magic.realm;

import java.util.ArrayList;
import java.util.Collection;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.data.JavaBeanLoader;
import com.chaosserver.data.JavaBeanLoaderExceptionX;
import com.chaosserver.exception.LoadErrorRX;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.data.FileNameGetter;

/**
 * This is a list of all the magical realms.
 * <p>
 * This list is pretty boring in MERP, because it only contains
 * two values.  It doesn't get much more exciting in RoleMaster either.
 * </p>
 *
 * @author Jordan Reed
 * @version 2.0
 * @since The Beginning
 */
public class RealmList implements IRealmXmlConstants {
    /** Singleton self reference. */
    private static RealmList self;

    /** Internal list of realms. */
    private ArrayList m_realmList;

    /**
     * Getter for the internal realmlist.  This is friendly since
     * only the iterator should use it.
     *
     * @return the ArrayList storing the realm objects
     */
    Collection getRealms() {
        return this.m_realmList;
    }

    /**
     * Setter to be used by the loader.
     *
     * @param realmList The realm list
     */
    public void setRealms(ArrayList realmList) {
        this.m_realmList = realmList;
    }

    /**
     * Private constructor for the realm list.  This is a
     * singleton and therefore should only be loaded from the magic
     * of the instance method.
     */
    public RealmList() {
    }

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static RealmList instance() {
        if(self == null) {
            try {
                self = (RealmList) JavaBeanLoader.getBean(FileNameGetter.XML_REALMLIST);
            } catch (JavaBeanLoaderExceptionX e) {
                throw new LoadErrorRX("Problem loading: '" + FileNameGetter.XML_REALMLIST + "', " + e.toString());
            }
        }

        return self;
    }

    /**
     * Returns an iterator over the elements in this collection.
     *
     * @return an iterator over the elements in this collection
     */
    public RealmListIterator iterator() {
        return new RealmListIterator(this);
    }

    /**
     * Returns a valid iterator over the elements in this collection.
     *
     * @param character The character to validate against
     * @return a valid iterator over the elements in this collection
     */
    public RealmListIterator validIterator(MerpCharacter character) {
        return new ValidRealmListIterator(this, character);
    }
}