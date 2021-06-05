package com.chaosserver.merp.character.language;

import com.chaosserver.merp.rules.language.BaseLanguageValue;
import com.chaosserver.merp.rules.language.Language;
import com.chaosserver.logging.CategoryCache;

import java.beans.PropertyVetoException;

/**
 * Holds a characters starting proficieny in a language.
 * <p>
 * When a character is first created at zero level, each
 * race begins with certain languages and an allowable range
 * of places where they can add ranks to learn other languages.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 *
 * @see com.chaosserver.merp.rules.language.Language
 */
public class BaseCharLanguage extends CharLanguage {
    /** The maximum ranks a character can start with in a skill. */
    protected int maxRanks;

    /** Event label for the max ranks property. */
    public static String P_MAX_RANKS = "CharLanguage.MaxRanks";

    /**
     * Empty constructor for bean loading.
     */
    public BaseCharLanguage() {
    }

    /**
     * Constructor that takes in basic information.
     *
     * @param language The language this represents
     * @param ranks The ranks the character starts with
     * @param maxRanks Maximum ranks a character can have in this language.
     */
    public BaseCharLanguage(Language language, int ranks, int maxRanks) {
        super(language, ranks);
        setMaxRanks(maxRanks);
    }

    /**
     * Constructor to build from a base language value.
     *
     * @param baseLanguageValue The base object to build this off of
     */
    public BaseCharLanguage(BaseLanguageValue baseLanguageValue) {
        super(baseLanguageValue.getLanguage(), baseLanguageValue.getValue());
        setMaxRanks(baseLanguageValue.getMaxValue());

    }


    /**
     * Getter for the max ranks.
     *
     * @return The max ranks
     * @see #setMaxRanks
     */
    public int getMaxRanks() {
        return this.maxRanks;
    }

    /**
     * Setter for the max ranks.
     *
     * @param maxRanks The max ranks
     * @see #getMaxRanks
     */
    public void setMaxRanks(int maxRanks) {
        this.maxRanks = maxRanks;
    }

    /**
     * Adds ranks to the number of ranks.
     * <p>
     * If the number of ranks is greater than the maximum
     * ranks this will throw a veto exception.
     * </p>
     *
     * @param ranks The number of ranks to add
     * @throws MaxRanksExceededX The player has exceeded the maximum ranks
     *         allowed in this language
     */
    public void addRanks(int ranks) throws MaxRanksExceededX {
        if( (this.ranks + ranks) > getMaxRanks()) {
            throw new MaxRanksExceededX(getMaxRanks(), getLanguage());
        }

        try {
            setRanks(getRanks() + ranks);
        } catch (PropertyVetoException e) {
            CategoryCache.getInstance(this).error("UI should allow adding to incorrect ranks", e);
        }
    }

}