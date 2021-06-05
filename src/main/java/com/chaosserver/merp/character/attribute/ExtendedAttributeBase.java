package com.chaosserver.merp.character.attribute;

/**
 * Extended Attributes are added to characters to store either temporary
 * data or data that doesn't fit into the normal flow.
 * <p>
 * This base class provides some functiolity useful for all other
 * attributes.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public abstract class ExtendedAttributeBase implements ExtendedAttribute {
    /** The name of the extended attribute. */
    protected String name;

    /**
     * This describes the level at which this attribute expires.
     * <p>
     * By default this is set to zero, and that means the next time
     * a character gains a level, this will expire.  When a level
     * is gained, if the new level is greater than this property,
     * the EA will be removed from the character.
     * </p>
     */
    protected int expiration = 0;

    /**
     * Default constructor for bean loading.
     */
    public ExtendedAttributeBase() {
    }

    /**
     * Constructor to build a full EA.
     *
     * @param name The name of the attribute
     * @param expiration The level that this attribute expires at
     */
    public ExtendedAttributeBase(String name, int expiration) {
        setName(name);
        setExpiration(expiration);
    }

    // Inherits from interface
    public void setName(String name) {
        this.name = name;
    }

    // Inherits from interface
    public String getName() {
        return this.name;
    }

    // Inherits from interface
    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    // Inherits from interface
    public int getExpiration() {
        return this.expiration;
    }

    /**
     * Returns a string representation of the attribute.
     *
     * @return String representation of attribute
     */
    public String toString() {
        return getName() + " (expires: " + getExpiration() + ")";
    }
}