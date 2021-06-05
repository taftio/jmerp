package com.chaosserver.merp.character.stat;

import java.util.ArrayList;

import com.chaosserver.merp.character.MerpCharacter;

/**
 * This is an interface for dealing with a basic
 * list of stats.
 *
 * @author Jordan Reed
 * @version 1.0
 */

public interface ICharStatList {
    /** The action event to fire when the stat is rolled. */
    public static String A_ROLL = "ICharStatList.ROLL";

    /**
     * This rerolls the values inside of the list.  It is expected
     * that the underlying object references remain the same, and
     * only property values change.
     */
    public void reroll();

    /**
     * This will attempt to swap the value of two stats.
     *
     * @param s1 The first stat to swap
     * @param s2 The second stat to swap
     * @throws NotSwappableX if an error occurs swapping the two stats
     */
    public void swap(ICharStat s1, ICharStat s2) throws NotSwappableX;

    /**
     * This will return the underlying collection of the character stats.
     *
     * @return The Collection
     */
    public ArrayList getCharStatList();


    /**
     * Return an iterator over all the ICharStat objects inside
     * the statlist.
     *
     * @return The iterator over the collection
     */
    public CharStatListIterator iterator();

    /**
     * Returns the stats rank.  You may also think of this as
     * returning the number of char stats greater than this is + 1.
     *
     * @param charStat The stat you are interested in
     * @return stat rank
     */
    public int getRank(ICharStat charStat);

    /**
     * Sets the character that this stat list will apply to.
     *
     * @param character The character this list will apply to
     */
    public void setCharacter(MerpCharacter character);
}
