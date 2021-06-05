package com.chaosserver.merp.rules.restriction;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

import com.chaosserver.merp.character.MerpCharacter;

/**
 * Base class for a character restriction.
 * <p>
 * When a character restriction is placed onto a character, any
 * change made to the character's properties will be tested against
 * the restriction allowing restrictions to veto changes
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public abstract class BaseCharRestriction implements VetoableChangeListener {
    /** Reference to the character this restriction applies to. */
    protected MerpCharacter m_mChar;

    /**
     * Setter for the character attribute.
     * <p>
     * Subclasses are responsible for unbinding themselves for the old
     * character and then binding their restrictions to the new one
     * threw Vetoable Property Events.
     * </p>
     *
     * @param mChar The new character
     * @see #getChar
     */
    public void setChar(MerpCharacter mChar) {
        preSet(getChar());
        if(m_mChar != null) {
            m_mChar.removeVetoableChangeListener(this);
        }

        m_mChar = mChar;

        if(m_mChar != null) {
            m_mChar.addVetoableChangeListener(this);
        }
        postSet(mChar);
    }

    /**
     * Getter for the character this restriction applies to.
     *
     * @return The character
     * @see #setChar
     */
    public MerpCharacter getChar() {
        return m_mChar;
    }

    /**
     * Should be overriden ina subclass and used to removing any
     * listeners to the old character.
     *
     * @param mChar The old merp character
     */
    protected void preSet(MerpCharacter mChar) {
    }

    /**
     * Should be ovverried in a subclass and used to add new
     * listeners so it may veto property changes that violate
     * state.
     *
     * @param mChar the new character
     */
    protected void postSet(MerpCharacter mChar) {
    }

    /**
     * based on a character this will state if the list if valid
     * given this restriction.
     *
     * @param mChar The character to check the validity of against this restriction
     * @return Is the character valid
     */
    public abstract boolean isValid(MerpCharacter mChar);

    /**
     * This returns a description of what this is checking.  It is
     * provided for user feedback when they violate the restriction.
     *
     * @return Description of rule
     */
    public abstract String getDescription();

    // Inherits from interface
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {

    }

}