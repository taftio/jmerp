package com.chaosserver.merp.character.magic;

import com.chaosserver.logging.CategoryCache;
import com.chaosserver.assertion.Assertion;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.rules.magic.SpellList;
import com.chaosserver.merp.rules.race.Race;
import com.chaosserver.merp.dice.ClosedPercentileDice;

/**
 * Used to create and develop spell lists on a character.
 * <p>
 * This object is a factory singleton.  All methods on this object are static and
 * should be called directory, but it is possible to make a single instance
 * if desired.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class CharSpellListFactory {
    /** Singleton self reference. */
    private static CharSpellListFactory self;

    /** Name for holding the extended attribute of adolescent SPell. */
    public static String ADOLESCENT_SPELL = "com.chaosserver.merp.character.CharacterFactory.ADOLESCENT_SPELL";

    /** Singleton. */
    public CharSpellListFactory() {
        Assertion.isTrue(self == null, "Should not call constructor of "
            + "a singleton after it has been constructed");
    }

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static CharSpellListFactory instance() {
        if(self == null) {
            self = new CharSpellListFactory();
        }

        return self;
    }

    /**
     * Creates an empty character spell list on a character.
     *
     * @param character The character to create the spell list on
     */
    public static void createCharSpellList(MerpCharacter character) {
        CharSpellLists charSpellLists = new CharSpellLists();

        character.setCharSpellLists(charSpellLists);

    }

    /**
     * Adds a spell list to the list of spell that a character knows.
     *
     * @param character The character to add the list to
     * @param spellList The list to add to the character
     * @param percentKnown The percent chance the character has to learn the list
     * @return The new character spell list added to the character
     */
    public static CharSpellList addCharSpellList(MerpCharacter character, SpellList spellList, int percentKnown) {
        Assertion.isNotNull(spellList, "Cannot add a null spell list to a character");
        Assertion.isTrue(character.isValidSpellList(spellList), "Spell list must be valid to add to character");

        CharSpellList charSpellList = new CharSpellList(spellList, percentKnown);
        CharSpellLists charSpellLists = character.getCharSpellLists();
        charSpellLists.addCharSpellList(charSpellList);

        return charSpellList;
    }

    /**
     * Tests if a character has a adolesence spell list.
     * <p>
     * If the character is zero level and has not been assigned a spell list
     * yet, this will make the roll on the character to see if it needs to
     * get a spell list.  The result is stored as an EA on the character
     * until it reaches the next level.
     * </p>
     *
     * @param character The character to check for an adolescence spell list
     * @return If the character has an adolescent spell list
     */
    public static boolean hasAdolesenceSpellList(MerpCharacter character) {
        boolean result = false;

        // First check if there's an EA on the character for this.
        AdolescentSpellListEA adolescentSpellListEA = (AdolescentSpellListEA) character.getAttribute(ADOLESCENT_SPELL);

        if(adolescentSpellListEA == null) {
            // The roll hasn't been made.  So make it and store it in an EA.
            result = hasRolledAdolesenceSpellList(character);
            CategoryCache.getInstance(CharSpellListFactory.class).debug("No EA for adolescent spell list, so make roll: " + result);
            character.putAttribute(new AdolescentSpellListEA(ADOLESCENT_SPELL, result, 1));
        }
        else {
            result = adolescentSpellListEA.hasAdolescentSpellList();
            CategoryCache.getInstance(CharSpellListFactory.class).debug("Got result for EA: " + result);
        }

        return result;
    }

    /**
     * Determines if the character has rolled successfully for an adolescent spell list.
     * <p>
     * This information is rolled based on the character's race
     * </p>
     *
     * @param character The character to check on
     * @return If the character has rolled successfully for an adolescent spell list
     */
    protected static boolean hasRolledAdolesenceSpellList(MerpCharacter character) {
        Race race = character.getRace();

        int chance = race.getChangeOfSpellList();
        ClosedPercentileDice cpd = new ClosedPercentileDice();
        int roll = cpd.getRolledValue();

        CategoryCache.getInstance(CharSpellListFactory.class).debug("Change of spell list: " + chance + ", rolled: " + roll);
        if(roll <= chance) {
            return true;
        }
        else {
            return false;
        }
    }

}