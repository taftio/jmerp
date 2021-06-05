package com.chaosserver.merp.rules.restriction;

/**
 * Describes and object with an associated RestrictionList.
 * <p>
 * Assigning the interface declares that you may have a list
 * of restrictions.  Before applying you to a character property
 * you are requesting the character make sure all of you restrictions
 * pass and that it then binds all of the restrictions to itself.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 */

public interface IRestricted {
    /**
     * Returns the restriction list associated with this object.
     *
     * @return The list
     */
    public RestrictionList getRestrictionList();
}