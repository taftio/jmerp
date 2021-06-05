package com.chaosserver.merp.character.attribute;

/**
 * Extended Attributes are added to characters to store either temporary
 * data or data that doesn't fit into the normal flow.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public interface ExtendedAttribute {
    /**
     * Setter for the name.
     *
     * @param name The name of the attribute
     * @see #getName
     */
    public void setName(String name);

    /**
     * Getter for the name.
     *
     * @return The name of the attribute
     * @see #setName
     */
    public String getName();

    /**
     * Setter for expiration.
     *
     * @param expiration the expiration level
     * @see #getExpiration
     */
    public void setExpiration(int expiration);


    /**
     * Getter for the expiration.
     *
     * @return the expiration level
     * @see #setExpiration
     */
    public int getExpiration();
}