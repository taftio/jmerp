package com.chaosserver.merp.dice;

/**
 * Your good old standard d10.  It will roll values between 1 and 10.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class TenSidedDie extends DieBase {
    /**
     * Constructor.
     */
    public TenSidedDie() {
    }

    /**
     * Sets the die value to between 1 and 10.
     */
    public void roll() {
        m_value = DieBase.getRandomValue(1, 11);
        if(m_value <= 0 || m_value > 10) {
            System.out.println("Die Roll Out of Range: "+m_value);
        }
    }
}