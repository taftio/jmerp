package com.chaosserver.merp.rules.race;

import com.chaosserver.exception.NotFoundX;

/**
 * Use this package to find a race.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class RaceFinder {
    /** Singleton self reference. */
    private static RaceFinder s_self;

    /** Protected constructor to force all access through the instance method. */
    protected RaceFinder() {

    }

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static RaceFinder instance() {
        if(s_self == null) {
            s_self = new RaceFinder();
        }

        return s_self;
    }

    /**
     * Finds the race by name from the system race list.
     *
     * @param name The name of the stat to find
     * @return the race with the given name
     * @throws NotFoundX If the Race cannot be found
     */
    public static Race findByName(String name) throws NotFoundX {
        return findByName(name, RaceList.instance());

    }

    /**
     * Finds the race by name from the given race list.
     *
     * @param name The name of the race to find
     * @param raceList the racelist to look in
     * @return the race with the given name
     * @throws NotFoundX If the Race cannot be found
     */
    public static Race findByName(String name, RaceList raceList) throws NotFoundX {
        RaceListIterator rli = raceList.iterator();

        while(rli.hasNext()) {
            Race currRace = (Race) rli.next();
            if(currRace.getName().equals(name)) {
                return currRace;
            }
        }
        throw new NotFoundX("Couldn't find race named: " + name);
    }

}