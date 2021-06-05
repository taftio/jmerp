package com.chaosserver.merp.character.skill;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;

import com.chaosserver.logging.CategoryCache;
import com.chaosserver.assertion.Assertion;
import com.chaosserver.merp.rules.skill.modifier.ISkillModifier;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.character.stat.CharStatFinder;
import com.chaosserver.merp.character.stat.ICharStat;
import com.chaosserver.merp.rules.skill.Skill;
import com.chaosserver.exception.NotFoundX;

/**
 * Information on a characters ability in a single <code>Skill</code>.
 *
 * @author Jordan Reed
 * @version 1.0
 *
 * @see com.chaosserver.merp.rules.skill.Skill
 */
public class CharSkill implements ICharSkill, PropertyChangeListener, Comparable {
    /** Reference to the property change supporter. */
    protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /** Ranks in a skill. */
    protected int m_ranks;

    /** Skill the ranks are in. */
    protected Skill m_skill;

    /**
     * Possible reference to the Character skill list that contains
     * this skill.
     */
    protected CharSkillList m_charSkillList;

    /**
     * Character this skill applies to.
     * <p>
     * This reference is used to get handles on various character
     * properties that will affect a <code>CharSkill</code>'s
     * Total bonus such as Stat Bonus, Profession Bonus, etc.
     * </p>
     */
    protected MerpCharacter m_mChar;

    /**
     * Base constructor creates a new skill and takes in the
     * skill and the initial ranks a character has in it.
     *
     * @param skill The skill a character knows
     * @param ranks Ranks the character has in the skill
     */
    public CharSkill(Skill skill, int ranks) {
        this.setSkill(skill);
        this.setRanks(ranks);
        addPropertyChangeListener(this);

    }

    // javadoc inherits from interface
    public void setCharacter(MerpCharacter mChar) {
        this.m_mChar = mChar;
        ICharStat charStat = getStat();
        if(charStat != null) {
            CategoryCache.getInstance(this).debug("Adding (" + this + ") as property change listener to : " + charStat);
            charStat.addPropertyChangeListener(this);
        }
        else {
            CategoryCache.getInstance(this).debug("Not adding (" + this + ") as property change listener to : " + charStat);
        }
    }

    // javadoc inherits from interface
    public Skill getSkill() {
        return this.m_skill;
    }

    /**
     * Sets the skill.
     *
     * @param skill the skill to associate
     * @see #getSkill
     */
    public void setSkill(Skill skill) {
        this.m_skill = skill;
    }


    // javadoc inherits from interface
    public int getRanks() {
        return this.m_ranks;
    }

    /**
     * Setter for the ranks property.
     * This should probably only be used at creation.  Elsewhere
     * the addRanks method makes more sense
     *
     * @param ranks The new ranks
     * @see #getRanks
     */
    public void setRanks(int ranks) {
        int old_ranks = getRanks();
        CategoryCache.getInstance(this).debug("Setting ranks on " + this + ": " + ranks);
        this.m_ranks = ranks;
        changes.firePropertyChange(P_RANKS, old_ranks, getRanks());
    }

    // javadoc inherits from interface
    public void addRanks(int ranks) {
        this.setRanks(this.getRanks() + ranks);
    }

    // javadoc inherits from interface
    public void setCharSkillList(CharSkillList charSkillList) {
        this.m_charSkillList = charSkillList;
    }

    /**
     * Gets a reference to the CharSkillList containing this skill.
     *
     * @return The charskilllist containing this object
     * @see #setCharSkillList
     */
    public CharSkillList getCharSkillList() {
        return this.m_charSkillList;
    }

    /**
     * Gets a reference to the stat that modified this skill.
     *
     * @return The stat modifying this skill or null if there is not stat
     */
    public ICharStat getStat() {
        if(this.m_mChar == null) {
            return null;
        }
        else if (m_mChar.getCharStatList() == null) {
            return null;
        }
        else if(getSkill().getStat() == null) {
            return null;
        }
        else {
            try {
                ICharStat charStat =
                    CharStatFinder.findByStat(getSkill().getStat(),
                                              m_mChar.getCharStatList());

                return charStat;
            }
            catch (NotFoundX e) {
                System.out.println("CharSkill.getStatBonus - Stat not found: "
                    + getSkill().getStat().getName());
                return null;
            }
        }
    }

    /**
     * Calculates and returns the bonus based on ranks.
     * <p>
     * <ul>
     *   <li>The first ten ranks yield a bonus of 5 points each.</li>
     *   <li>The next five ranks yield a bonus of 2 points each </li>
     *   <li>Each additional rank yields a bonus of 1 point each</li>
     * </ul>
     * </p>
     *
     * @return Bonus based on ranks
     */
    public int getRankBonus() {
        int ranks = this.getRanks();
        int bonus = 0;

        if(ranks<=10) {
            bonus = ranks * 5;
        }
        else if(ranks<=15) {
            bonus = (10 * 5) + ( (ranks-10) * 2 );
        }
        else {
            bonus = (10 * 5) + (5 * 2) + ( (ranks-15) * 1 );
        }
        return bonus;
    }

