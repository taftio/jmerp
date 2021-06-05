package com.chaosserver.merp.rules.stat.modifier;

import com.chaosserver.merp.rules.stat.Stat;

/**
 * Base class for modifiers that affect a stat bonus.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public abstract class BaseStatModifier implements IStatModifier {
    /** The stat being modified. */
    protected Stat m_stat;

    /** The value of the modification. */
    protected int m_value;

    /**
     * Setter for the stat being modified.
     *
     * @param stat The stat being modified
     * @see #getStat
     */
    public void setStat(Stat stat) {
        m_stat = stat;
    }

    // Inherits from interace
    public Stat getStat() {
        return m_stat;
    }

    /**
     * Setter for the value of the modification.
     *
     * @param value The value of the modification
     * @see #getValue
     */
    public void setValue(int value) {
        m_value = value;
    }

    // Inherits javadoc from interface
    public int getValue() {
        return m_value;
    }

}