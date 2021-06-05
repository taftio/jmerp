package com.chaosserver.merp.rules.skill;

import java.util.Iterator;

/**
 * Iterates over the complete set of skills.
 *
 * @author Jordan Reed
 */
public class SkillListIterator {
    /** Iterator over the skill categories. */
    SkillCategoryIterator skillCategoryIterator = null;

    /** Iterator over skills in a category. */
    Iterator skillIterator = null;

    /**
     * Emptry Constructor.
     * <p>
     * Runs against system skill list.
     * </p>
     */
    public SkillListIterator() {
        skillCategoryIterator = SkillCategoryList.instance().iterator();
    }

    /**
     * Gets the next element from the iterator.
     *
     * @return the next element in the iterator
     */
    public Skill next() {
        if(skillIterator == null || !skillIterator.hasNext()) {
            SkillCategory skillCategory = (SkillCategory) (skillCategoryIterator.next());
            skillIterator = skillCategory.iterator();
        }
        return (Skill) skillIterator.next();
    }

    /**
     * Checks if the iterator has another element.
     *
     * @return if the iterator has another element
     */
    public boolean hasNext() {
        boolean result = false;
        if(skillIterator != null && skillIterator.hasNext()) {
            result = true;
        }
        else {
            if(skillCategoryIterator.hasNext()) {
                result = true;
            }
        }

        return result;
    }



}