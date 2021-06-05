package com.chaosserver.data.find;

/**
 * This interface is meant to be applied to objects that
 * have an associated finder.  There are meant to allow
 * another class to easily determine the appropriate finder
 * for reading/writing.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public interface Findable {
    /**
     * This method will return the name of the property holding the
     * primary key.
     * <p>
     * The name returned simply means that the finder should have
     * a findByXXX method for this name and that it will return a
     * unique object in the system
     * </p>
     *
     * @return Property name holding the primary key
     */
    public String getPkName();

    /**
     * Gets and instance of the finder for this object.
     *
     * @return Instance of finder
     */
    public Object getFinder();
}