package com.chaosserver.merp.character;

import java.beans.PropertyVetoException;
import java.io.File;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.logging.CategoryCache;
import com.chaosserver.data.RollTable;
import com.chaosserver.merp.character.attribute.AttributeMap;
import com.chaosserver.merp.character.language.BaseCharLanguage;
import com.chaosserver.merp.character.language.BaseCharLanguageList;
import com.chaosserver.merp.character.language.CharLanguageFactory;
import com.chaosserver.merp.character.language.CharLanguageListIterator;
import com.chaosserver.merp.character.language.MaxRanksExceededX;
import com.chaosserver.merp.character.magic.AdolescentSpellListEA;
import com.chaosserver.merp.character.magic.CharSpellListFactory;
import com.chaosserver.merp.character.skill.CharSkillListFactory;
import com.chaosserver.merp.character.stat.standard.StandardCharStatListFactory;
import com.chaosserver.merp.data.FileNameGetter;
import com.chaosserver.merp.dice.DieBase;
import com.chaosserver.merp.rules.language.Language;
import com.chaosserver.merp.rules.language.LanguageList;
import com.chaosserver.merp.rules.language.LanguageListIterator;
import com.chaosserver.merp.rules.magic.realm.Realm;
import com.chaosserver.merp.rules.magic.SpellLists;
import com.chaosserver.merp.rules.magic.SpellList;
import com.chaosserver.merp.rules.magic.SpellListIterator;
import com.chaosserver.merp.rules.profession.Profession;
import com.chaosserver.merp.rules.race.Race;

