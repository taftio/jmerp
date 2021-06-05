package com.chaosserver.merp.character.stat.standard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.Serializable;

import com.chaosserver.merp.character.stat.CharStat;
import com.chaosserver.merp.rules.stat.Stat;

/**
 * This holds a characters value in a single stat.
 *
 * @author Jordan Reed
 * @version 1.0
 */

public class StandardCharStat extends CharStat
                              implements Serializable,
                                         PropertyChangeListener,
                                         VetoableChangeListener {

    // PROPERTIES ///////////////////////////////////////////////////

    /** Property: Holds the original value of the stat. */
    private int m_origValue;

    /** Property change event. */
    public static String P_ORIG_VALUE = "P_ORIG_VALUE";

    /** The maximum value that this stat can hold. */
    protected static int MAX_VALUE = 100;

    /** The minimum value that this stat can be. */
    protected static int MIN_VALUE = 1;


    /**
     * Getter for original value property.
     *
     * @return the original value property
     */
    int getOrigValue() {
        return m_origValue;
    }

    /**
     * Setter for the original value property.
     *
     * @origValue the orginal value property
     */
    private void setOrigValue(int origValue) {
        int old_origValue = getOrigValue();
        m_origValue = origValue;
        changes.firePropertyChange(P_ORIG_VALUE, old_origValue, m_origValue);
    }


    /** Property change event. */
    public static String P_ADJUST_AMOUNT = "P_ADJUST_AMOUNT";

    /**
     * Setter for the adjusted amount from the original value.
     *
     * @param adjustAmount the adjustAmount property
     * @throws PropertyVetoException If there is restriction on adjusting the stat
     * @see #getAdjustAmount
     */
    public void setAdjustAmount(int adjustAmount) throws PropertyVetoException {
        int old_adjustAmount = getAdjustAmount();
        vetoes.fireVetoableChange(P_ADJUST_AMOUNT,
                                  getAdjustAmount(),
                                  adjustAmount);

        setValue(getOrigValue() + adjustAmount);
        changes.firePropertyChange(P_ADJUST_AMOUNT, old_adjustAmount, getAdjustAmount());
    }

    /**
     * Getter for the adjust amount from the original value.
     *
     * @return the adjustAmount property
     * @see #setAdjustAmount
     */
    public int getAdjustAmount() {
        return getValue() - getOrigValue(); // m_adjustAmount;
    }

    /**
     * Setter for the value property.
     *
     * @param value The stat value
     */
    public void setValue(int value) {
        int old_adjustAmount = getAdjustAmount();
        super.setValue(value);
        changes.firePropertyChange(P_ADJUST_AMOUNT, old_adjustAmount, getAdjustAmount());
    }

    /**
     * Setter for the value of the stat.
     *
     * @param value The value of the stat
     * @param origValue The original value of the stat
     */
    void setValue(int value, int origValue) {
        setOrigValue(origValue);
        setValue(value);
        // setAdjustAmount(value - origValue);
    }

    // END PROPERTIES ///////////////////////////////////////////////


    //  CONSTRUCTORS ////////////////////////////////////////////////

    /**
     * This is the main public construct for a StandardCharStat object.
     *
     * @param stat The stat this object holds the value of
     * @param value The value of the stat
     */
    public StandardCharStat(Stat stat, int value) {
        super(stat, value);
        setOrigValue(value);
        try {
            setAdjustAmount(0);
        } catch (PropertyVetoException e) {
            System.out.println("StandardCharStat() - "
                + "Caught PropertyVetoException");
        }
        addVetoableChangeListener(this);
        addPropertyChangeListener(this);
    }
    //  END CONSTRUCTORS ////////////////////////////////////////////

    // Inherits from interface
    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt);

        // If the value has changed, it means that the virtual adjust
        // amount property has been changed
        if(evt.getPropertyName().equals(P_VALUE)) {
            changes.firePropertyChange(P_ADJUST_AMOUNT,
                                       -(getAdjustAmount()+1),
                                       getAdjustAmount());
        }

    }

    // Inherits from interface
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        // If the adjust amount has changed, there may be reasons to veto that change.
        if(evt.getPropertyName().equals(P_ADJUST_AMOUNT)) {
            int maxAdjust = getStat().getMaxAdjust();
            int newAdjust = ((Integer)evt.getNewValue()).intValue();
            if(newAdjust < -maxAdjust) {
                throw new PropertyVetoException("Adjust too small.  "
                    + newAdjust + " smaller than max ("
                    + (-maxAdjust), evt);

            } else if (newAdjust > maxAdjust) {
                throw new PropertyVetoException("Adjust too large.  "
                    + newAdjust + " smaller than max ("
                    + (-maxAdjust), evt);
            }

            if(getOrigValue() + newAdjust > MAX_VALUE) {
                throw new PropertyVetoException("Stat too large", evt);
            } else if (getOrigValue() + newAdjust < MIN_VALUE) {
                throw new PropertyVetoException("Stat too small", evt);
            }

        }

    }

    /**
     * Converts the stat to a string.
     *
     * @return String version of stat
     */
    public String toString() {
        return getStat() + " (" + getValue() + ")";
    }
}