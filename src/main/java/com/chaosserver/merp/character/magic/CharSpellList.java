package com.chaosserver.merp.character.magic;

import com.chaosserver.merp.rules.magic.SpellList;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

/**
 * This contains information on a character's ability in a single.
 * spell list.
 */
public class CharSpellList {
    /** Reference to the property change supporter. */
    protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /** The property event name for SpellList. */
    public static String P_SPELL_LIST = "com.chaosserver.merp.rules.magic.SpellList";

    /** The list the character knows. */
    public static String P_PERCENT_KNOWN = "com.chaosserver.merp.rules.magic.PercentKnown";

    /** The property change for percentage known. */
    protected SpellList spellList;

    /** Percent known.  When a spell list is learned this automatically becomes 100. */
    protected int percentKnown;

    /** Default bean constructor. */
    public CharSpellList() {
    }

    /**
     * Fuller constructor.
     *
     * @param spellList The spell list the character knows
     * @param percentKnown Percentage of the spell list the character has learned
     */
    public CharSpellList(SpellList spellList, int percentKnown) {
        setSpellList(spellList);
        setPercentKnown(percentKnown);
    }

    /**
     * Setter for the spellList.
     *
     * @param spellList The spellList
     * @see #getSpellList
     */
    public void setSpellList(SpellList spellList) {
        SpellList old_spellList = getSpellList();
        this.spellList = spellList;
        changes.firePropertyChange(P_SPELL_LIST, old_spellList, getSpellList());
    }

    /**
     * Getter for the spellList.
     *
     * @return The spellList
     * @see #setSpellList
     */
    public SpellList getSpellList() {
        return this.spellList;
    }

    /**
     * Setter for percent known.
     *
     * @param percentKnown Percent of the spell list the character knows
     * @see #getPercentKnown
     */
    public void setPercentKnown(int percentKnown) {
        int old_percentKnown = getPercentKnown();
        this.percentKnown = percentKnown;
        changes.firePropertyChange(P_PERCENT_KNOWN, old_percentKnown, getPercentKnown());
    }

    /**
     * Getter for percent known.
     *
     * @return Percent of the spell list the character knows
     * @see #setPercentKnown
     */
    public int getPercentKnown() {
        return this.percentKnown;
    }

    /**
     * Determines if the spell list is known by the character.
     *
     * @return If the character knows the spell list
     */
    public boolean isKnown() {
        if(percentKnown >= 100) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Converts the EA into a string.
     *
     * @return The EA in string form
     */
    public String toString() {
        return getSpellList() + ": " + getPercentKnown();
    }

    /**
     * Add a PropertyChangeListener to the listener list. The listener is registered for all properties.
     *
     * @param l The PropertyChangeListener to be added
     * @see #removePropertyChangeListener
     */
    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    /**
     * Remove a PropertyChangeListener from the listener list. This removes a PropertyChangeListener
     * that was registered for all properties.
     *
     * @param l The PropertyChangeListener to be removed
     * @see #addPropertyChangeListener
     */
    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }

}