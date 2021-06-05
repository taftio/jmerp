package com.chaosserver.merp.rules.skill;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Category that a skill falls into.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class SkillCategory {
    /** The name of the skill category. */
    private String m_name;

    /** The list of the skills in this category. */
    private ArrayList m_skillList;

    /**
     * Empty Constructor.
     */
    public SkillCategory() {
    }

    /**
     * Gets the name property.
     *
     * @return the name
     * @see #setName
     */
    public String getName() {
        return m_name;
    }

    /**
     * Sets the name property.
     *
     * @param name the name
     * @see #getName
     */
    public void setName(String name) {
        m_name = name;
    }

    /**
     * Setter for the skill list.
     *
     * @return the skill list
     * @see #setSkillList
     */
    public List getSkillList() {
        return m_skillList;
    }

    /**
     * Getter for the skill list.
     *
     * @param skillList the skill list
     * @see #getSkillList
     */
    public void setSkillList(ArrayList skillList) {
        m_skillList = skillList;
    }

    /**
     * Converts the object to string representation.
     *
     * @return a string representation of the object
     */
     public String toString() {
        StringBuffer sb = new StringBuffer(128);
        sb.append(getName());
        sb.append("[");
        Iterator iter = m_skillList.iterator();
        while(iter.hasNext()) {
            sb.append( iter.next() );
            if(iter.hasNext()) {
                sb.append(",");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    /**
     * Returns an iterator over the skill categories.
     *
     * @return an iterator over the skill categories
     */
    public Iterator iterator() {
        return m_skillList.iterator();
    }


}