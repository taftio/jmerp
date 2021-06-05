package com.chaosserver.merp.rules.race;

import java.beans.PropertyVetoException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.assertion.Assertion;
import com.chaosserver.exception.MethodNotImplementedRX;
import com.chaosserver.merp.rules.restriction.BaseCharRestriction;
import com.chaosserver.merp.rules.restriction.RestrictionList;
import com.chaosserver.merp.rules.restriction.RestrictionListIterator;

/**
 * The valid race list iterator takes in a merp character along with
 * the standard iterator.  It will then use the restrictions
 * associated with each race to see if the merp character can switch
 * to that race without violating any of these restrictions.  It is
 * a way to get a list of races that a character could switch to.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class ValidRaceListIterator extends RaceListIterator {
    /** Next valid race. */
    protected Race m_nextRace;

    /** Character to validate the race against. */
    protected MerpCharacter m_mChar;

    /**
     * Constructor.
     *
     * @param raceListIter The iterator to go over
     * @param mChar The character to validate each race against
     */
    public ValidRaceListIterator(Iterator raceListIter, MerpCharacter mChar) {
        super(raceListIter);
        Assertion.isNotNull(mChar, "Cannot pass in a null character");
        m_mChar = mChar;
    }

    /**
     * Checks if the iterator has another element.
     *
     * @return if the iterator has another element
     */
    public boolean hasNext() {
        races: while(super.hasNext()) {
            // Just because super has another one doesn't mean that
            // it's valid.
            m_nextRace = (Race) super.next();

            // First get the restriction list for the race and see if
            // any of the restrictions are a problem for the current
            // character.
            RestrictionList resList = m_nextRace.getRestrictionList();
            if(resList != null) {
                RestrictionListIterator rli = resList.iterator();
                while(rli.hasNext()) {
                    BaseCharRestriction currRes = (BaseCharRestriction) rli.next();
                    if( !currRes.isValid(m_mChar) ) {
                        continue races;
                    }
                }
            }

            // The race has no objections to the character
            // Check if the character has any objections to the race
            if( !m_mChar.isValidRace(m_nextRace) ) {
                continue races;
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
    public Race next() {
        if(m_nextRace == null) {
            if(hasNext()) {
                return (Race) m_nextRace;
            } else {
                throw new NoSuchElementException();
            }
        } else {
            Race tempRace = m_nextRace;
            m_nextRace = null;
            return (Race) tempRace;
        }
    }
}