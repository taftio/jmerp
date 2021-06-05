package com.chaosserver.merp.rules.stat;

import java.io.Serializable;
import com.chaosserver.data.find.Findable;

/**
 * This contains generic information about a stat in the system
 * including certain rules for rolling the stat.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */

public class Stat implements Findable, Serializable {
    /** Default minumum value. */
    private static int s_defaultMinVal = 20;

    /** Default max adjust value. */
    private static int s_defaultMaxAdjust = 0;

    /** Unique ID identifying this stat. */
    private int m_id;

    /** Name of the stat. */
    private String m_name;

    /** Abbreviation of the stat name. */
    private String m_abbr;

    /** Is this stat swappable with others? */
    private boolean m_isSwappable = false;

    /** Is this stat totaled when calculating a minimum total? */
    private boolean m_isTotaled = false;

    /** This is the minumum value this stat can have. */
    private int m_minVal = s_defaultMinVal;

    /** The maximum that a stat can be adjusted after rolled. */
    private int m_maxAdjust = s_defaultMaxAdjust;


    /**
     * The constructor for the stat takes in all the information about it.
     * the general stat object cannot be changed once it has been created.
     *
     * @param id The unique ID of this stat
     * @param name The name of the stat
     * @param abbr The two letter abreviation for the stat
     */
    public Stat(int id, String name, String abbr) {
        setID(id);
        setName(name);
        setAbbr(abbr);
    }

    /**
     * This is more complicated constructor that allows on to define
     * certain rules for how the stat can be rolled.  This rules are
     * needed if you choose to use the StandardCharStatFactory to
     * generate character stats.
     *
     * @param id The unique ID of this stat
     * @param name The name of the stat
     * @param abbr The two letter abreviation for the stat
     * @param isSwappable This says if the stat is swappable with other stats in the list
     * @param isTotaled If there is a minumum total for the stats, this says it should be included in that total
     * @param minVal This is the minumum value this stat should be when rolled
     * @param maxAdjust This is the maximum amount this stat can be adjusted in either direction
     */
    public Stat(int id, String name, String abbr, boolean isSwappable, boolean isTotaled, int minVal, int maxAdjust) {
        this(id, name, abbr);
        setSwappable(isSwappable);
        setTotaled(isTotaled);
        setMinVal(minVal);
        setMaxAdjust(maxAdjust);
    }

    // Inherits javadoc from interface
    public String getPkName() {
        return "Name";
    }

    // Inherits javadoc from interface
    public Object getFinder() {
        return StatFinder.instance();
    }

    /**
     * Retreives the ID of the stat.
     *
     * @return The stat's ID
     * @see #setID
     */
    public int getID() {
        return m_id;
    }

    /**
     * Setter for the ID.
     *
     * @param id What is the ID?
     * @see #getID
     */
    protected void setID(int id) {
        m_id = id;
    }

    /**
     * Retreives the name of the stat.
     *
     * @return The stat's name
     * @see #setName
     */
    public String getName() {
        return m_name;
    }

    /**
     * Setter for the name.
     *
     * @param name The new name
     * @see #getName
     */
    protected void setName(String name) {
        m_name = name;
    }

    /**
     * Retreives the two letter abbreviation for the stat name.
     *
     * @return The stat's abbreviation
     * @see #setAbbr
     */
    public String getAbbr() {
        return m_abbr;
    }

    /**
     * Setter for the abbreviation.
     *
     * @param abbr The new abbreviation
     * @see #getAbbr
     */
    protected void setAbbr(String abbr) {
        m_abbr = abbr;
    }

    /**
     * Getter for isTotaled.
     *
     * @return Is this totaled?
     * @see #setTotaled
     */
    public boolean isTotaled() {
        return m_isTotaled;
    }

    /**
     * Setter for isTotaled.
     *
     * @param isTotaled Whether or not this value is totaled
     * @see #isTotaled
     */
    public void setTotaled(boolean isTotaled) {
        m_isTotaled = isTotaled;
    }

    /**
     * Getter for Swappable.
     *
     * @return Is this stat swappable
     * @see #setSwappable
     */
    public boolean isSwappable() {
        return m_isSwappable;
    }

    /**
     * Setter for swappable.
     *
     * @param isSwappable Whether or not this stat can be swapped
     * @see #isSwappable
     */
    public void setSwappable(boolean isSwappable) {
        m_isSwappable = isSwappable;
    }

    /**
     * Getter for min val.
     *
     * @return the Minumum value
     * @see #setMinVal
     */
    public int getMinVal() {
        return m_minVal;
    }

    /**
     * Setter for min val.  If the value passed in is less than 0, the
     * default min val of the object is used.
     *
     * @param minVal the new minimum value
     * @see #getMinVal
     */
    public void setMinVal(int minVal) {
        if(minVal >= 0) {
            m_minVal = minVal;
        } else {
            m_minVal = s_defaultMinVal;
        }
    }

    /**
     * Getter for max adjust.
     *
     * @return the max adjustment
     * @see #setMaxAdjust
     */
    public int getMaxAdjust() {
        return m_maxAdjust;
    }

    /**
     * Setter for max adjust.  If the value passed in is less than 0, the
     * default max adjust of the object is used.
     *
     * @param maxAdjust the new minimum value
     * @see #getMaxAdjust
     */
    public void setMaxAdjust(int maxAdjust) {
        if(maxAdjust >= 0) {
            m_maxAdjust = maxAdjust;
        } else {
            m_maxAdjust = s_defaultMaxAdjust;
        }
    }

    /**
     * Converts the object to string representation.
     *
     * @return a string representation of the object
     */
    public String toString() {
        return getName();
    }

    /**
     * Checks the stat name for equality.
     *
     * @param stat The stat to check for equality
     * @return If the names are equal
     */
    public boolean equals(Stat stat) {
        if(stat == null || getName() == null) {
            return false;
        }
        else {
            return getName().equals(stat.getName());
        }
    }
}