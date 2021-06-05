package com.chaosserver.merp.character.stat.standard;

import java.util.ArrayList;

import com.chaosserver.merp.character.stat.CharStatListIterator;
import com.chaosserver.merp.character.stat.ICharStatList;
import com.chaosserver.merp.character.stat.ICharStatListFactory;
import com.chaosserver.merp.dice.ClosedPercentileDice;
import com.chaosserver.merp.rules.stat.Stat;
import com.chaosserver.merp.rules.stat.StatList;
import com.chaosserver.merp.rules.stat.StatListIterator;

/**
 * This is the standard char stat list factory.  It is used to create
 * a standard stat list.  The rules governing this list are:<br>
 *   1) Rolls must be between 20-100
 *
 * <p>
 * This object is a static singleton.  All methods on this object are static and
 * should be called directory, but it is possible to make a single instance
 * if desired.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class StandardCharStatListFactory implements ICharStatListFactory {
    /** Singleton self reference. */
    private static StandardCharStatListFactory s_self;

    /** Contains the minimum value of any single stat rolled. */
    public static int s_minValue = 20;

    /** Contains the minimum total of all totaled stats. */
    public static int s_minTotal = 0;

    /** Protected constructor to force all access through the instance method. */
    protected StandardCharStatListFactory() {
    }

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static StandardCharStatListFactory instance() {
        if(s_self==null) {
            s_self = new StandardCharStatListFactory();
        }

        return s_self;
    }

    /**
     * Creates a standard stat list for a character.
     * <p>
     * Creates an empty list and then call <code>rollStatList</code> on it.
     * </p>
     *
     * @return The stat list that's created
     * @see StandardCharStatListFactory#rollStatList(ICharStatList)
     */
    public ICharStatList createCharStatList() {
        StatList statList = StatList.instance();
        ArrayList charStats = new ArrayList(statList.size());

        Stat currStat;
        StatListIterator slIter = (StatList.instance()).iterator();

        // Cycle through the statlist and create stuff
        while(slIter.hasNext()) {
            currStat = (Stat) slIter.next();
            charStats.add(currStat.getID(), new StandardCharStat(currStat, rollBasicStat()));
        }

        StandardCharStatList csl = new StandardCharStatList(charStats);

        rollStatList(csl);

        return csl; //new StandardCharStatList(charStats);
    }

    /**
     * Updates the standard statlist for a character.
     * <p>
     * Calls <code>rollStatList</code>.
     * </p>
     *
     * @see StandardCharStatListFactory#rollStatList(ICharStatList)
     */
    public void createCharStatList(ICharStatList csl) {
        rollStatList(csl);
    }

    /**
     * Does a single stat role.
     *
     * @return The value of the roll
     * @see com.chaosserver.merp.dice.ClosedPercentileDice
     */
    protected int rollBasicStat() {
        ClosedPercentileDice statDie = new ClosedPercentileDice();

        statDie.roll();
        return statDie.getValue();
    }

    /**
     * Rolls the stats for each item in the stat list.
     * <p>
     * <ul>
     *   <li>All stats must be greater than <code>s_minValue</code></li>
     *   <li>The total of all the totalled stats must be greater than
     *       <code>s_minTotal</code></li>
     * </ul>
     *
     * @param csl The stat list to rolls stats on
     */
    protected void rollStatList(ICharStatList csl) {

        StatList statList = StatList.instance();
        CharStatListIterator cslIter;
        int tempRoll = 0;
        int tempTotal = 0;

        // Keep rerolling all of the stats until you surpass the total
        do {
            tempTotal = 0;
            cslIter = (CharStatListIterator) csl.iterator();

            // Iterate through each stat and roll for it
            while(cslIter.hasNext()) {
                StandardCharStat currCharStat = (StandardCharStat) cslIter.next();
                Stat currStat = currCharStat.getStat();
                tempRoll = 0;

                // Continue to reroll the stat until you get a larger value
                //   the minumum for the stat
                do {
                    tempRoll = rollBasicStat();
                } while(tempRoll<currStat.getMinVal());

                // If this stat counts towards the total, then add it
                if(currStat.isTotaled()) {
                    tempTotal += tempRoll;
                }

                currCharStat.setValue(tempRoll, tempRoll);
            }

        } while(tempTotal<statList.getMinTotal());
    }
}