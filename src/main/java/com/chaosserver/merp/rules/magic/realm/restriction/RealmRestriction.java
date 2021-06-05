package com.chaosserver.merp.rules.magic.realm.restriction;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.rules.magic.realm.Realm;

/**
 * Restricts a character for being in a realm.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class RealmRestriction extends BaseRealmRestriction {
    /** Holds a reference to the realm being restricted. */
    private Realm m_realm;

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
     * Empty Constructor.
     */
    public RealmRestriction() {
    }

    /**
     * Constructor.
     *
     * @param realm The realm that's restricted
     */
    public RealmRestriction(Realm realm) {
        setRealm(realm);
    }

    // Inherits from baseclass
    public boolean isValid(MerpCharacter mChar) {
        Assertion.isNotNull(mChar, "mChar cannot be null");

        // First check if the current character is valid
        if(mChar.getRealm() != null) {
            if(getRealm().equals(mChar.getRealm())) {
                return false;
            }
        }
        return true;
    }

    // Inherits from baseclass
    public String getDescription() {
        return "This realm is restricted for your character";
    }

    // Inherits from interface
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        if(MerpCharacter.P_REALM.equals(evt.getPropertyName()) && evt.getNewValue() != null) {
            if(evt.getNewValue().equals(getRealm())) {
                throw new PropertyVetoException("Restricted Realm!", evt);
            }
        }
    }

}