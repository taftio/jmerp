package com.chaosserver.merp.rules.skill;

import java.util.Iterator;

import com.chaosserver.exception.MethodNotImplementedRX;

/**
 * Iterates over the SkillCategories in the system.
 */

public class SkillCategoryIterator {
    Iterator m_categoryIter;

    SkillCategoryIterator(SkillCategoryList skillCategoryList) {
        m_categoryIter = skillCategoryList.getSkillCategoryList().iterator();

    }

    /**
     * Checks if the iterator has another element.
     *
     * @return if the iterator has another element
     */
    public boolean hasNext() {
        return m_categoryIter.hasNext();
    }

    /**
     * Gets the next element from the iterator.
     *
     * @return the next element in the iterator
     */
    public SkillCategory next() {
        return (SkillCategory) m_categoryIter.next();
    }

}