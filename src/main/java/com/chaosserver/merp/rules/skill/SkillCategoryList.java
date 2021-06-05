package com.chaosserver.merp.rules.skill;

import java.util.Collection;
import java.util.ArrayList;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.data.JavaBeanLoader;
import com.chaosserver.data.JavaBeanLoaderExceptionX;
import com.chaosserver.exception.LoadErrorRX;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.data.FileNameGetter;

/**
 * Holds references to all of the skill categories.
 *
 */

public class SkillCategoryList {
    /** Singleton self reference. */
    private static SkillCategoryList m_self;

    /** Holds a collection of skill categories. */
    private Collection m_skillCategoryList;

    /**
     * The private constructor.  This is a singleton, so one should use the
     * instance() method to get a reference to it.  This will cycle though
     * all the constants help in the <code>XML_SKILLLISTS</code> constant
     * and load them into this.
     */
    private SkillCategoryList() {
        m_skillCategoryList = new ArrayList(40);
        int i = 0;
        try {
            for(i=0; i < FileNameGetter.XML_SKILLISTS.length; i++) {
                // System.out.println("Loading: " + FileNameGetter.XML_SKILLISTS[i]);
                SkillCategory currSkillCategory =
                    (SkillCategory) JavaBeanLoader.getBean(FileNameGetter.XML_SKILLISTS[i]);

                m_skillCategoryList.add(currSkillCategory);
            }
        } catch (JavaBeanLoaderExceptionX e) {
            throw new LoadErrorRX("Problem loading: '" + FileNameGetter.XML_SKILLISTS[i] + "', " + e.toString());
        }
    }

    /**
     * Getter for the skill category list.
     *
     * @return the skill category list
     */
    Collection getSkillCategoryList() {
        return m_skillCategoryList;
    }

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static SkillCategoryList instance() {
        if(m_self == null) {
            m_self = new SkillCategoryList();
        }

        return m_self;
    }

    /**
     * Gets iterator over the skill category list.
     *
     * @return iterator over the skill category list
     */
    public SkillCategoryIterator iterator() {
        return new SkillCategoryIterator(this);
    }

    /**
     * Gets and iterator over the skills that a character does not
     * already have.
     *
     * @param character The character to generate an available iterator list over
     * @return An iterator over available skills
     */
    public AvailableSkillListIterator availableSkillListIterator(MerpCharacter character) {
        Assertion.isNotNull(character, "Cannot iterator against a null character.");
        return new AvailableSkillListIterator(this, character);
    }




}