package com.chaosserver.merp.character.attribute;

import java.util.HashMap;
import java.util.Map;

/**
 * An attribute map is a map of extended attributes.
 * <p>
 * Each attribute is keyed off its name property.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning 6/17/2001
 */
public class AttributeMap {
    /** Holds the attributes internally. */
    protected Map attributes;

    /**
     * Default constructor.
     */
    public AttributeMap() {
        attributes = new HashMap();
    }

    /**
     * Getter on attributes for bean writer.
     *
     * @return The attribute map
     * @see #setAttributes
     */
    public Map getAttributes() {
        return this.attributes;
    }

    /**
     * Setter on attributes for bean loader.
     *
     * @param attributes The attribute map
     * @see #getAttributes
     */
    public void setAttributes(Map attributes) {
        this.attributes = attributes;
    }

    /**
     * Adds a value to the map.
     *
     * @param attribute The attribute to add
     */
    public void put(ExtendedAttribute attribute) {
        attributes.put(attribute.getName(), attribute);
    }

    /**
     * Gets a value from the map.
     *
     * @param name The name of the attribute to get from the map
     * @return the attribute
     */
    public ExtendedAttribute get(String name) {
        return (ExtendedAttribute) attributes.get(name);
    }
}