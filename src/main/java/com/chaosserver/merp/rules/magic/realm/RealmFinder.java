package com.chaosserver.merp.rules.magic.realm;

import com.chaosserver.exception.NotFoundX;

/**
 * Use this package to find a realm.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class RealmFinder {
    /** Singleton self reference. */
    private static RealmFinder s_self;

    /** Protected constructor to force all access through the instance method. */
    protected RealmFinder() {
    }

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static RealmFinder instance() {
        if(s_self == null) {
            s_self = new RealmFinder();
        }

        return s_self;
    }

    /**
     * Finds the realm by name from the system realm list.
     *
     * @param name The name of the stat to find
     * @return the Realm with the given name
     * @throws NotFoundX If the realm cannot be found
     */
    public Realm findByName(String name) throws NotFoundX {
        return findByName(name, RealmList.instance());

    }

    /**
     * Finds the realm by name from the given realm list.
     *
     * @param name The name of the realm to find
     * @param realmList the realmlist to look in
     * @return the Realm with the given name
     * @throws NotFoundX If the realm cannot be found
     */
    public Realm findByName(String name, RealmList realmList) throws NotFoundX {
        RealmListIterator rli = realmList.iterator();

        while(rli.hasNext()) {
            Realm currRealm = (Realm) rli.next();
            if(currRealm.getName().equals(name)) {
                return currRealm;
            }
        }
        throw new NotFoundX("Couldn't find realm named: " + name);
    }

}