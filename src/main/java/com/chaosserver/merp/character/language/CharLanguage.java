package com.chaosserver.merp.character.language;

import com.chaosserver.merp.rules.language.Language;
import com.chaosserver.logging.CategoryCache;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

/**
 * Information about a character's skill in a single <code>Language</code>.
 *
 * @author Jordan Reed
 * @version 1.0
 *
 * @see com.chaosserver.merp.rules.language.Language
 */
public class CharLanguage {
    /** Reference to the property change supporter. */
    protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /** Reference to the vetoable change supporter. */
    protected VetoableChangeSupport vetoes = new VetoableChangeSupport(this);

    /** The language this character has. */
    protected Language language;

    /** Event label for the langauge property. */
    public static String P_LANGUAGE = "CharLanguage.Language";

    /** The ranks the character has in the language. */
    protected int ranks;

    /** Event label for the ranks property. */
    public static String P_RANKS = "CharLanguage.Ranks";

    /**
     * Base constructor for bean loading.
     */
    public CharLanguage() {
    }

    /**
     * Constructor taking in the language and ranks.
     *
     * @param language The new language known by the character
     * @param ranks The number of ranks the character has in the language
     */
    public CharLanguage(Language language, int ranks) {
        try {
            setLanguage(language);
            setRanks(ranks);
        }
        catch (PropertyVetoException e) {
            CategoryCache.getInstance(this).error("Constructor should NEVER have a veto occur", e);
        }
    }

    /**
     * Setter for the language.
     *
     * @param language The language
     * @see #getLanguage
     */
    public void setLanguage(Language language) {
        this.language = language;
    }

    /**
     * Getter for the language.
     *
     * @return the Language
     * @see #setLanguage
     */
    public Language getLanguage() {
        return this.language;
    }

    /**
     * Setter for the ranks.
     *
     * @param ranks The number of ranks in the language
     * @throws PropertyVetoException If the character can not set the
     *         ranks to this value
     * @see #getRanks
     */
    public void setRanks(int ranks) throws PropertyVetoException {
        vetoes.fireVetoableChange(P_RANKS, getRanks(), ranks);
        int old_ranks = getRanks();
        this.ranks = ranks;
        changes.firePropertyChange(P_RANKS, old_ranks, getRanks());
    }

    /**
     * getter for the ranks.
     *
     * @return The ranks in this language
     * @see #setRanks
     */
    public int getRanks() {
        return this.ranks;
    }

    /**
     * Adds a single rank to the number of ranks.
     *
     * @throws MaxRanksExceededX The player has exceeded the maximum ranks
     *         allowed in this language
     */
    public void addRank() throws MaxRanksExceededX {
        addRanks(1);
    }

    /**
     * Adds ranks to the number of ranks.
     *
     * @param ranks The number of ranks to add
     * @throws MaxRanksExceededX The player has exceeded the maximum ranks
     *         allowed in this language
     */
    public void addRanks(int ranks) throws MaxRanksExceededX {
        try {
            setRanks(getRanks() + ranks);
        }
        catch (PropertyVetoException e) {
            CategoryCache.getInstance(this).error("UI should allow adding to incorrect ranks", e);
        }
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

    /**
     * Add a VetoableChangeListener to the listener list. The listener is registered for all properties.
     *
     * @param v The VetoableChangeListener to be added
     * @see #removeVetoableChangeListener
     */
    public void addVetoableChangeListener(VetoableChangeListener v) {
      vetoes.addVetoableChangeListener(v);
    }

    /**
     * Remove a VetoableChangeListener from the listener list. This removes a
     * VetoableChangeListener that was registered for all properties.
     *
     * @param v The VetoableChangeListener to be removed
     * @see #addVetoableChangeListener
     */
    public void removeVetoableChangeListener(VetoableChangeListener v) {
      vetoes.removeVetoableChangeListener(v);
    }


    /**
     * Converts the object to string representation.
     *
     * @return a string representation of the object
     */
    public String toString() {
        return getLanguage().toString();
    }
}