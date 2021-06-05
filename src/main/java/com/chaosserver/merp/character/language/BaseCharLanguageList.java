package com.chaosserver.merp.character.language;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.logging.CategoryCache;
import com.chaosserver.merp.character.language.MaxRanksExceededX;
import com.chaosserver.merp.rules.language.Language;

/**
 * A list of all the <code>BaseCharLanguage</code>s a character has.
 * <p>
 * This list is a copy of the base languages from the race
 * and then allows for manipulation.  User the CharLanguageFactory
 * to build this list.
 * </p>
 * <p>
 * Once this list is done being manipulated, use the CharLanguageFactory
 * again to generate the CharLanguageList which actually represents the
 * list moving forward.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 *
 * @see com.chaosserver.merp.character.language.CharLanguage
 */
public class BaseCharLanguageList extends CharLanguageList implements VetoableChangeListener, PropertyChangeListener {
    /** Reference to the property change supporter. */
    protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /** Number of free ranks to distribute to languages. */
    protected int freeRanks;

    /** Property for free ranks. */
    public static String P_FREE_RANKS = "BaseCharLanguageList.freeRanks";

    /**
     * Setter for the number of free ranks.
     *
     * @param freeRanks The number of free ranks
     * @see #getFreeRanks
     */
    protected void setFreeRanks(int freeRanks) {
        int old_freeRanks = getFreeRanks();
        this.freeRanks = freeRanks;
        changes.firePropertyChange(P_FREE_RANKS, old_freeRanks, getFreeRanks());
    }

    /**
     * Getter for the number of free ranks.
     *
     * @return The number of free ranks
     * @see #setFreeRanks
     */
    public int getFreeRanks() {
        return this.freeRanks;
    }

    /**
     * Constructor takes in the number of free ranks to add the languages.
     *
     * @param freeRanks Number of free ranks
     */
    public BaseCharLanguageList(int freeRanks) {
        setFreeRanks(freeRanks);
    }

    /**
     * Sets the language list.
     * <p>
     * This method should be avoided as it is not type safe.  Instead,
     * use the add method to add the BaseCharLanguage objects one at
     * a time.
     * </p>
     *
     * @param charLanguageList The collection of <code>BaseCharLanguage</code>'s
     */
    public void setCharLanguageList(Collection charLanguageList) {
        this.charLanguageList = charLanguageList;

        Iterator iterator = charLanguageList.iterator();
        BaseCharLanguage baseCharLanguage;
        while(iterator.hasNext()) {
            baseCharLanguage = (BaseCharLanguage) iterator.next();
            baseCharLanguage.addVetoableChangeListener(this);
            baseCharLanguage.addPropertyChangeListener(this);
        }
    }

    /**
     * Adds a BaseCharLanguage to the list.
     *
     * @param charLanguage The language to add to the list
     */
    public void add(BaseCharLanguage charLanguage) {
        super.add(charLanguage);
        charLanguage.addVetoableChangeListener(this);
        charLanguage.addPropertyChangeListener(this);
    }

    /**
     * Adds ranks to a given base language.
     *
     * @param languageNumber The number of the language in the list to add ranks to
     * @param ranks The number of ranks to add
     * @throws MaxRanksExceededX If you have added more than the possible amount
     */
    public void addRanks(int languageNumber, int ranks) throws MaxRanksExceededX {
        Assertion.isTrue(languageNumber >= 0, "Must give a positive language number");
        Assertion.isTrue(languageNumber < size(), "Must give a langauge number in range");

        int counter = -1;
        CharLanguageListIterator charLanguageListIterator = this.iterator();
        while(charLanguageListIterator.hasNext()) {
            counter++;
            BaseCharLanguage currBaseCharLanguage = (BaseCharLanguage) charLanguageListIterator.next();
            if(counter==languageNumber) {
                currBaseCharLanguage.addRanks(ranks);
            }
        }
    }

    /**
     * Adds ranks to a given base language.
     *
     * @param language The language in the list to add ranks to
     * @param ranks The number of ranks to add
     * @throws MaxRanksExceededX If you have added more than the possible amount
     */
    public void addRanks(Language language, int ranks) throws MaxRanksExceededX {
        boolean success = false;
        CharLanguageListIterator charLanguageListIterator = this.iterator();
        while(charLanguageListIterator.hasNext()) {
            BaseCharLanguage currBaseCharLanguage = (BaseCharLanguage) charLanguageListIterator.next();
            if(currBaseCharLanguage.getLanguage().equals(language)) {
                currBaseCharLanguage.addRanks(ranks);
                success = true;
            }
        }

        if(!success) {
            Assertion.shouldNotReach("Tried to add rank to a language that's not in the list");
        }
    }

    /**
     * Subtracks a single rank from the free ranks.
     */
    public void subtrackFreeRank() {
        subtrackFreeRanks(1);
    }

    /**
     * Subtracts a certain number of free ranks.
     *
     * @param ranks Number of ranks to subtract
     */
    public void subtrackFreeRanks(int ranks) {
        setFreeRanks(getFreeRanks() - ranks);
    }

    // Inherits from interface
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        if(CharLanguage.P_RANKS.equals(evt.getPropertyName())) {
            CategoryCache.getInstance(this).debug("P_RANKS vetoable change has been fired");
            int rankChange = ((Integer) evt.getNewValue()).intValue() - ((Integer) evt.getOldValue()).intValue();
            CategoryCache.getInstance(this).debug("Rank has changed by: " + rankChange);
            //if(rankChange > getFreeRanks()) {
            //    throw new PropertyVetoException("Cannot reduce free ranks below zero!", evt);
            //}
        }
    }

    // Inherits from interface
    public void propertyChange(PropertyChangeEvent evt) {
        if(CharLanguage.P_RANKS.equals(evt.getPropertyName())) {
            CategoryCache.getInstance(this).debug("P_RANKS property change has been fired");
            int rankChange = ((Integer) evt.getNewValue()).intValue() - ((Integer) evt.getOldValue()).intValue();
            CategoryCache.getInstance(this).debug("Rank has changed by: " + rankChange);
            subtrackFreeRanks(rankChange);
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

}