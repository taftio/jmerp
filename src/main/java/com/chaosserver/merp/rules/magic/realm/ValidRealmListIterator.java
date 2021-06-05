package com.chaosserver.merp.rules.magic.realm;

import java.beans.PropertyVetoException;
import java.util.NoSuchElementException;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.assertion.Assertion;
import com.chaosserver.exception.MethodNotImplementedRX;
import com.chaosserver.merp.rules.restriction.BaseCharRestriction;
import com.chaosserver.merp.rules.restriction.RestrictionList;
import com.chaosserver.merp.rules.restriction.RestrictionListIterator;

/**
 * The valid realm list iterator takes in a merp character along with
 * the standard iterator.  It will then use the restrictions
 * associated with each realm to see if the merp character can switch
 * to that realm without violating any of these restrictions.  It is
 * a way to get a list of realms that a character could switch to.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class ValidRealmListIterator extends RealmListIterator {
    /** The next valid realm. */
    protected Realm m_nextRealm;

    /** The character to validate against. */
    protected MerpCharacter m_mChar;

    /**
     * Constructor.
     *
     * @param realmList List of realms to iterator over
     * @param mChar The character to validate against
     */
    public ValidRealmListIterator(RealmList realmList, MerpCharacter mChar) {
        super(realmList);
        Assertion.isNotNull(mChar, "Cannot pass in a null character");
        m_mChar = mChar;
    }

    /**
     * Checks if the iterator has another element.
     *
     * @return if the iterator has another element
     */
    public boolean hasNext() {
        realms: while(super.hasNext()) {
            // Just because super has another one doesn't mean that
            // it's valid.
            m_nextRealm = (Realm) super.next();

            // First, cycle through all the restrictions on the realm
            //   and see if any block the realm from being applied
            RestrictionList resList = m_nextRealm.getRestrictionList();
            if(resList != null) {
                RestrictionListIterator rli = resList.iterator();
                while(rli.hasNext()) {
                    BaseCharRestriction currRes = (BaseCharRestriction) rli.next();
                    if( !currRes.isValid(m_mChar) ) {
                        continue realms;
                    }
                }
            }

            // Okay, now we need to try applying the realm to the
            // character and see if an exception occurs
            if( !isValid(m_nextRealm) ) {
                continue realms;
            }

            return true;
        }
        return false;
    }

    /**
     * Checks if the current element is a valid element.
     *
     * @param obj The object to check the validity of
     * @return If the object is valid
     */
    protected boolean isValid(Object obj) {
        return m_mChar.isValidRealm((Realm)obj);
    }

    /**
     * Gets the next element from the iterator.
     *
     * @return the next element in the iterator
     */
    public Realm next() {
        if(m_nextRealm == null) {
            if(hasNext()) {
                return (Realm) m_nextRealm;
            } else {
                throw new NoSuchElementException();
            }
        } else {
            Realm tempRealm = m_nextRealm;
            m_nextRealm = null;
            return (Realm) tempRealm;
        }
    }
}