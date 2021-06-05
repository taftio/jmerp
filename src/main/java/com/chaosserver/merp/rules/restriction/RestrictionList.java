package com.chaosserver.merp.rules.restriction;

import java.util.ArrayList;

import com.chaosserver.merp.character.MerpCharacter;

/**
 * A list of restrictions placed on another object.
 * <p>
 * This is a list of restrictions that can be placed on a character.
 * Objects having one of these should use the <code>IRestricted</code> interface
 * to provide a common accessor to the restriction list associated with
 * it.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 */

public class RestrictionList {
    /** Holds the list of restrictions.  These should extend from the baseCharRestriction. */
    private ArrayList m_RestrictionList;

    /**
     * Setter for the stat restriction list.
     *
     * @param RestrictionList The new list
     * @see #getRestrictionList
     */
    public void setRestrictionList(ArrayList RestrictionList) {
        m_RestrictionList = RestrictionList;
    }

    /**
     * Getter for the restriction list friendly so the iterator can
     * grab it.
     *
     * @return The restriction list
     * @see #setRestrictionList
     */
    public ArrayList getRestrictionList() {
        return m_RestrictionList;
    }

    /**
     * Empty Constructor.
     */
    public RestrictionList() {
        m_RestrictionList = new ArrayList();
    }

    /**
     * Construct that takes in an arraymap to use as the restriction.
     * list.
     *
     * @param restrictionList The new list
     */
    public RestrictionList(ArrayList restrictionList) {
        setRestrictionList(restrictionList);
    }

    /**
     * Returns an iterator over the restriction list.
     *
     * @return the iterator
     */
    public RestrictionListIterator iterator() {
        return new RestrictionListIterator(this);
    }

    /**
     * Checks the character against the restriction in the list.
     *
     * @param mChar The character to validate
     * @return True if the character validates againsts all the
     *          restrictions in the list
     */
    public boolean isValid(MerpCharacter mChar) {
        RestrictionListIterator rli = this.iterator();

        while(rli.hasNext()) {
            BaseCharRestriction restriction = (BaseCharRestriction) rli.next();
            if( !restriction.isValid(mChar) ) {
                return false;
            }
        }

        return true;
    }

    /**
     * Sets the character that this list is applying to.  It will
     * apply the character to every restriction in the list.
     *
     * @param mChar The character to restrict
     */
    public void setChar(MerpCharacter mChar) {
        RestrictionListIterator rli = this.iterator();

        while(rli.hasNext()) {
            BaseCharRestriction restriction = (BaseCharRestriction) rli.next();
            restriction.setChar(mChar);
        }

    }


}