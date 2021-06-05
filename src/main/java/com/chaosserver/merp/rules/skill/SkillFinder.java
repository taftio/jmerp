package com.chaosserver.merp.rules.skill;

import java.util.Iterator;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.exception.NotFoundX;

/**
 * This object is used to find one of the skills held in the system.
 *
 * @author Jordan Reed
 * @version 1.1
 * @since The Beginning
 */
public class SkillFinder {
    /** Singleton self reference */
    private static SkillFinder s_self;

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static SkillFinder instance() {
        if(s_self == null) {
            s_self = new SkillFinder();
        }

        return s_self;
    }

    /**
     * Finds a skill by name from the system skill list.
     *
     * @param name Name of the skill to find
     * @return The skill with the given name
     * @throws NotFoundX If the skill is not found
     */
    public Skill findByName(String name) throws NotFoundX {
        return findByName(name, SkillCategoryList.instance());
    }

    /**
     * Finds a skill by name from the given skill list.
     *
     * @param name Name of the skill to find
     * @param skillCategoryList The list to search for the skill
     * @return The skill with the given name
     * @throws NotFoundX If the skill is not found
     */
    public Skill findByName(String name, SkillCategoryList skillCategoryList) throws NotFoundX {
        Assertion.isNotNull(name, "Must have a primary key to find on");
        // Iterate through the categories
        SkillCategoryIterator skillCategoryIter = skillCategoryList.iterator();
        while(skillCategoryIter.hasNext()) {
            Iterator skillIter = ((SkillCategory)(skillCategoryIter.next())).iterator();
            while(skillIter.hasNext()) {
                Skill currSkill = (Skill) skillIter.next();
                if(name.equals(currSkill.getName())) {
                    return currSkill;
                }
            }
        }
        throw new NotFoundX("Cound not file Skill named: " + name);
    }
}