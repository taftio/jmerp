package com.chaosserver.merp.character.skill;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.rules.skill.Skill;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Interfaces to an character skill.
 *
 * Skills should be referenced through the accessing interface.  Because
 * the inner workings of the character skill can change greatly from
 * a standard skill like <em>Missile</em> to a trickier one such as
 * <em>Body Development</em> or <em>Ambush</em>.  Therefore, while most
 * ICharSkills will simply be the CharSkill underneath, some will be
 * the other more specific types.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public interface ICharSkill {
    /** Property change for ranks. */
    public static String P_RANKS = "CharSkill.ranks";

    /** Fired when the stat bonus changes. */
    public static String P_STAT_BONUS = "CharSkill.statBonus";

    /** Fired when the rank bonus changes. */
    public static String P_RANK_BONUS = "CharSkill.rankBonus";

    /** Fired when the special bonus changes. */
    public static String P_SPECIAL_BONUS = "CharSkill.specialBonus";

    /** Fired when the profession bonus changes. */
    public static String P_PROFESSION_BONUS = "CharSkill.professionBonus";

    /** Fired when the item bonus changes. */
    public static String P_ITEM_BONUS = "CharSkill.itemBonus";

    /** Fired when the total bonus changes. */
    public static String P_TOTAL_BONUS = "CharSkill.totalBonus";

    /**
     * Sets the character this skill applies to.
     *
     * @param mChar The character
     */
    public void setCharacter(MerpCharacter mChar);

    /**
     * Sets a reference to the CharSkillList containing this skill.
     * The skill will use this list to add in Skill Bonuses that apply.
     *
     * @param charSkillList the skill list this is a part of
     * @see #getCharSkillList
     */
    public void setCharSkillList(CharSkillList charSkillList);


    /**
     * Getter for the ranks property.
     *
     * @return the ranks
     */
    public int getRanks();

    /**
     * Calculates and returns the bonus based on ranks.
     *
     * @return Bonus based on ranks
     */
    public int getRankBonus();

    /**
     * Returns the stat bonus for the charstat for this skill.
     *
     * @return The stat bonus
     */
    public int getStatBonus();

    /**
     * Returns the profession bonus for the profession for this level for
     * this skill.
     * <p>
     *   This bonus must be inclusive of skill level as well as skill
     *   category level profession bonuses
     * </p>
     *
     * @return the profession bonus
     */
    public int getProfessionBonus();

    /**
     * Returns the total item bonus' for this skill.
     *
     * @return The item bonuses
     */
    public int getItemBonus();

    /**
     * Returns the total special bonus for this skill.
     *
     * @return Special bonuses
     */
    public int getSpecialBonus();

    /**
     * Calculates and returns the total bonus.
     *
     * @return Total bonus for skill
     */
    public int getTotalBonus();

    /**
     * Adds to the number of ranks currently in the stat.
     *
     * @param ranks The ranks to add
     */
    public void addRanks(int ranks);

    /**
     * Returns the skill.
     *
     * @return the skill associated with this charskill.
     */
    public Skill getSkill();

    /**
     * Returns the CharSkillList this belongs to.
     *
     * @return List it belongs to
     * @see #setCharSkillList
     */
    public CharSkillList getCharSkillList();

    /**
     * Informs that skill that something has made changes to the bonus applied to it.
     *
     * @param bonusType indicates the bonux type to fire the change for
     */
    public void fireBonusChange(int bonusType);

    /**
     * Add a PropertyChangeListener to the listener list. The listener is registered for all properties.
     *
     * @param l The PropertyChangeListener to be added
     * @see #removePropertyChangeListener
     */
    public void addPropertyChangeListener(PropertyChangeListener l);

    /**
     * Remove a PropertyChangeListener from the listener list. This removes a PropertyChangeListener
     * that was registered for all properties.
     *
     * @param l The PropertyChangeListener to be removed
     * @see #addPropertyChangeListener
     */
    public void removePropertyChangeListener(PropertyChangeListener l);
}