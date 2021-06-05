package com.chaosserver.merp.rules.language;

import com.chaosserver.data.find.Findable;

/**
 * Holds information about a single language.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class Language implements Findable {
    /** Holds the name of this language. */
    protected String name;

    /**
     * Default constructor.
     */
    public Language() {
    }

    /**
     * Setter for the name.
     *
     * @param name The name of the language
     * @see #getName
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the name.
     *
     * @return The language's name
     * @see #setName
     */
    public String getName() {
        return this.name;
    }

    // Inherits javadoc from Interface
    public String getPkName() {
        return "Name";
    }

    // Inherits javadoc from Interface
    public Object getFinder() {
        return LanguageFinder.instance();
    }

    /**
     * Returns the name of the language.
     *
     * @return the name of the language
     */
    public String toString() {
        return getName();
    }

    /**
     * Checks equality based on name.
     *
     * @param language The language to test against
     * @return If the languages are equal
     */
    public boolean equals(Language language) {
        boolean result = false;
        if( language != null
            && language.getName() != null
            && language.getName().equals(this.getName()) ) {

            result = true;
        }

        return result;
    }
}