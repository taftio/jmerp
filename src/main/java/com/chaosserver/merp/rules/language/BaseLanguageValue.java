package com.chaosserver.merp.rules.language;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.merp.rules.restriction.IRestricted;
import com.chaosserver.merp.rules.restriction.RestrictionList;

/**
 * Holds the basic information for a language.
 * <p>
 * When a character is first created they have a set of languages
 * with ranks as well as a set of potential ranks in a a language.
 * This class represents the base value that a race has in a language
 * and also contains the restriction of how high the ranks are
 * allowed to go.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class BaseLanguageValue {
    /** Holds the language. */
    protected Language language;

    /** Holds the starting value of the language. */
    protected int value;

    /** Holds the potential value of the language. */
    protected int maxValue;

    /**
     * Setter for language.
     *
     * @param language The language
     * @see #getLanguage
     */
    public void setLanguage(Language language) {
        Assertion.isNotNull(language, "Cannot change a language "
            + "once it is set.");

        this.language = language;
    }

    /**
     * Getter for language.
     *
     * @return The internal language
     * @see #setLanguage
     */
    public Language getLanguage() {
        return this.language;
    }

    /**
     * Setter for the starting value.
     *
     * @param value The starting value
     * @see #getValue
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Getter for the starting value.
     *
     * @return The value
     * @see #setValue
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Setter for the potential value.
     * <p>
     * This will create a <code>StartingLanguageRankRestriction</code>
     * associated with this class.
     * </p>
     *
     * @param maxValue The max value
     * @see #getMaxValue
     */
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * Getter for the max value.
     *
     * @return The max value
     * @see #setMaxValue
     */
    public int getMaxValue() {
        return this.maxValue;
    }
}