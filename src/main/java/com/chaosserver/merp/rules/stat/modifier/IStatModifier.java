package com.chaosserver.merp.rules.stat.modifier;

import com.chaosserver.merp.rules.stat.Stat;

/**
 * Interface for objects that will modified a stats bonus.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public interface IStatModifier {
    /** Racial bonus modification. */
    public static String RACE_BONUS_STR = "RACE";

    /** Enumeration for a racial bonus modification. */
    public static int RACE_BONUS = 0;

    /**
     * Gets the type of bonus.
     *
     * @return the type of bonus
     */
    public int getBonusType();

    /**
     * Getter for the stat being modified.
     *
     * @return The stat being modified
     */
    public Stat getStat();

    /**
     * Getter for the value of the modification.
     *
     * @return The value of the modification
     */
    public int getValue();
}