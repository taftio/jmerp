package com.chaosserver.merp.rules.skill;

import java.util.Iterator;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.exception.NotFoundX;

/**
 * Find a skill category from a skill category list.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class SkillCategoryFinder {
    /** Singleton self reference. */
    private static SkillCategoryFinder s_self;

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static SkillCategoryFinder instance() {
        if(s_self == null) {
            s_self = new SkillCategoryFinder();
        }

        return s_self;
    }

    /**
     * Find a skill category by name from the system skill category list.
     *
     * @param name The name of the skill category to find
     * @return The SkillCategory with the given name
     * @throws NotFoundX If no skill category with the given name exists
     */
    public static SkillCategory findByName(String name) throws NotFoundX {
        return findByName(name, SkillCategoryList.instance());
    }

    /**
     * Find a skill category by name from the given skill category list.
     *
     * @param name The name of the skill category to find
     * @param skillCategoryList the list to search through for the name
     * @return The SkillCategory with the given name
     * @throws NotFoundX If no skill category with the given name exists
     */
    public static SkillCategory findByName(String name, SkillCategoryList skillCategoryList) throws NotFoundX {
        Assertion.isNotNull(name, "Must have a primary key to find on");
        // Iterate through the categories
        SkillCategoryIterator skillCategoryIter = skillCategoryList.iterator();
        while(skillCategoryIter.hasNext()) {
            SkillCategory skillCategory = (SkillCategory) skillCategoryIter.next();
            if(name.equals(skillCategory.getName())) {
                return skillCategory;
            }
        }
        throw new NotFoundX("Cound not file Skill named: " + name);
    }

    /**
     * Find the skill category that a certain skill belongs to from the
     * system skill category list.
     *
     * @param skill The skill to find the category that contains it
     * @return The SkillCategory the skill belongs to
     * @throws NotFoundX If no skill category with the given name exists
     */
    public static SkillCategory findBySkill(Skill skill) throws NotFoundX {
        return findBySkill(skill, SkillCategoryList.instance());
    }

    /**
     * Find the skill category that a certain skill belongs to.
     *
     * @param skill The skill to find the category that contains it
     * @param skillCategoryList The list to search through for the name
     * @return The SkillCategory the skill belongs to
     * @throws NotFoundX If no skill category with the given name exists
     */
    public static SkillCategory findBySkill(Skill skill, SkillCategoryList skillCategoryList) throws NotFoundX {
        Assertion.isNotNull(skill, "Must have a primary key to find on");
        SkillCategoryIterator skillCategoryIter = skillCategoryList.iterator();
        while(skillCategoryIter.hasNext()) {
            SkillCategory skillCategory = (SkillCategory) (skillCategoryIter.next());
            Iterator skillIter = skillCategory.iterator();
            while(skillIter.hasNext()) {
                Skill currSkill = (Skill) skillIter.next();
                if(skill.equals(currSkill)) {
                    return skillCategory;
                }
            }
        }
        throw new NotFoundX("Cound not file category containing: " + skill.getName());
    }
}