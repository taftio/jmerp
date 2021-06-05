package com.chaosserver.merp.dice;

/**
 * This is a closed percetine die.  It is capable of rolling between 1 and 100.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class ClosedPercentileDice extends DieBase {
    /**
     * Empty constructor.
     */
    public ClosedPercentileDice() {
    }

    // Inherits from base
    public void roll() {
        m_value = DieBase.getRandomValue(1, 101);
        if(m_value <= 0 || m_value > 100) {
            System.out.println("Die Roll Out of Range: "+m_value);
        }
    }

}