package com.chaosserver.merp.character.language;

import com.chaosserver.logging.CategoryCache;
import com.chaosserver.assertion.Assertion;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.rules.language.BaseLanguageList;
import com.chaosserver.merp.rules.language.BaseLanguageListIterator;
import com.chaosserver.merp.rules.race.Race;

/**
 * Used to create things relating to character languages
 * <p>
 * This object is a factory singleton.  All methods on this object are static and
 * should be called directory, but it is possible to make a single instance
 * if desired.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class CharLanguageFactory {
    /** Singleton self reference. */
    protected static CharLanguageFactory self;

    /** Private constructor. */
    private CharLanguageFactory() {}

    /**
     * Gets the single instance.
     *
     * @return The instance of the char factory
     */
    public static CharLanguageFactory getInstance() {
        if(self == null) {
            self = new CharLanguageFactory();
        }

        return self;
    }

    /**
     * Constructs a BaseCharLanguageList.
     * <p>
     * Wheren a character is first being created it needs to get a
     * BaseCharLanguageList generated based on the race.  Use this method
     * to do.  Edit the BaseCharLanguageList as appropriate.  Then this
     * can be used to create the CharLanguageList to be added to the character.
     * </p>
     *
     * @param race The race to get the BaseCharLanguageList from
     * @return A BaseCharLanguageList from the race
     */
    public static BaseCharLanguageList createBaseCharLanguageList(Race race) {
        Assertion.isNotNull(race, "Cannot create a base language list from a null race");

        BaseCharLanguageList baseCharLanguageList = new BaseCharLanguageList(race.getLanguageRanks());
        BaseLanguageListIterator baseLanguageListIterator = race.getBaseLanguageList().iterator();


        while(baseLanguageListIterator.hasNext()) {
            baseCharLanguageList.add(new BaseCharLanguage(baseLanguageListIterator.next()));
        }

        return baseCharLanguageList;
    }

    /**
     * Constructs a BaseCharLanguageList.
     * <p>
     * Wheren a character is first being created it needs to get a
     * BaseCharLanguageList generated based on the race.  Use this method
     * to do.  Edit the BaseCharLanguageList as appropriate.  Then this
     * can be used to create the CharLanguageList to be added to the character.
     * </p>
     *
     * @param character The character to get the BaseCharLanguageList from
     * @return A BaseCharLanguageList from the race
     */
    public static BaseCharLanguageList createBaseCharLanguageList(MerpCharacter character) {
        Race race = character.getRace();
        return(createBaseCharLanguageList(race));
    }

    /**
     * Converts a characters BaseCharLanguageList into a CharLanguageList.
     *
     * @param character The character to create a language list on
     */
    public static void createCharLanguageList(MerpCharacter character) {
        CharLanguageList baseCharLanguageList = character.getCharLanguageList();
        if(baseCharLanguageList instanceof BaseCharLanguageList) {
            CategoryCache.getInstance(CharLanguageFactory.class).info("Creating a CharLanguageList from BaseCharLanguageList");
            CharLanguageList charLanguageList = new CharLanguageList();
            CharLanguageListIterator charLanguageListIterator = baseCharLanguageList.iterator();
            BaseCharLanguage baseCharLanguage;

            while(charLanguageListIterator.hasNext()) {
                baseCharLanguage = (BaseCharLanguage) charLanguageListIterator.next();
                CategoryCache.getInstance(CharLanguageFactory.class).debug("Adding in: " + baseCharLanguage.getLanguage());
                if(baseCharLanguage.getRanks() > 0) {
                    charLanguageList.add(new CharLanguage(baseCharLanguage.getLanguage(), baseCharLanguage.getRanks()));
                }
            }

            character.setCharLanguageList(charLanguageList);
        }
    }
}