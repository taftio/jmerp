package com.chaosserver.merp.character.skill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.chaosserver.merp.rules.skill.Skill;
import com.chaosserver.merp.dice.TenSidedDie;

/**
 * Holds a body development skill.
 * <p>
 * The body development is different than the standard CharSkill that
 * a character would have.  When a rank is added to this skill, a d10
 * is rolled to determine the actual value the rank will be.  It is
 * then held internally in a collection.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class BodyDevelopmentCharSkill extends CharSkill implements ICharSkill {
    /** Reference to a d10 for rolling new values. */
    protected transient TenSidedDie m_Die = new TenSidedDie();

    /** Collection holding the values of each rank. */
    protected Collection m_bodyDevelopmentValues;

    /**
     * Constructor to build a full body development skill.
     *
     * @param skill The skill to wrap around.
     * @param ranks The ranks a character has in this skill.
     */
    public BodyDevelopmentCharSkill(Skill skill, int ranks) {
        super(skill, ranks);

        this.m_bodyDevelopmentValues = new ArrayList();
        this.addDevelopmentValues(getRanks());
    }

    /**
     * Setter for bodyDevelopmentValues.
     *
     * @param bodyDevelopmentValues Value of bodyDevelopmentValues
     * @see #getBodyDevelopmentValues
     */
    public void setBodyDevelopmentValues(Collection bodyDevelopmentValues) {
        this.m_bodyDevelopmentValues = bodyDevelopmentValues;
    }

    /**
     * Getter for bodyDevelopmentValues.
     *
     * @return Value of bodyDevelopmentValues
     * @see #setBodyDevelopmentValues
     */
    public Collection getBodyDevelopmentValues() {
        return this.m_bodyDevelopmentValues;
    }

    /**
     * Returns the total value of all the ranks.
     * <p>
     * This differs from the standard method which will return
     * a number with 5 points per rank.
     * </p>
     *
     * @return Total value of in this skill
     */
    public int getRankBonus() {
        int total = 0;

        Iterator iter = m_bodyDevelopmentValues.iterator();
        while(iter.hasNext()) {
            total += ((Integer)iter.next()).intValue();
        }

        return total;
    }

    // javadoc inherits from interface
    public void addRanks(int ranks) {
        addDevelopmentValues(ranks);
        super.addRanks(ranks);
    }

    /**
     * When adding ranks, this will roll a d10 for each
     * new rank and add that to the internal history of
     * rolls.
     *
     * @param valueCount The number of ranks to add
     */
    protected void addDevelopmentValues(int valueCount) {
        for(int i=0; i<valueCount; i++) {
            m_bodyDevelopmentValues.add(new Integer(m_Die.getRolledValue()));
        }
    }
}