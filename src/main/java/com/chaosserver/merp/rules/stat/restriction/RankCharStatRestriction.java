package com.chaosserver.merp.rules.stat.restriction;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.exception.NotFoundX;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.character.stat.CharStatFinder;
import com.chaosserver.merp.character.stat.ICharStat;
import com.chaosserver.merp.character.stat.ICharStatList;
import com.chaosserver.merp.rules.stat.Stat;

import java.beans.VetoableChangeListener;
import java.util.ListIterator;

/**
 * This restriction requires that a stat be of a certain rank.
 * For example, a Noldor Elf would have a stat restriction that
 * the Presnse stat has a rank of 1, because it must be their
 * highest stat.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class RankCharStatRestriction extends BaseStatRestriction {
    /** Property holding the rank this must be. */
    private int m_rank;

    /**
     * getter for the rank.
     *
     * @return the rank
     * @see #setRank
     */
    public int getRank() {
        return m_rank;
    }

    /**
     * setter for the rank.
     *
     * @param rank What to the rank to
     * @see #getRank
     */
    public void setRank(int rank) {
        m_rank = rank;
    }

    /**
     * Empty Constructor.
     */
    public RankCharStatRestriction() {
    }

    /**
     * Constructor to make the restriction.
     *
     * @param stat The stat being restricted
     * @param rank The rank it must be
     */
    public RankCharStatRestriction(Stat stat, int rank) {
        setStat(stat);
        setRank(rank);
    }

    // Inhertics javadoc from baseclass
    public boolean isValid(MerpCharacter mChar) {
        Assertion.isNotNull(mChar, "mChar cannot be null");
        if(mChar.getCharStatList() == null) {
            // If there is no stat list, no restriction is violated
            return true;
        }

        try {
            ICharStatList charStatList = mChar.getCharStatList();
            ICharStat cs = CharStatFinder.findByName(getStat().getName(), charStatList);
            if(charStatList.getRank(cs) <= getRank()) {
                return true;
            } else {
                return false;
            }
        } catch (NotFoundX e) {
            System.out.println("Restriction on " + getStat().getName() + ", yet no stat by this name exists in list");
            return false;
        }
    }

    // Inherits javadoc from baseclass
    public String getDescription() {
        return "You are required to make the stat [BLAH] be the [RANK] in your list";
    }

}