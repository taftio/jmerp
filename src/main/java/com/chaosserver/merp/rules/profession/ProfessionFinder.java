package com.chaosserver.merp.rules.profession;

import com.chaosserver.exception.NotFoundX;

/**
 * Use this package to find a profession.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class ProfessionFinder {
    /** Singleton self reference. */
    private static ProfessionFinder s_self;

    /** Protected constructor to force all access through the instance method. */
    protected ProfessionFinder() {
    }

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static ProfessionFinder instance() {
        if(s_self == null) {
            s_self = new ProfessionFinder();
        }

        return s_self;
    }

    /**
     * Finds the profession by name from the system profession list.
     *
     * @param name The name of the stat to find
     * @return The profession with the given name
     * @throws NotFoundX If the profession cannot be found
     */
    public Profession findByName(String name) throws NotFoundX {
        return findByName(name, ProfessionList.instance());

    }

    /**
     * Finds the profession by name from the given profession list.
     *
     * @param name The name of the profession to find
     * @param professionList the professionlist to look in
     * @return The profession with the given name
     * @throws NotFoundX If the profession cannot be found
     */
    public Profession findByName(String name, ProfessionList professionList) throws NotFoundX {
        ProfessionListIterator rli = professionList.iterator();

        while(rli.hasNext()) {
            Profession currProfession = (Profession) rli.next();
            if(currProfession.getName().equals(name)) {
                return currProfession;
            }
        }
        throw new NotFoundX("Couldn't find profession named: " + name);
    }

}