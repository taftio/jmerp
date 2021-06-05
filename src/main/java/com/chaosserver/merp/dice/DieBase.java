package com.chaosserver.merp.dice;

import java.util.Random;

/**
 * This is the base implementation for dice.  It should be extened.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public abstract class DieBase {
    /** A constant value meaning the die hasn't been rolled. */
    protected static int NOT_ROLLED = -10000;

    /** Holds a random generator. */
    protected static Random s_rand;

    /** This holds the value of the die. */
    protected int m_value = NOT_ROLLED;

    /**
     * Base constructor creates a new random object if needed and
     * does an initial roll of the die.
     */
    protected DieBase() {
        if(s_rand == null) {
            s_rand = new Random();
        }
        roll();
    };

    /**
     * Returns a random number between the high and low point.
     *
     * @param high The highest to roll
     * @param low the lowest to roll
     * @return The random roll
     */
    public static int getRandomValue(int low, int high) {
        return s_rand.nextInt(high-low) + low;
    }

    /**
     * Changes the value of the die into a new roll.
     */
    protected abstract void roll();

    /**
     * Rolls a new value and gets the result.
     *
     * @return the new value
     */
    public int getRolledValue() {
        roll();
        return getValue();
    }

    /**
     * Gets the value on the die.
     *
     * @return the value of the die
     * @throws DieNotRolledRX if you get the value before the die is tossed
     */
    public int getValue() {
        if(m_value == NOT_ROLLED) {
            throw new DieNotRolledRX("You must roll a die before getting it");
        } else {
            return m_value;
        }
    }
}