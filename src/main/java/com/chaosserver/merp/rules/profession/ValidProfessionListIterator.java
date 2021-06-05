package com.chaosserver.merp.rules.profession;

import java.beans.PropertyVetoException;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.assertion.Assertion;
import com.chaosserver.exception.MethodNotImplementedRX;
import com.chaosserver.merp.rules.restriction.BaseCharRestriction;
import com.chaosserver.merp.rules.restriction.RestrictionList;
import com.chaosserver.merp.rules.restriction.RestrictionListIterator;

/**
 * The valid profession list iterator takes in a merp character along with
 * the standard iterator.  It will then use the restrictions
 * associated with each profession to see if the merp character can switch
 * to that profession without violating any of these restrictions.  It is
 * a way to get a list of professions that a character could switch to.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class ValidProfessionListIterator extends ProfessionListIterator {
    /** Holds the next valid profession to return. */
    protected Profession m_nextProfession;

    /** Holds the character being validated against. */
    protected MerpCharacter m_mChar;

    /**
     * Constructor.
     *
     * @param professionList The profession collection to iterate over
     * @param mChar The character to validate against
     */
    public ValidProfessionListIterator(ProfessionList professionList, MerpCharacter mChar) {
        super(professionList);
        Assertion.isNotNull(mChar, "Cannot pass in a null character");
        m_mChar = mChar;
    }

    /**
     * Checks if the iterator has another element.
     *
     * @return if the iterator has another element
     */
     public boolean hasNext() {
        professions: while(super.hasNext()) {
            // Just because super has another one doesn't mean that
            // it's valid.
            m_nextProfession = (Profession) super.next();

            // First, cycle through all the restrictions on the profession
            //   and see if any block the profession from being applied
            RestrictionList resList = m_nextProfession.getRestrictionList();
            RestrictionListIterator rli = resList.iterator();
            while(rli.hasNext()) {
                BaseCharRestriction currRes = (BaseCharRestriction) rli.next();
                if( !currRes.isValid(m_mChar) ) {
                    continue professions;
                }
            }

            // Okay, now we need to try applying the profession to the
            // character and see if an exception occurs
            if( !m_mChar.isValidProfession(m_nextProfession) ) {
                continue professions;
            }

            return true;
        }
        return false;
    }

    /**
     * Gets the next element from the iterator.
     *
     * @return the next element in the iterator
     */
    public Profession next() {
        if(m_nextProfession == null) {
            if(hasNext()) {
                return (Profession) m_nextProfession;
            } else {
                throw new NoSuchElementException();
            }
        } else {
            Profession tempProfession = m_nextProfession;
            m_nextProfession = null;
            return (Profession) tempProfession;
        }
    }
}