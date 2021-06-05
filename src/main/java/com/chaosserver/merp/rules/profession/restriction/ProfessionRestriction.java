package com.chaosserver.merp.rules.profession.restriction;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.rules.profession.Profession;


/**
 * This restriction blocks a profession from being applied to a character.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class ProfessionRestriction extends BaseProfessionRestriction  {
    /** Holds a reference to the profession the restriction is on. */
    private Profession m_profession;

    /**
     * getter for the profession.
     *
     * @return Profession this is restricting
     * @see #setProfession
     */
    public Profession getProfession() {
        return m_profession;
    }

    /**
     * setter for the profession.
     *
     * @param profession The new value of profession
     * @see #getProfession
     */
    public void setProfession(Profession profession) {
        m_profession = profession;
    }


    /**
     * Constructor for bean loader.
     */
    public ProfessionRestriction() {
    }

    /**
     * Constructor to make the restriction.
     *
     * @param prof The profession that is restricted
     */
    public ProfessionRestriction(Profession prof) {
        setProfession(prof);
    }

    // Inherits from interface
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        if(MerpCharacter.P_PROFESSION.equals(evt.getPropertyName()) && evt.getNewValue() != null) {
            if(evt.getNewValue().equals(getProfession())) {
                throw new PropertyVetoException("Restricted Profession!", evt);
            }
        }
    }

    // Inherits from base class
    public boolean isValid(MerpCharacter mChar) {
        Assertion.isNotNull(mChar, "mChar cannot be null");

        // First check if the current character is valid
        if(mChar.getProfession() != null) {
            if(getProfession().equals(mChar.getProfession())) {
                return false;
            }
        }
        return true;
    }

    // Inherits from base class
    public String getDescription() {
        return "Profession Not Allowed"+getProfession().getName();
    }

    /**
     * Converts the object to string representation.
     *
     * @return a string representation of the object
     */
     public String toString() {
        StringBuffer sb = new StringBuffer(255);
        sb.append("----- ProfessionRestriction.toString -----\n");
        sb.append("Profession: " + getProfession().getName() + "\n");
        return sb.toString();
    }

}