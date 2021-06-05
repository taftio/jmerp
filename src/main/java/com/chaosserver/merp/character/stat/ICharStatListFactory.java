package com.chaosserver.merp.character.stat;

/**
 * Creates a ICharStatList based on an algorithm.
 *
 * Creation of a <code>CharStatList</code> is tricky business.  It
 * should only be built by a factory which can implement specific
 * rules for the list, such as minumum stat values and other things.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public interface ICharStatListFactory {
    /**
     * Creates a brand new <code>CharStatList</code> object.
     *
     * @return The new CharStatList
     */
    public ICharStatList createCharStatList();

    /**
     * Creates a new roll for an existing <code>CharStatList</code> object.
     *
     * @param csl A reference to an existing list for which it will reroll the values
     */
    public void createCharStatList(ICharStatList csl);
}