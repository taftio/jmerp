package com.chaosserver.merp.rules.magic.realm.restriction;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.rules.magic.realm.Realm;

/**
 * Restricts the maximum level that a character is allowed to get in a realm.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class RealmLevelRestriction extends BaseRealmRestriction {
    /** Holds a reference to the realm being restricted. */
    private Realm m_realm;

    /** Holds the max level of spells for a realm. */
    private int m_maxLevel;

    /**
     * The setter method for the realm.
     *
     * @param realm The new realm
     * @see #getRealm
     */
    public void setRealm(Realm realm) {
        m_realm = realm;
    }

    /**
     * The getter method for the realm.
     *
     * @return the restricted realm
     * @see #setRealm
     */
    public Realm getRealm() {
        return m_realm;
    }

    /**
     * The setter method for the max level.
     *
     * @param maxLevel the max level
     * @see #getMaxLevel
     */
    public void setMaxLevel(int maxLevel) {
        m_maxLevel = maxLevel;
    }

    /**
     * The getter method for max level.
     *
     * @return the max level
     * @see #setMaxLevel
     */
    public int getMaxLevel() {
        return m_maxLevel;
    }

    /**
     * Empty Constructor.
     */
    public RealmLevelRestriction() {
    }

    /**
     * Constructor.
     *
     * @param realm The realm that's restricted
     * @param maxLevel the max level for the realm
     */
    public RealmLevelRestriction(Realm realm, int maxLevel) {
        setRealm(realm);
        setMaxLevel(maxLevel);
    }

    /**
     * Checks if the realm is valid for a character.
     *
     * @param mChar The character to check validity against
     * @return If the realm is valid for the character
     */
    public boolean isValid(MerpCharacter mChar) {
        return true;
    }

    /**
     * Gets a description of the restriction.
     *
     * @return A description of the restriction
     */
    public String getDescription() {
        return "This realm is restricted for your character";
    }
}