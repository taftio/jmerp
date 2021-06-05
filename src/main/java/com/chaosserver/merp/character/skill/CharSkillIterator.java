package com.chaosserver.merp.character.skill;

import java.util.Iterator;

/**
 * Iterates over a character skill list.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class CharSkillIterator {
    /** Reference to the iterator. */
    private Iterator m_iterator;

    /**
     * Constructor takes in the CharSkillList to iterator over.
     *
     * @param charSkillList The list to iterate over
     */
    public CharSkillIterator(CharSkillList charSkillList) {
        m_iterator = charSkillList.getCharSkillList().iterator();
    }

    /**
     * Return the next character skill in the iterator.
     *
     * @return The next character skill in the iterator
     */
    public ICharSkill next() {
        return (ICharSkill) m_iterator.next();
    }

    /**
     * Tests if there is another character skill in the iterator.
     *
     * @return If there is another character skill in the iterator
     */
    public boolean hasNext() {
        return m_iterator.hasNext();
    }

}