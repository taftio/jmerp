package com.chaosserver.merp.rules.skill.modifier;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.character.skill.ICharSkill;

/**
 * This is an interface to objects that will modify the bonus of
 * a character skill.  Good uses for this would be profession based
 * bonuses or item based bonus, but it may be used for any
 * bonus.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public interface ISkillModifier {
    /** This bonus is a profession bonus. */
    public static String PROFESSION_BONUS_STR = "PROFESSION_BONUS";

    /** This bonus is an item bonus. */
    public static String ITEM_BONUS_STR = "ITEM_BONUS";

    /** This bonus is a special bonus. */
    public static String SPECIAL_BONUS_STR = "SPECIAL_BONUS";

    /** The enumeration for a profession bonus. */
    public static int PROFESSION_BONUS = 0;

    /** The enumeration for an item bonus. */
    public static int ITEM_BONUS = 1;

    /** The enumeration for a special bonus. */
    public static int SPECIAL_BONUS = 2;

    /** This bonus affects a single skill. */
    public static String SKILL_BONUS_STR = "SKILL_BONUS";

    /** The enumeration for a bonus affecting a single skill. */
    public static int SKILL_BONUS = 0;

    /** This bonus affects an entire skill category. */
    public static String CATEGORY_BONUS_STR = "CATEGORY_BONUS";

    /** The enumeration of the category level bonus. */
    public static int CATEGORY_BONUS = 1;


    /**
     * Sets the character on the skill modifier.
     *
     * @param character The character this modifies
     */
    public void setCharacter(MerpCharacter character);

    /**
     * Gets the value of the modification.
     *
     * @return The value of the modification
     */
    public int getValue();

    /**
     * Getter for the bonus type.
     *
     * @return the bonus type
     */
    public int getBonusType();

    /**
     * Getter for the affected type.
     *
     * @return the affected type
     */
    public int getAffectedType();

    /**
     * Checks if this modifier is relevant for the given character skill.
     * This is done by checking if the skill or category matches.
     *
     * @param charSkill The skill to check against
     * @return If the modifier affects the given character skill
     */
    public boolean isRelevant(ICharSkill charSkill);
}