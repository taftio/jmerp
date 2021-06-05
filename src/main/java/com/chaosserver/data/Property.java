package com.chaosserver.data;

/**
 * This represents a property of a JavaBean
 * <p>
 * A property is nothing more than a name value pair.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class Property {
    /** Property name. */
    private String m_name;

    /** Property value. */
    private Object m_object;

    /**
     * Default constructor.
     */
    public Property() {
    }

    /**
     * Constructor to build complete object.
     *
     * @param name The name of the property
     * @param obj The property's value
     */
    public Property(String name, Object obj) {
        setName(name);
        setObject(obj);
    }

    /**
     * Setter for the name.
     *
     * @param name The value of the name
     * @see #getName
     */
    public void setName(String name) {
        m_name = name;
    }

    /**
     * Getter for the name.
     *
     * @return The value of the name
     * @see #setName
     */
    public String getName() {
        return m_name;
    }

    /**
     * Setter for the object.
     *
     * @param obj The value of the object
     * @see #getObject
     */
    public void setObject(Object obj) {
        m_object = obj;
    }

    /**
     * Getter for the object.
     *
     * @return The value of the object
     * @see #setObject
     */
    public Object getObject() {
        return m_object;
    }
}