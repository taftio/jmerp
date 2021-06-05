package com.chaosserver.merp.rules.skill;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Holds information about development progression in a single skill.
 * <p>
 * For example, a skill such as Soft Leather has an infinite progression
 * list of 1/1/1/1/... because you can place as many development points
 * as you want into it.  While a skill like Thrown would have a
 * progression list like 1/2 because the first DP would get you one rank,
 * and it takes 2 more DPs to get the second rank.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class ProgressionList {
    /** Holds the list of progression costs. */
    List m_progressionList;

    /**
     * Empty Constructor.
     */
    public ProgressionList() {
        m_progressionList = new ArrayList(2);
    }

    /**
     * Setter for the progression list.
     *
     * @param progressionList the list
     * @see #getProgressionList
     */
    public void setProgressionList(ArrayList progressionList) {
        m_progressionList = progressionList;
    }

    /**
     * Getter for the progression list.
     *
     * @return the progression list
     * @see #setProgressionList
     */
    protected List getProgressionList() {
        return m_progressionList;
    }

    /**
     * Adds another progression cost
     *
     * To create the progression list of 1/2 this method should be
     * called twice.  The first with 1, and the second with 2
     *
     * @param progressionCost The cost of the next progression
     */
    public void addProgressionCost(int progressionCost) {
        getProgressionList().add(new Integer(progressionCost));
    }

    /**
     * Converts the object to string representation.
     *
     * @return a string representation of the object
     */
     public String toString() {
        StringBuffer sb = new StringBuffer(super.toString()+"\n");

        int i = 0;
        Iterator iter = getProgressionList().iterator();
        while(iter.hasNext()) {
            i++;
            sb.append(i + ": <" + iter.next() + ">\n");
        }

        return sb.toString();


    }
}