package com.chaosserver.merp.character.skill;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.exception.NotFoundX;
import com.chaosserver.merp.rules.skill.Skill;
import com.chaosserver.logging.CategoryCache;

/**
 * Finder to get back a refernce to a character skill from a character's
 * skill list.
 * <p>
 * This object is a static singleton.  All methods on this object are static and
 * should be called directory, but it is possible to make a single instance
 * if desired.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class CharSkillFinder {
    /** Singleton self reference. */
    protected static CharSkillFinder self;

    /** Protected constructor to force all access through the instance method. */
    protected CharSkillFinder() {
        Assertion.isTrue(self == null, "Should not call constructor of "
            + "a singleton after it has been constructed");
    }

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static CharSkillFinder instance() {
        if(self == null) {
            self = new CharSkillFinder();
            CategoryCache.getInstance(CharSkillFinder.class).info("Created new CharSkillFinder");
        }

        return self;
    }

    /**
     * Retreives a reference to a character skill based on the name of the skill.
     *
     * @param name The name of the skill to search for
     * @param charSkillList The skill list to search in
     * @return The character skill object for the skill with the given name
     * @throws NotFoundX If the skill is not found in the list
     */
    public static ICharSkill findByName(String name, CharSkillList charSkillList) throws NotFoundX {
        Assertion.isNotNull(charSkillList, "Cannot search through a null list");

        CharSkillIterator charSkillIterator = charSkillList.iterator();

        while(charSkillIterator.hasNext()) {
            ICharSkill charSkill = charSkillIterator.next();
            if(charSkill.getSkill().getName().equals(name)) {
                return charSkill;
            }
        }

        throw new NotFoundX("Failed to find CharSkill with name: " + name);
    }

    /**
     * Retreives a reference to a character skill based on the skill.
     *
     * @param skill The skill to search for
     * @param charSkillList The skill list to search in
     * @return The character skill object for the skill with the given name
     * @throws NotFoundX If the skill is not found in the list
     */
    public static ICharSkill findBySkill(Skill skill, CharSkillList charSkillList) throws NotFoundX {
        Assertion.isNotNull(charSkillList, "Cannot search through a null list");

        CharSkillIterator charSkillIterator = charSkillList.iterator();

        while(charSkillIterator.hasNext()) {
            ICharSkill charSkill = charSkillIterator.next();
            if(charSkill.getSkill().equals(skill)) {
                return charSkill;
            }
        }

        throw new NotFoundX("Failed to find CharSkill with skill: " + skill);
    }
}