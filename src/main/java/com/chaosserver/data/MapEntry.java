package com.chaosserver.data;

import java.io.Serializable;

/**
 * Holds the data that will be put into a map
 * <p>
 * To simplify the interface of Maps in the JavaBeanLoader, Maps
 * are really just collections of MapEntry objects.  Therefore
 * to load a Map, you just list out a bunch of MapEntry objects
 * and the loader will take care of putting them into your
 * map correctly.
 * </p>
 * <p>
 * A map entry is just a name/value pair.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning 4/20/2001
 */
public class MapEntry implements Serializable {
    /** The key of the entry. */
    protected Object key;

    /** The value mapped to the key. */
    protected Object value;

    /** Default constructor. */
    public MapEntry() {
    }

    /**
     * Faster constructor.
     *
     * @param key The key for a map entry
     * @param value The value of the map entry
     */
    public MapEntry(Object key, Object value) {
        setKey(key);
        setValue(value);
    }

    /**
     * Setter for the key.
     *
     * @param key The new key
     * @see #getKey
     */
    public void setKey(Object key) {
        this.key = key;
    }

    /**
     * Setter for the value.
     *
     * @param value The new value
     * @see #getValue
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Getter for the key.
     *
     * @return the key
     * @see #setKey
     */
    public Object getKey() {
        return this.key;
    }

    /**
     * Getter for the value.
     *
     * @return The value
     * @see #setValue
     */
    public Object getValue() {
        return this.value;
    }
}