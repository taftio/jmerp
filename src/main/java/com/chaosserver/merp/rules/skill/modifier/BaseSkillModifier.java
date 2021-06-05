package com.chaosserver.merp.rules.skill.modifier;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.character.skill.ICharSkill;
import com.chaosserver.assertion.Assertion;
import com.chaosserver.exception.NotFoundX;
import com.chaosserver.merp.rules.skill.Skill;
import com.chaosserver.merp.rules.skill.SkillCategory;
import com.chaosserver.merp.rules.skill.SkillCategoryFinder;

/**
 * This is an interface to objects that will modify the bonus of
 * a character skill.  Good uses for this would be profession based
 * bonuses or item based bonus, but it may be used for any
 * bonus.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public abstract class BaseSkillModifier implements ISkillModifier {
    /** Reference to the character this skill applies to. */
    protected MerpCharacter m_character;

    /** Reference the skill this modifier affects. */
    protected Skill m_skill = null;

    /** Reference to the skill category this modifier affects. */
    protected SkillCategory m_skillCategory = null;

    /** The type of bonus: Profession, Item, etc. */
    protected int m_bonusType = 0;

    /** The type of affect this bonus has: Category, Single Skill, etc. */
    protected int m_affectedType = 0;

    /**
     * Setter fo the affected skill.
     *
     * @param skill The skill to be bonused
     * @see #getSkill
     */
    public void setSkill(Skill skill) {
        m_skill = skill;
    }

    /**
     * Getter for the affected skill.  This will throw an
     * assertion exception on a category based bonus.
     *
     * @return The skill to be bonused
     * @see #setSkill
     */
    public Skill getSkill() {
        Assertion.isNotNull(m_skill, "The skill is null.  Is this a category based bonus?");
        return m_skill;
    }

    /**
     * Getter for the skill cateogry this bonus affects.
     *
     * @return The skill category being affected
     * @see #setSkillCategory
     */
    public SkillCategory getSkillCategory() {
        if(m_skillCategory == null && getSkill() != null) {
            try {
                return SkillCategoryFinder.instance().findBySkill(getSkill());
            }
            catch (NotFoundX e) {
                return m_skillCategory;
            }
        } else {
            return m_skillCategory;
        }
    }

    /**
     * Sets the skill category that this skill belongs to.
     * This should only be used for category modifiers.  A skill
     * modifier is smart enough to know which category it
     * belongs to.
     *
     * @param skillCategory The category this modifier bonuses
     * @see #getSkillCategory
     */
    public void setSkillCategory(SkillCategory skillCategory) {
        m_skillCategory = skillCategory;
    }

    /**
     * Setter for the bonus type by the enumeration.
     *
     * @param bonusType the bonus type
     * @see #getBonusType
     */
    public void setBonusType(int bonusType) {
        m_bonusType = bonusType;
    }

    /**
     * Setter for the bonus type by the string.
     * This method will match the string against
     * the enumeration.
     *
     * @param bonusType the bonus type
     * @see #getBonusType
     */
    public void setBonusType(String bonusType) {
        if(bonusType.equals(PROFESSION_BONUS_STR)) {
            setBonusType(PROFESSION_BONUS);
        }
        else if (bonusType.equals(ITEM_BONUS_STR)) {
            setBonusType(ITEM_BONUS);
        }
        else if (bonusType.equals(SPECIAL_BONUS_STR)) {
            setBonusType(SPECIAL_BONUS);
        }
    }

    // Inherits from interface
    public int getBonusType() {
        return m_bonusType;
    }

    /**
     * Setter for the affected type by the enumeration.
     *
     * @param affectedType the affected type
     * @see #getAffectedType
     */
    public void setAffectedType(int affectedType) {
        m_affectedType = affectedType;
    }

    /**
     * Setter for the affected type by the string.
     *
     * @param affectedType the affected type
     * @see #getAffectedType
     */
    public void setAffectedType(String affectedType) {
        if(CATEGORY_BONUS_STR.equals(affectedType)) {
            setAffectedType(CATEGORY_BONUS);
        }
        else if(SKILL_BONUS_STR.equals(affectedType)) {
            setAffectedType(SKILL_BONUS);
        }
    }

    /**
     * Gets the affected type (Category or Skill) as a string.
     *
     * @return Affected type as string
     */
    public String getAffectedAsString() {
        if(getAffectedType() == CATEGORY_BONUS) {
            return getSkillCategory().toString();
        }
        else if(getAffectedType() == SKILL_BONUS) {
            return getSkill().toString();
        }
        else {
            Assertion.shouldNotReach("This skill has an invalid affected type of: " + getAffectedType());
            return null;
        }
    }

    // Inherits from interface
    public int getAffectedType() {
        return m_affectedType;
    }

    // Inherites from interface
    public void setCharacter(MerpCharacter character) {
        m_character = character;
    }

    /**
     * Gets the character on the skill modifier.
     *
     * @return The character this modifies
     * @see #setCharacter
     */
    public MerpCharacter getCharacter() {
        return m_character;
    }

    // Inherits from interface
    public boolean isRelevant(ICharSkill charSkill) {
        if(charSkill == null) {
            return false;
        } else {
            Skill skill = charSkill.getSkill();
            return isRelevant(skill);
        }
    }

    /**
     * Checks if this modifier is relevant for the given skill.
     * THis is done by checking fi the skill or category matches.
     *
     * @param skill The skill to check
     * @return If the modifier affects the given skill
     */
     public boolean isRelevant(Skill skill) {
        if(skill == null) {
            return false;
        }
        else {
            if(getAffectedType() == CATEGORY_BONUS) {
                try {
                    SkillCategory skillCategory = SkillCategoryFinder.findBySkill(skill);
                    if(skillCategory.equals(getSkillCategory())) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                catch (NotFoundX e) {
                    return false;
                }
            }
            else if(getAffectedType() == SKILL_BONUS) {
                if(skill.equals(getSkill())) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                Assertion.shouldNotReach("This skill has an invalid affected type of: " + getAffectedType());
                return false;
            }
        }
     }
}