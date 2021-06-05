package com.chaosserver.merp.character.attribute;

/**
 * Extended Attributes are added to characters to store either temporary
 * data or data that doesn't fit into the normal flow.
 * <p>
 * This generic attribute holds only a name/value pair
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class ExtendedAttributeGeneric extends ExtendedAttributeBase {
    /** The name of the extended attribute. */
    protected String name;

    /** The attribute object. */
    protected Object attribute;

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
    public ExtendedAttributeGeneric() {
    }

    /**
     * Constructor to build a full EA.
     *
     * @param name The name of the attribute
     * @param attribute The object of the attribute
     */
    public ExtendedAttributeGeneric(String name, Object attribute) {
        setName(name);
        setAttribute(attribute);
    }

    /**
     * Constructor to build a full EA.
     *
     * @param name The name of the attribute
     * @param attribute The object of the attribute
     * @param expiration The expiration level
     */
    public ExtendedAttributeGeneric(String name, Object attribute, int expiration) {
        setName(name);
        setAttribute(attribute);
        setExpiration(expiration);
    }

    /**
     * Setter for the attribute.
     *
     * @param attribute The attribute
     * @see #getAttribute
     */
    public void setAttribute(Object attribute) {
        this.attribute = attribute;
    }

    /**
     * Getter for the attribute.
     *
     * @return the attribute
     * @see #setAttribute
     */
    public Object getAttribute() {
        return this.attribute;
    }

}