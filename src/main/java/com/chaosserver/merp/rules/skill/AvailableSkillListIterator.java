package com.chaosserver.merp.rules.skill;

import java.util.Iterator;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.character.skill.CharSkillList;

/**
 * Iterates over the skills that character does not already have.
 *
 * @author Jordan Reed
 */
public class AvailableSkillListIterator extends SkillListIterator {
    /** Character being validated against. */
    protected MerpCharacter character;

    /** Next valid race. */
    protected Skill nextSkill;

    /** The character's skill list. */
    protected CharSkillList charSkillList;

    /**
     * Constructor.
     *
     * @param skillCategoryList The skill category list to iterator over for
     *        available skills
     * @param character The merp character to check for available skills against
     */
    public AvailableSkillListIterator(SkillCategoryList skillCategoryList, MerpCharacter character) {
        super();
        Assertion.isNotNull(skillCategoryList, "Need a list to iterate over.");
        Assertion.isNotNull(character, "Need a character to validate against.");
        setCharacter(character);
        setCharSkillList(character.getCharSkillList());
    }

    /**
     * Setter for the character skill list to iterate against.
     *
     * @param charSkillList the character skill list to iterate against
     * @see #getCharSkillList
     */
    public void setCharSkillList(CharSkillList charSkillList) {
        this.charSkillList = charSkillList;
    }

    /**
     * Getter for the character skill list to iterate against.
     *
     * @return the character skill list to iterate against
     * @see #setCharSkillList
     */
    public CharSkillList getCharSkillList() {
        return this.charSkillList;
    }

    /**
     * Setter for the character to iterate against.
     *
     * @param character the character to iterate against
     * @see #getCharacter
     */
    public void setCharacter(MerpCharacter character) {
        this.character = character;
    }

    /**
     * Getter for the character to iterate against.
     *
     * @return the character to iterate against
     * @see #setCharacter
     */
    public MerpCharacter getCharacter() {
        return this.character;
    }

    /**
     * Setter for the next valid skill.
     *
     * @param nextSkill the next valid skill
     * @see #getNextSkill
     */
    public void setNextSkill(Skill nextSkill) {
        this.nextSkill = nextSkill;
    }

    /**
     * Getter for the next valid skill.
     *
     * @return the next valid skill
     * @see #setNextSkill
     */
    public Skill getNextSkill() {
        return this.nextSkill;
    }

    /**
     * Gets the next element from the iterator.
     *
     * @return the next element in the iterator
     */
    public Skill next() {
        return getNextSkill();
    }

    /**
     * Checks if the iterator has another element.
     *
     * @return if the iterator has another element
     */
    public boolean hasNext() {
        skills: while(super.hasNext()) {
            // Just because super has another one doesn't mean that
            // it's valid.
            setNextSkill(super.next());
            if(getCharSkillList().contains(getNextSkill())) {
                continue skills;
            }
            else {
                return true;
            }
        }
        return false;
    }



}