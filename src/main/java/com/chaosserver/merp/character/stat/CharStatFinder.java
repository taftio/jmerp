package com.chaosserver.merp.character.stat;

import java.util.ListIterator;

import com.chaosserver.exception.NotFoundX;
import com.chaosserver.assertion.Assertion;
import com.chaosserver.merp.rules.stat.Stat;

/**
 * Finds a ICharStat inside of a ICharStatList.
 *
 * Given an ICharStatList this will search through it to find the
 * associated Characater Statistic
 *
 * @pattern Finder
 * @author Jordan Reed
 * @version 1.0
 */
public class CharStatFinder {
    /**
     * Default constructor.
     */
    private CharStatFinder() {
    }

    /**
     * Finds the stat by name from the given CharStat list.
     *
     * @param name The name of the stat to find
     * @param charStatList the ICharStatList to look in
     * @return the character staff in the list with the given name
     * @throws NotFoundX If the character stat cannot be found
     */
    public static ICharStat findByName(String name, ICharStatList charStatList) throws NotFoundX {
        Assertion.isNotNull(name, "Must pass a name to look for");
        Assertion.isNotNull(charStatList, "Must pass a stat list to look through");

        ListIterator li = charStatList.iterator();

        while(li.hasNext()) {
            ICharStat currStat = (ICharStat) li.next();
            if(currStat.getStat().getName().equals(name)) {
                return currStat;
            }
        }
        throw new NotFoundX("Couldn't find stat named: " + name);
    }

    /**
     * Finds the stat by the system stat object.
     *
     * @param stat The sytem stat reference
     * @param charStatList the ICharStatList to look in
     * @return the character staff in the list for the given stat
     * @throws NotFoundX If the character stat cannot be found
     */
    public static ICharStat findByStat(Stat stat, ICharStatList charStatList) throws NotFoundX {
        Assertion.isNotNull(stat, "Must give a stat to look for");
        Assertion.isNotNull(charStatList, "Must pass a char stat list to look through");
        ListIterator li = charStatList.iterator();

        while(li.hasNext()) {
            ICharStat currStat = (ICharStat) li.next();
            if(currStat.getStat().equals(stat)) {
                return currStat;
            }
        }
        throw new NotFoundX("Couldn't find stat object named: " + stat.getName());
    }

}