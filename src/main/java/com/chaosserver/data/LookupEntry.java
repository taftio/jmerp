package com.chaosserver.data;

/**
 * A lookup entry inside of a lookup table.  A lookup table
 * is nothing more than a map that links ranges of integers
 * to values.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class LookupEntry {
    private Object m_key;
    private Object m_value;

    public LookupEntry() {
    }

    /**
     * Setter method for the key.
     *
     * @param key The value of the key
     * @see #getKey
     */
    public void setKey(Object key) {
        m_key = key;
    }

    /**
     * Getter method for the key.
     *
     * @return The value of the key
     * @see #setKey
     */
    public Object getKey() {
        return m_key;
    }

    /**
     * Setter method for the value.
     *
     * @param value The value of the value
     * @see #getValue
     */
    public void setValue(Object value) {
        m_value = value;
    }

    /**
     * Getter method for the value.
     *
     * @return The value of the value
     * @see #setValue
     */
    public Object getValue() {
        return m_value;
    }
}