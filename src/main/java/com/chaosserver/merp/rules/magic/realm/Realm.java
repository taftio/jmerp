package com.chaosserver.merp.rules.magic.realm;

import com.chaosserver.data.find.Findable;
import com.chaosserver.merp.rules.restriction.IRestricted;
import com.chaosserver.merp.rules.restriction.RestrictionList;

/**
 * This class represents a Realm.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class Realm implements IRestricted, Findable {
    /** This name of the realm. */
    protected String m_name;

    /** Holds the restrictions. */
    protected RestrictionList restrictionList;


    /**
     * Setter for the restriction list.
     *
     * @param restrictionList the new restriction list
     * @see #getRestrictionList
     */
    public void setRestrictionList(RestrictionList restrictionList) {
        this.restrictionList = restrictionList;
    }

    /**
     * Getter for the restriction list.
     *
     * @return The restriction list
     * @see #setRestrictionList
     */
    public RestrictionList getRestrictionList() {
        return this.restrictionList;
    }

    /**
     * Setter method for name.
     *
     * @param name The new name
     * @see #getName
     */
    public void setName(String name) {
        m_name = name;
    }

    /**
     * Getter method for name.
     *
     * @return the name of the realm
     * @see #setName
     */
    public String getName() {
        return m_name;
    }

    /**
     * Blank constructor for bean loading.
     */
    public Realm() {
    }

    /**
     * This friendly constructor should only be built by the RealmList
     * object in the same package.
     *
     * @param name The name of the list
     */
    Realm(String name) {
        setName(name);
    }

    // Inherits javadocs from interface
    public String getPkName() {
        return "Name";
    }

    // Inherits javadocs from interface
    public Object getFinder() {
        return RealmFinder.instance();
    }

    /**
     * Converts the object to string representation.
     *
     * @return a string representation of the object
     */
    public String toString() {
        return getName();
    }

}