    /**
     * Returns the stat bonus for the charstat for this skill.
     * <p>
     * <ul>
     *   <li>Gets the character's statlist</li>
     *   <li>Finds the stat for this skill</li>
     *   <li>Makes a call to <code>getTotalBonus</code> on the stat</li>
     * </ul>
     * </p>
     *
     * @return The stat bonus
     */
    public int getStatBonus() {
        ICharStat charStat = getStat();
        if(charStat != null) {
            return charStat.getTotalBonus();
        }
        else {
            return 0;
        }
    }

    /**
     * Returns the profession bonus for the profession for this level for
     * this skill.
     * <p>
     * This bonus is inclusive of skill level as well as skill
     * category level profession bonuses by calling the
     * <code>getProfessionBonus</code> on the skill list this skill
     * is included in.
     * </p>
     *
     * @return the profession bonus
     * @see CharSkillList#getProfessionBonus(ICharSkill)
     */
    public int getProfessionBonus() {
        int professionBonus = 0;
        if(getCharSkillList() != null) {
            professionBonus += getCharSkillList().getProfessionBonus(this);
        }

        return professionBonus;
    }

    // javadoc inherits from interface
    public int getItemBonus() {
        return 0;
    }

    // javadoc inherits from interface
    public int getSpecialBonus() {
        CategoryCache.getInstance(this).debug("Getting special bonus for: " + this);
        int specialBonus = 0;
        specialBonus += getSkill().getSpecialBonus();
        CategoryCache.getInstance(this).debug("After skill's special have: " + specialBonus);
        specialBonus += getCharSkillList().getBonus(this, ISkillModifier.SPECIAL_BONUS);
        CategoryCache.getInstance(this).debug("After skill list special have: " + specialBonus);

        // Add up all of the special modifers.
        return specialBonus;
    }

    // javadoc inherits from interface
    public int getTotalBonus() {
        return getRankBonus() + getStatBonus() + getProfessionBonus()
                + getItemBonus() + getSpecialBonus();
    }

    public String toString() {
        return getSkill() + " (" + getRanks() + ")";
    }

    /**
     * Add a PropertyChangeListener to the listener list. The listener is registered for all properties.
     *
     * @param l The PropertyChangeListener to be added
     * @see #removePropertyChangeListener
     */
    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    /**
     * Remove a PropertyChangeListener from the listener list. This removes a PropertyChangeListener
     * that was registered for all properties.
     *
     * @param l The PropertyChangeListener to be removed
     * @see #addPropertyChangeListener
     */
    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }

    // Inherits from javadocs
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        CategoryCache.getInstance(this).debug("Got property change: " + evt);

        if(P_RANKS.equals(propertyName)) {
            // When ranks change, the rank bonus and total bonus changes
            changes.firePropertyChange(P_RANK_BONUS, getRankBonus()-1, getRankBonus());
            changes.firePropertyChange(P_TOTAL_BONUS, getTotalBonus()-1, getTotalBonus());
        }

        else if(ICharStat.P_VALUE.equals(propertyName)) {
            // When a stat value changes, the stat bonus and total bonus changes
            changes.firePropertyChange(P_STAT_BONUS, getStatBonus()-1, getStatBonus());
            changes.firePropertyChange(P_TOTAL_BONUS, getTotalBonus()-1, getTotalBonus());
        }

        else if(P_SPECIAL_BONUS.equals(propertyName)) {
            // When a special bonus changes, the total changes
            changes.firePropertyChange(P_TOTAL_BONUS, getTotalBonus()-1, getTotalBonus());
        }
    }

    public void fireBonusChange(int bonusType) {
        switch(bonusType) {
            case ISkillModifier.PROFESSION_BONUS:
                changes.firePropertyChange(P_PROFESSION_BONUS, getProfessionBonus()-1, getProfessionBonus());
                break;

            case ISkillModifier.ITEM_BONUS:
                changes.firePropertyChange(P_ITEM_BONUS, getItemBonus()-1, getItemBonus());
                break;

            case ISkillModifier.SPECIAL_BONUS:
                changes.firePropertyChange(P_SPECIAL_BONUS, getSpecialBonus()-1, getSpecialBonus());
                break;

            default:
                Assertion.shouldNotReach("Got invalid bonus type of: " + bonusType);
        }
    }

    public int compareTo(Object o)  {
        Skill skill_1 = this.getSkill();
        Skill skill_2 = ((ICharSkill) o).getSkill();

        return skill_1.compareTo(skill_2);
    }
}
