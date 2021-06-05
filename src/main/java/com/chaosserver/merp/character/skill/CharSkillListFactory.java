package com.chaosserver.merp.character.skill;

import java.util.Iterator;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.rules.race.Race;
import com.chaosserver.merp.rules.skill.BaseSkillValue;
import com.chaosserver.merp.rules.skill.Skill;

/**
 * Manages creation of a CharSkillList for a character.
 * <p>
 * When a character is first created the <code>createSkillList</code>
 * method can be invoked on it to create an empty SkillList that has
 * no skills within it.
 * </p>
 * <p>
 * Once a character has a race solidified, the addBaseRaceSkills
 * can be used to add in all of the skills that a particular race
 * gets automatically.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class CharSkillListFactory {
    /**
     * Add a race's base skills into a character.
     * <p>
     * Inserts all of the skills of a given race into the character.
     * </p>
     * <p>
     * <em>Warning:</em>  This does not check for duplicates.  Using this
     * method on a character that already has skills may cause strange
     * behavior.
     * </p>
     *
     * @param race The race to build from
     * @param charSkillList The skill list to add all of the race's skills to
     */
    public static void addBaseRaceSkills(CharSkillList charSkillList,
                                         Race race) {

        Assertion.isNotNull(race,
                            "Cannot create a skill list based off a null race");
        Assertion.isNotNull(charSkillList,
                            "Cannot add skills to a null skill list");
        // CharSkillList charSkillList = new CharSkillList();


        Iterator skillIter = race.getBaseSkillList().iterator();
        while(skillIter.hasNext()) {
            // TODO: Before adding a skill, assert that it doesn't already exist
            BaseSkillValue baseSkillValue = (BaseSkillValue) skillIter.next();
            charSkillList.add(baseSkillValue.getCharSkill());
        }

        // Add in Base Spell OB


    }

    /**
     * Adds all of the base racial skills into a character for the character's race.
     *
     * @param mChar The character to add all the races skills to
     * @see CharSkillListFactory#addBaseRaceSkills(CharSkillList, Race)
     */
    public static void addBaseRaceSkills(MerpCharacter mChar) {
        addBaseRaceSkills(mChar.getCharSkillList(), mChar.getRace());
    }

    /**
     * Creates an empty skill list for a character.
     *
     * @param character The character to set an empty skill list on
     */
    public static void createSkillList(MerpCharacter character) {
        CharSkillList charSkillList = createSkillList();
        character.setCharSkillList(charSkillList);
    }

    /**
     * Creates an emptry skill list.
     *
     * @return An empty skill list
     */
    public static CharSkillList createSkillList() {
        return new CharSkillList();
    }

    /**
     * Creates the appropriate charskill for a given skill.
     *
     * @param skill The skill to create the charskill for
     * @return the char skill
     */
    public static ICharSkill createCharSkill(Skill skill) {
        return new CharSkill(skill, 0);
    }
}