package com.chaosserver.merp.rules.stat;

import com.chaosserver.exception.NotFoundX;

/**
 * Use this package to find a stat.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class StatFinder {
    /** Singleton self reference. */
    private static StatFinder s_self;

    /**
     * The main constructor is private.  It's a singleton.
     */
    private StatFinder() {

    }

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static StatFinder instance() {
        if(s_self == null) {
            s_self = new StatFinder();
        }

        return s_self;
    }

    /**
     * Finds the stat by name from the system statlist.
     *
     * @param name The name of the stat to find
     * @return The stat with the given name
     * @throws NotFoundX If the the stat cannot be found
     */
    public static Stat findByName(String name) throws NotFoundX {
        return findByName(name, StatList.instance());

    }

    /**
     * Finds the stat by name from the given stat list.
     *
     * @param name The name of the stat to find
     * @param statList the statlist to look in
     * @return The stat with the given name
     * @throws NotFoundX If the the stat cannot be found
     */
    public static Stat findByName(String name, StatList statList) throws NotFoundX {
        StatListIterator sli = statList.iterator();

        while(sli.hasNext()) {
            Stat currStat = (Stat) sli.next();
            if(currStat.getName().equals(name)) {
                return currStat;
            }
        }
        throw new NotFoundX("Couldn't find stat named: " + name);
    }

}