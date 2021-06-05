package com.chaosserver.merp.character.stat;

import java.beans.PropertyChangeListener;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.rules.stat.Stat;

/**
 * Basic representation of a read-only Character Stat.
 *
 * In this basic implementation the character stat is read only.
 * It is probably sufficient for a character once the character
 * creation process is over since character statistical values
 * are, for the most part, immutable.
 */
public interface ICharStat {
    /** Names the property for Stat. */
    public static String P_STAT = "STAT";

    /** Names the property for Value. */
    public static String P_VALUE = "P_VALUE";

    /**
     * Getter method for the Stat property.
     *
     * @return the Stat
     */
    public Stat getStat();

    /**
     * Getter method for the value of the Stat.
     *
     * @return The stat's value
     */
    public int getValue();

    /**
     * Getter value for the adjustment amount.
     *
     * @return The amount the stat has been adjusted
     */
    public int getAdjustAmount();

    /**
     * Adjusts the stat.
     *
     * @param amount amount to adjust the stat by
     */
    public void adjust(int amount);

    /**
     * Returns the bonus for this skill.
     *
     * @return Stat bonus (See BT-1)
     */
    public int getNormalBonus();

    /**
     * Gets the racial bonus for this stat.
     * <p>
     * Should return 0 if a race has not been specified.
     * </p>
     *
     * @return the racial bonus
     */
    public int getRaceBonus();

    /**
     * Gets the total bonus.
     * <p>
     * This bonus should include the results of the normal bonus
     * plus the racial bonus.
     * </p>
     *
     * @return The total bonus
     */
    public int getTotalBonus();

    /**
     * Sets the character that this stat applies to.
     *
     * @param character The character this stat is on
     */
    public void setCharacter(MerpCharacter character);

    /**
     * Add a PropertyChangeListener to the listener list. The listener is registered for all properties.
     *
     * @param l The PropertyChangeListener to be added
     * @see #removePropertyChangeListener
     */
    public void addPropertyChangeListener(PropertyChangeListener l);

    /**
     * Remove a PropertyChangeListener from the listener list. This removes a PropertyChangeListener
     * that was registered for all properties.
     *
     * @param l The PropertyChangeListener to be removed
     * @see #addPropertyChangeListener
     */
    public void removePropertyChangeListener(PropertyChangeListener l);
}