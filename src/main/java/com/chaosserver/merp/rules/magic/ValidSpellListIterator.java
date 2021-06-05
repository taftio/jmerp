package com.chaosserver.merp.rules.magic;

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
 * The valid spell list iterator takes in a merp character along with
 * the standard iterator.  It will then use the restrictions
 * associated with each spell to see if the merp character can switch
 * to that spell without violating any of these restrictions.  It is
 * a way to get a list of spells that a character could switch to.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class ValidSpellListIterator extends SpellListIterator {
    /** The next spell list to be returned. */
    protected SpellList nextSpellList;

    /** The character being validated against. */
    protected MerpCharacter character;

    /**
     * Constructor.
     *
     * @param spellLists The spell lists to iterate over
     * @param character The character to validate each list against
     */
    public ValidSpellListIterator(SpellLists spellLists, MerpCharacter character) {
        super(spellLists);
        Assertion.isNotNull(character, "Cannot pass in a null character");
        this.character = character;
    }

    /**
     * Checks if there is another element in the iterator.
     *
     * @return If there is another element in the iterator
     */
    public boolean hasNext() {
        spells: while(super.hasNext()) {
            // Just because super has another one doesn't mean that
            // it's valid.
            nextSpellList = (SpellList) super.next();

            // Okay, now we need to try applying the spell to the
            // character and see if an exception occurs
            if( !character.isValidSpellList(nextSpellList) ) {
                continue spells;
            }

            return true;
        }
        return false;
    }

    /**
     * Gets the next element in the iterator.
     *
     * @return The next element in the iterator
     */
    public SpellList next() {
        if(nextSpellList == null) {
            if(hasNext()) {
                return nextSpellList;
            } else {
                throw new NoSuchElementException();
            }
        } else {
            SpellList tempSpellList = nextSpellList;
            nextSpellList = null;
            return tempSpellList;
        }
    }
}