/**
 * Provides various standard ways for creating a new MerpCharacter.
 * <p>
 * The factory should always be used as the object of choice when
 * attempting to create a character.  Character creation/read/write should
 * never be done directly on the MerpCharacter.
 * </p>
 * <p>
 * This character generator provides methods for creation of a blank character
 * that can then be used to fill in with other character generation processes.
 * </p>
 * <p>
 * Also included in this object are methods to allow the random creation of
 * a character or various attributes of that character.
 * </p>
 * <p>
 * This object is a factory singleton.  All methods on this object are static and
 * should be called directory, but it is possible to make a single instance
 * if desired.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class CharacterFactory {
    /** Singleton self reference. */
    private static CharacterFactory self;

    /** Singleton. */
    public CharacterFactory() {
        Assertion.isTrue(self == null, "Should not call constructor of "
            + "a singleton after it has been constructed");
    }

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static CharacterFactory instance() {
        if(self == null) {
            self = new CharacterFactory();
        }

        return self;
    }

    /**
     * Creates an empty character ready to build made by another process.
     *
     * @return An empty merp character to work with
     */
    public static MerpCharacter createCharacter() {
        MerpCharacter character = new MerpCharacter();
        CharSkillListFactory.createSkillList(character);
        CharSpellListFactory.createCharSpellList(character);
        createAttributeMap(character);

        return character;
    }

    /**
     * Creates an empty extended attribute map on a character if the character
     * does not already have one.
     * <p>
     * This method should be called as part of standard character creation
     * when the empty character is being built.
     * </p>
     *
     * @param character The character to add the attribute list to
     */
    public static void createAttributeMap(MerpCharacter character) {
        if(character.getAttributeMap() == null) {
            character.setAttributeMap(new AttributeMap());
        }
    }

    /**
     * Creates a brand new 1st level character with totally random options.
     *
     * @return A new character object random for first level
     */
    public static MerpCharacter createRandomCharacter() {
        MerpCharacter character = createCharacter();

        // Create the basic character template
        character.createCharStatList(StandardCharStatListFactory.instance());

        // Set the birth attributes
        setRandomRace(character);
        setRandomProfession(character);
        setRandomRealm(character);

        // Solidify the basic skills based on race
        CharSkillListFactory.addBaseRaceSkills(character);

        // Build the base language list
        character.setCharLanguageList(CharLanguageFactory.createBaseCharLanguageList(character));

        // Fill in the adolesence attributes
        addRandomLanguageRanks(character);
        if(CharSpellListFactory.hasAdolesenceSpellList(character)) {
            addRandomSpellList(character);
        }


        // Solidify the adolesence attributes
        CharLanguageFactory.createCharLanguageList(character);

        return character;
    }

    /**
     * Returns a random race based on the Race Selector Table.
     *
     * @return A random race
     * @see #setRandomRace
     */
    public static Race getRandomRace() {
        RollTable m_raceRoller = new RollTable(new File(FileNameGetter.XML_RACE_SELECTOR_TABLE));
        return (Race) m_raceRoller.getNewResult();
    }

    /**
     * Sets a random race on a character.
     * <p>
     * This method will use the Race Selector Table to generate a random
     * race and attempt to set it on the character.  If there are
     * PropertyVetoExceptions, it will try again until it can find
     * a successful race.
     * </p>
     *
     * @param character The merp character to assign the race to
     * @see #getRandomRace
     */
    public static void setRandomRace(MerpCharacter character) {
        boolean success = false;
        do {
            try {
                character.setRace(getRandomRace());
                success = true;
            }
            catch (PropertyVetoException e) {
                CategoryCache.getInstance(CharacterFactory.class).info("Failed to set random race: " + e);
            }
        } while (!success);
    }

    /**
     * Returns a random profession based on the profession selector table.
     *
     * @return A random profession
     * @see #setRandomProfession
     */
    public static Profession getRandomProfession() {
        RollTable m_professionRoller = new RollTable(new File(FileNameGetter.XML_PROFESSION_SELECTOR_TABLE));
        return (Profession) m_professionRoller.getNewResult();
    }

    /**
     * Sets a random profession on a character
     * <p>
     * This method will use the Profession Selector Table to generate a random
     * profession and attempt to set it on the character.  If there are
     * PropertyVetoExceptions, it will try again until it can find
     * a successful race.
     * </p>
     *
     * @param character The character to assign the profession to
     * @see #getRandomProfession
     */
    public static void setRandomProfession(MerpCharacter character) {
        boolean success = false;
        do {
            try {
                character.setProfession(getRandomProfession());
                success = true;
            }
            catch (PropertyVetoException e) {
                CategoryCache.getInstance(CharacterFactory.class).info("Failed to set random profession: " + e);
            }
        } while (!success);
    }

    /**
     * Returns a random realm based on a the realm selector table.
     *
     * @return A random realm
     * @see #setRandomRealm
     */
    public static Realm getRandomRealm() {
        RollTable realmRoller = new RollTable(new File(FileNameGetter.XML_REALM_SELECTOR_TABLE));
        return (Realm) realmRoller.getNewResult();
    }

    /**
     * Sets a random magic realm on a character
     * <p>
     * This method will use the Realm Selector Table to generate a random
     * realm and attempt to set it on the character.  If there are
     * PropertyVetoExceptions, it will try again until it can find
     * a successful race.
     * </p>
     *
     * @param character The character to assign the realm to
     * @see #getRandomRealm
     */
    public static void setRandomRealm(MerpCharacter character) {
        boolean success = false;
        do {
            try {
                character.setRealm(getRandomRealm());
                success = true;
            }
            catch (PropertyVetoException e) {
                CategoryCache.getInstance(CharacterFactory.class).info("Failed to set random realm: " + e);
            }
        } while (!success);
    }


    /**
     * Add a random language ranks to a character.
     * <p>
     * Given a character with a <code>BaseCharLanguageList</code> and a
     * free rank, this will randomly assign that rank to the character.
     * Ideally it will assign it to a random language that the character
     * already has, but under some circumstances may add an entirely
     * new language to the character and add the rank to that.
     * </p>
     * <p>
     * This method will pick a random language that the character
     * already knows and see if it is full.  If not, it will add a
     * rank to it.  If that language is full, it will pick a random
     * language again.  And so on.
     * </p>
     * <p>
     * After many failed attempts to add a rank to a language the character
     * already knows, it is assumed that most or all of the characters
     * languages are full.  A new language that the character does not
     * know will be added to the list and a rank will be added.
     * </p>
     *
     * @param character The character to add a random language rank to
     */
    public static void addRandomLanguageRank(MerpCharacter character) {
        boolean success = false;
        BaseCharLanguageList baseCharLanguageList = (BaseCharLanguageList) character.getCharLanguageList();
        Assertion.isTrue(baseCharLanguageList.getFreeRanks() > 0, "Must have free ranks to add a rank");
        int totalSize = baseCharLanguageList.size();
        int attempt = 0;

        while(!success) {
            attempt++;
            if(attempt>=10) {
                CategoryCache.getInstance(CharacterFactory.class).info("Bad luck picking random language, attempt: " + attempt);
                int languageCount = DieBase.getRandomValue(1, LanguageList.instance().size());

                CategoryCache.getInstance(CharacterFactory.class).debug("Looking for language #: " + languageCount);
                LanguageListIterator languageListIterator = LanguageList.instance().iterator();
                Language languageChoice = null;

                for(int i=0;i<languageCount;i++) {
                    languageChoice = languageListIterator.next();
                }

                if(!baseCharLanguageList.contains(languageChoice)) {
                    BaseCharLanguage newCharLanguage = new BaseCharLanguage(languageChoice, 0, 5);
                    baseCharLanguageList.add(newCharLanguage);
                    try {
                        baseCharLanguageList.addRanks(languageChoice, 1);
                        success = true;
                    }
                    catch (MaxRanksExceededX e) {

                    }
                }
            }
            else {
                int languageNumber = DieBase.getRandomValue(0, totalSize);
                try {
                    baseCharLanguageList.addRanks(languageNumber, 1);
                    CategoryCache.getInstance(CharacterFactory.class).debug("Adding rank to language #" + languageNumber);
                    success = true;
                }
                catch (MaxRanksExceededX e) {

                }
            }
        }
    }

    /**
     * Add random language ranks to a character until he is out of free ranks.
     * <p>
     * This method will continue calling the <code>addRandomLanguageRank</code>
     * method until the character is out of free ranks.
     * </p>
     *
     * @param character The character to fill with random ranks
     */
    public static void addRandomLanguageRanks(MerpCharacter character) {
        BaseCharLanguageList baseCharLanguageList = (BaseCharLanguageList) character.getCharLanguageList();
        while(baseCharLanguageList.getFreeRanks() > 0) {
            addRandomLanguageRank(character);
            CategoryCache.getInstance(CharacterFactory.class).debug("Free Ranks Left: " + baseCharLanguageList.getFreeRanks());
        }
    }

    /**
     * Add a random spell list to a character.
     *
     * @param character The character to add a random spell list to.
     */
    protected static void addRandomSpellList(MerpCharacter character) {
        boolean success = false;

        while(!success) {
            int spellNumber = DieBase.getRandomValue(0, SpellLists.instance().size());
            SpellListIterator spellListIterator = SpellLists.instance().iterator();
            SpellList spellList = null;

            for(int i=0;i<spellNumber;i++) {
                spellList = spellListIterator.next();
            }
            if(character.isValidSpellList(spellList)) {
                CharSpellListFactory.addCharSpellList(character, spellList, 100);
                CategoryCache.getInstance(CharacterFactory.class).debug("Adding spell list: " + spellList);
                success = true;
            }
        }
    }

}