package com.chaosserver.merp.rules.stat.restriction;

import com.chaosserver.merp.rules.restriction.BaseCharRestriction;
import com.chaosserver.merp.rules.stat.Stat;

/**
 * This class represents a stat restriction.  It will define whether
 * or not a statlist is valid give this restriction.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public abstract class BaseStatRestriction extends BaseCharRestriction {
    /** Holds a reference to the stat the restriction is on. */
    protected Stat m_stat;

    /**
     * getter for the stat.
     *
     * @return Stat this is restricting
     * @see #setStat
     */
    public Stat getStat() {
        return m_stat;
    }

    /**
     * setter for the stat.
     *
     * @param stat The new value of stat
     * @see #getStat
     */
    public void setStat(Stat stat) {
        m_stat = stat;
    }

}