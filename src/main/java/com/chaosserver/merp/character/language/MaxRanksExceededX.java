package com.chaosserver.merp.character.language;

import com.chaosserver.merp.rules.language.Language;

/**
 * An attempt has been made to add ranks to a language that exceed
 * the maximum amount of ranks the language can hold.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class MaxRanksExceededX extends CharLanguageX {
    /** Max ranks that have been exceeded. */
    protected int maxRanks;

    /** Language that has been violated. */
    protected Language language;

    /**
     * Setter for max ranks.
     *
     * @param maxRanks The max ranks for the language
     * @see #getMaxRanks
     */
    public void setMaxRanks(int maxRanks) {
        this.maxRanks = maxRanks;
    }

    /**
     * Getter for max ranks.
     *
     * @return Max ranks that have been exceeded
     * @see #setMaxRanks
     */
    public int getMaxRanks() {
        return this.maxRanks;
    }

    /**
     * Setter for the language maxed over.
     *
     * @param language The language
     * @see #getLanguage
     */
    public void setLanguage(Language language) {
        this.language = language;
    }

    /**
     * getter for the language.
     *
     * @return the language
     * @see #setLanguage
     */
    public Language getLanguage() {
        return this.language;
    }

    /**
     * Base constructor.
     *
     * @param maxRanks max ranks for language
     * @param language Language that has been over ranked
     */
    public MaxRanksExceededX(int maxRanks, Language language) {
        super("Max ranks of '" + maxRanks + "' exceeded for language '" + language + "'");
        setMaxRanks(maxRanks);
        setLanguage(language);
    }

    /**
     * Base constructor.
     *
     * @param maxRanks max ranks for language
     * @param language Language that has been over ranked
     * @param t Wrapped exception
     */
    public MaxRanksExceededX(int maxRanks, Language language, Throwable t) {
        super("Max ranks of '" + maxRanks + "' exceeded for language '" + language + "'", t);
        setMaxRanks(maxRanks);
        setLanguage(language);
    }

}