package com.chaosserver.merp.rules.specialabilities;

import com.chaosserver.assertion.AssertionFailedRX;
import com.chaosserver.merp.rules.skill.Skill;
import com.chaosserver.merp.rules.skill.modifier.ISkillModifiable;
import com.chaosserver.merp.rules.skill.modifier.ISkillModifier;
import com.chaosserver.merp.rules.skill.modifier.SkillModifierList;
import com.chaosserver.merp.rules.skill.modifier.SpecialBonus;
import com.chaosserver.merp.character.MerpCharacter;

/**
 * Applies a special bonus to a skill.
 *
 * @author Jordan Reed
 */
public class SkillBonusAbility extends SpecialAbilityBase implements ISkillModifiable {
    /** The amount this modifies the skill. */
    protected int amount = 0;

    /** Special bonus that will be applied to the character. */
    protected SpecialBonus specialBonus;

    /**
     * Constructs a default object that is not valid.
     */
    public SkillBonusAbility() {
        specialBonus = new SpecialBonus();
    }

    /**
     * Constructs a fully valid special ability skill bonus.
     *
     * @param amount The amount to adjust the skill by
     * @param specialBonus The special bonus object.
     */
    public SkillBonusAbility(int amount, SpecialBonus specialBonus) {
        setSpecialBonus(specialBonus);
        setAmount(amount);
    }

    // Inherits doc from base class
    public String getName() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Skill Bonus: +");
        stringBuffer.append(amount);
        try {
            if(specialBonus != null && specialBonus.getAffectedAsString() != null) {
                stringBuffer.append(" (");
                stringBuffer.append(specialBonus.getAffectedAsString());
                stringBuffer.append(")");
            }
        }
        catch (AssertionFailedRX e) {
            // Eat it.  Yum.
        }

        return stringBuffer.toString();
    }

    /**
     * Returns the amount of the special bonus.
     *
     * @return Amount of the special bonus
     * @see #setAmount
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * Sets the amount of the special bonus.
     *
     * @param amount The amount of the special bonus
     * @see #getAmount
     */
    public void setAmount(int amount) {
        this.amount = amount;
        SpecialBonus specialBonus = getSpecialBonus();
        if(specialBonus != null) {
            specialBonus.setValue(amount);
        }
    }

    /**
     * Returns the special bonus.
     *
     * @return the special bonus
     * @see #setSpecialBonus
     */
    public SpecialBonus getSpecialBonus() {
        return this.specialBonus;
    }

    /**
     * Sets the special bonus.
     *
     * @param specialBonus sets the special bonus
     * @see #getSpecialBonus
     */
    public void setSpecialBonus(SpecialBonus specialBonus) {
        this.specialBonus = specialBonus;
        this.specialBonus.setValue(getAmount());
    }

    public String getDescription() {
        return "A special +" + getAmount() + " bonus to any one primary skill.";
    }

    public SkillModifierList getSkillModifierList() {
        SkillModifierList skillModifierList = new SkillModifierList();
        skillModifierList.add(specialBonus);

        return skillModifierList;
    }

    /**
     * Sets the skill that the special bonus applies to.
     *
     * @param skill The skill to apply to.
     */
    public void setSkill(Skill skill) {
        specialBonus.setSkill(skill);
        specialBonus.setAffectedType(ISkillModifier.SKILL_BONUS);
        setValid(true);
    }

    public Object clone() {
        SkillBonusAbility skillBonusAbility = new SkillBonusAbility(getAmount(), (SpecialBonus) getSpecialBonus().clone());

        return skillBonusAbility;
    }
}