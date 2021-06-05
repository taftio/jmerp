package com.chaosserver.merp.rules.race;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ListIterator;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.data.JavaBeanLoader;
import com.chaosserver.data.JavaBeanLoaderExceptionX;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.data.FileNameGetter;
import com.chaosserver.exception.LoadErrorRX;

/**
 * Holds the system list of all of the races.
 *
 * This class is a singleton method.  It will rarely be used on its own.
 * In stead it can be used to get a reference to an iterator or to
 * use the <code>RaceFinder</code> object to get a particular race.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class RaceList {
    /** Singleton self reference. */
    private static RaceList s_self;

    /** The raceList holds the list of all the races in the system. */
    ArrayList m_raceList;

    /**
     * Empty Constructor.
     */
    public RaceList() {
    }

    /**
     * Getter of raceList.
     *
     * @return The race list
     * @see #setRaceList
     */
    public Collection getRaceList() {
        return m_raceList;
    }

    /**
     * Setter of the raceList.
     *
     * @param raceList the new racelist
     * @see #getRaceList
     */
    public void setRaceList(ArrayList raceList) {
        m_raceList = raceList;
    }

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static RaceList instance() {
        if(s_self == null) {
            try {
                s_self = (RaceList) JavaBeanLoader.getBean(FileNameGetter.XML_RACELIST);
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
    public RaceListIterator iterator() {
        return new RaceListIterator((ListIterator) m_raceList.listIterator());;
    }

    /**
     * Returns a valid iterator over the elements in this collection.
     *
     * @param mChar The character to validate the races against
     * @return a valid iterator over the elements in this collection
     */
    public ValidRaceListIterator validIterator(MerpCharacter mChar) {
        return new ValidRaceListIterator(m_raceList.listIterator(), mChar);
    }

    /**
     * Returns the size of the underlying race list.
     *
     * @return The size
     */
    public int size() {
        Assertion.isNotNull(m_raceList, "Null race lists don't have a size");
        return m_raceList.size();
    }

}