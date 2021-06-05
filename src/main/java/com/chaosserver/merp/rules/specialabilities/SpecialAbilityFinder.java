package com.chaosserver.merp.rules.specialabilities;

import com.chaosserver.exception.NotFoundX;

/**
 * Finds a special ability from a list.
 *
 * @author Jordan Reed
 */
public class SpecialAbilityFinder {
    /** Singleton self reference. */
    private static SpecialAbilityFinder s_self;

    /** Protected constructor to force all access through the instance method. */
    protected SpecialAbilityFinder() {

    }

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static SpecialAbilityFinder instance() {
        if(s_self == null) {
            s_self = new SpecialAbilityFinder();
        }

        return s_self;
    }

    /**
     * Finds a special ability by name from the system list.
     *
     * @param name the name of the ability to find
     * @return The special ability with the given name
     * @throws NotFoundX If the ability is not found.
     */
    public static SpecialAbility findByName(String name) throws NotFoundX {
        return findByName(name, SpecialAbilities.instance());
    }

    /**
     * Finds a special ability by name from the given list.
     *
     * @param name the name of the ability to find
     * @param specialAbilities the list to search
     * @return The special ability with the given name
     * @throws NotFoundX If the ability is not found.
     */
    public static SpecialAbility findByName(String name, SpecialAbilities specialAbilities) throws NotFoundX {
        SpecialAbilityIterator specialAbilityIterator = specialAbilities.iterator();

        while(specialAbilityIterator.hasNext()) {
            SpecialAbility specialAbility = (SpecialAbility) specialAbilityIterator.next();
            if(specialAbility.getName().equals(name)) {
                return specialAbility;
            }
        }
        throw new NotFoundX("Couldn't find special ability named: " + name);
    }

}