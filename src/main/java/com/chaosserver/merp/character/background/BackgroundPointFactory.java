package com.chaosserver.merp.character.background;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.data.RollTable;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.character.language.CharLanguage;
import com.chaosserver.merp.character.skill.ICharSkill;
import com.chaosserver.merp.character.stat.CharStatFinder;
import com.chaosserver.merp.character.stat.ICharStat;
import com.chaosserver.merp.data.FileNameGetter;
import com.chaosserver.merp.dice.ClosedPercentileDice;
import com.chaosserver.merp.rules.race.Race;
import com.chaosserver.merp.rules.skill.Skill;
import com.chaosserver.merp.rules.specialabilities.SpecialAbility;
import com.chaosserver.logging.CategoryCache;

import java.beans.PropertyVetoException;
import java.io.File;

/**
 * The background point factory is used to manage the creation and spending of
 * background points on a character.
 * <p>
 * State information associated with a character's background points is stored
 * in a <code>BackgroundPointEA</code> extended attribute on the character.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class BackgroundPointFactory {
    /** Singleton self reference. */
    private static BackgroundPointFactory self;

    /** Name of the BackgroundPointEA extended attribute managed by this factory. */
    protected static String BACKGROUND_POINT = "com.chaosserver.merp.character.background.BackgroundPoints";

    /** Amount of ranks added to a primary skill when a background point is spent on it. */
    public static int POINTS_TO_ADD_TO_PRIMARY_SKILL = 2;

    /** Amount of ranks added to a secondary skill when a background point is spent on it. */
    public static int POINTS_TO_ADD_TO_SECONDAY_SKILL = 5;

    /**
     * Option to add a single point to a statistic.  A background point can be used to add
     * one point to three different statistics.
     */
    public static int ADD_STAT_ONE = 1;

    /** Option to add two points to a single statistic. */
    public static int ADD_STAT_TWO = 2;

    /** Singleton. */
    public BackgroundPointFactory() {
        Assertion.isTrue(self == null, "Should not call constructor of "
            + "a singleton after it has been constructed");
    }

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static BackgroundPointFactory instance() {
        if(self == null) {
            self = new BackgroundPointFactory();
        }

        return self;
    }

    /**
     * Gets the number of a background points a character currently
     * has left to spend.
     *
     * @param character The character of interest
     * @return The number of background points that character has left.
     */
    public static int getBackgroundPoints(MerpCharacter character) {
        BackgroundPointEA backgroundPointEA = getBackgroundPointsEA(character);
        return backgroundPointEA.getCurrentPoints();
    }

    /**
     * Gets a reference to the extended attribute that holds background point state.
     * <p>
     * If no extended attribute for background points exist in the character, then
     * a new one is created and added to the character.
     * </p>
     *
     * @param character The character of interest
     * @return The backgound point attribute for the character
     */
    public static BackgroundPointEA getBackgroundPointsEA(MerpCharacter character) {
        // First check if there's an EA on the character for this.
        BackgroundPointEA backgroundPointEA = (BackgroundPointEA) character.getAttribute(BACKGROUND_POINT);

        if(backgroundPointEA == null) {
            // The character has no background points so get the count
            Race race = character.getRace();
            Assertion.isNotNull(race, "Race is null, therefore no background options exist");
            int result = race.getBackgroundOptions();
            CategoryCache.getInstance(BackgroundPointFactory.class).debug("No EA for background options, so make one: " + result);
            backgroundPointEA = new BackgroundPointEA(BACKGROUND_POINT, 1, result);
            character.putAttribute(backgroundPointEA);
        }

        return backgroundPointEA;
    }

    /**
     * Spends a background point to add ranks to a characters skill.
     * <p>
     * Front ends should validate that character is able to do this action
     * by calling the <code>canAddSkillRank</code> method.  Calling this method
     * when a character cannot will result in an assertion failure.
     * </p>
     * <p>
     * Primary skill will have two ranks added.  Secondary skills will have five
     * ranks added.
     * </p>
     * <p>
     * BUGBUG: Secondary skills do not work yet
     * </p>
     *
     * @param character The character to add skill ranks to
     * @param charSkill The character skill
     */
    public static void addSkillRank(MerpCharacter character, ICharSkill charSkill) {
        CategoryCache.getInstance(BackgroundPointFactory.class).info("Adding points to : " + charSkill);
        // A CharSkill is a skill the character already has.  We know that
        // we must add 2 points to a skill that character already has.  I suppose
        // this should be in our config service somewhere, eh?

        // Validate there is a rank available.  If not, the GUI messed up.
        Assertion.isTrue(canAddSkillRank(character, charSkill), "Cannot add skill ranks");
        BackgroundPointEA backgroundPointEA = getBackgroundPointsEA(character);
        backgroundPointEA.spendPoint();
        charSkill.addRanks(POINTS_TO_ADD_TO_PRIMARY_SKILL);
    }

    /**
     * Validates a character can spend a background point to add
     * ranks to a characters skill.
     * <p>
     * BUGBUG: Secondary skills do not work yet.
     * </p>
     *
     * @param character The character to add skill ranks to
     * @param charSkill The character skill
     * @return If the character is allowed to add a skill rank to the skill
     */
    public static boolean canAddSkillRank(MerpCharacter character, ICharSkill charSkill) {
        boolean result = true;

        // BUGBUG Check for secondary skill

        if(getBackgroundPoints(character) < 1) {
            CategoryCache.getInstance(BackgroundPointFactory.class).debug("Not enough background points to add to: " + charSkill + ".  Only has: " + getBackgroundPoints(character));
            result = false;
        }

        int maxRanks = charSkill.getSkill().getMaxRanks();
        if(     maxRanks != Skill.NO_MAX
                && (charSkill.getRanks() + POINTS_TO_ADD_TO_PRIMARY_SKILL) >  maxRanks) {

            CategoryCache.getInstance(BackgroundPointFactory.class).debug("Will surpase max ranks of " + charSkill + ": " + maxRanks);
            result = false;
        }
        return result;
    }

    /**
     * Spends a background point to increase the points in a stat.
     * <p>
     * Front ends should validate that character is able to do this action
     * by calling the <code>canStatIncrease</code> method.  Calling this method
     * when a character cannot will result in an assertion failure.
     * </p>
     * <p>
     * The only valid amounts are 1 or 2.
     * </p>
     *
     * @param character The character to add stat points to
     * @param charStat The stat to add points to
     * @param amount The amount of points to add stats to.  Only 1 or 2 points may be
     *               given as a valid amount.
     */
    public static void increaseStat(MerpCharacter character, ICharStat charStat, int amount) {
        CategoryCache.getInstance(BackgroundPointFactory.class).info("Adding " + amount + " to " + charStat);

        Assertion.isTrue(canStatIncrease(character, charStat, amount), "Cannot add to stat: " + charStat);

        if(amount == ADD_STAT_TWO) {
            // Easier of the two because there's no state to worry about
            BackgroundPointEA backgroundPointEA = getBackgroundPointsEA(character);
            backgroundPointEA.spendPoint();
            charStat.adjust(amount);
        }
        else if(amount == ADD_STAT_ONE) {
            // Got some state to maintain here.
            BackgroundPointEA backgroundPointEA = getBackgroundPointsEA(character);

            if(!backgroundPointEA.isAddingToStats()) {
                backgroundPointEA.beginAddingToStats();
            }

            backgroundPointEA.spendStatPoint(charStat);
            charStat.adjust(amount);
        }
        else {
            Assertion.shouldNotReach("Invalid stat increase amount: " + amount);
        }
    }

    /**
     * Validates a character can spend a background point to increase the points in a stat.
     *
     * @param character The character to add stat points to
     * @param charStat The stat to add points to
     * @param amount The amount of points to add stats to.  Only 1 or 2 points may be
     *               given as a valid amount.
     * @return If the character is able to increase a stat by the given amount
     */
    public static boolean canStatIncrease(MerpCharacter character, ICharStat charStat, int amount) {
        boolean result = true;
        BackgroundPointEA backgroundPointEA = getBackgroundPointsEA(character);

        if(amount == ADD_STAT_ONE) {
            if(backgroundPointEA.isAddingToStats()) {
                // In the middle of adding to a stat.  The only thing to stop is if this stat
                // has already been added to.
                if(backgroundPointEA.isAddingTo(charStat)) {
                    CategoryCache.getInstance(BackgroundPointFactory.class).debug("In the middle of adding to 3 stats, but already added to this");
                    result = false;
                }
            }
            else {
                // Not in the middle of adding so check if there are points to spend
                if(getBackgroundPoints(character) < 1) {
                    CategoryCache.getInstance(BackgroundPointFactory.class).debug("Not enough background points to add to: " + charStat + ".  Only has: " + getBackgroundPoints(character));
                    result = false;
                }
            }

        }
        else if(amount == ADD_STAT_TWO) {
            // Check if there are points to spend
            if(getBackgroundPoints(character) < 1) {
                CategoryCache.getInstance(BackgroundPointFactory.class).debug("Not enough background points to add to: " + charStat + ".  Only has: " + getBackgroundPoints(character));
                result = false;
            }

            if(backgroundPointEA.isAddingToStats()) {
                CategoryCache.getInstance(BackgroundPointFactory.class).debug("In the middle of adding one point to three, so block 2 point adds");
                result = false;
            }
        }

        // Never go above 102
        if(charStat.getValue() >= 102) {
            result = false;
        }

        return result;
    }

    /**
     * Check if a character can spend a background point to increase a language.
     *
     * @param character The character to check if it can increase a language
     * @param charLanguage The language to see if it can be increased
     * @return If the language can be increased
     */
    public static boolean canIncreaseLanguage(MerpCharacter character, CharLanguage charLanguage) {
        boolean result = false;

        CategoryCache.getInstance(BackgroundPointFactory.class).debug("Checking against: " + charLanguage + " [" + charLanguage.getRanks() + "]");

        if(getBackgroundPoints(character) > 0) {
            // BUGBUG Need to check against race allowance
            if(charLanguage.getRanks() < maxLanguageValue(character, charLanguage)) {
                result = true;
            }
        }

        return result;
    }

    /**
     * Returns the number of ranks a stat will increase to if a background point is spend on it.
     *
     * @param character Character to see how much the language will increase
     * @param charLangauge The language to increase
     * @return The amount the language can increase to
     */
    public static int maxLanguageValue(MerpCharacter character, CharLanguage charLangauge) {
        return 5;
    }

    /**
     * Spends a background point increasing the language ranks to their max.
     *
     * @param character The character to spend a point on
     * @param charLanguage The language to increase a point
     */
    public static void increaseLanguage(MerpCharacter character, CharLanguage charLanguage) {
        Assertion.isTrue(canIncreaseLanguage(character, charLanguage), "Cannot incrase language: " + charLanguage);

        try {
            BackgroundPointEA backgroundPointEA = getBackgroundPointsEA(character);
            backgroundPointEA.spendPoint();
            charLanguage.setRanks(maxLanguageValue(character, charLanguage));
        }
        catch (PropertyVetoException e) {
            e.printStackTrace();
            CategoryCache.getInstance(BackgroundPointFactory.class).error(e);
        }
    }

    /**
     * Check if a character can spend a background point to get a special ability.
     *
     * @param character The character to check
     * @return If the character can spend a background point to get a special ability
     */
    public static boolean canAddSpecialAbility(MerpCharacter character) {
        boolean result = false;
        BackgroundPointEA backgroundPointEA = getBackgroundPointsEA(character);

        if(getBackgroundPoints(character) > 0) {
            if(backgroundPointEA.hasSpecialAbility()) {
                result = false;
            }
            else {
                result = true;
            }
        }

        return result;
    }

    /**
     * Rolls a special ability from the table and adds it to the character.
     * <p>
     * If the special ability generated is not valid (it needs more specific
     * information set) it will be added to the EA instead of directly to
     * the character.
     * </p>
     *
     * @param character the character to roll the special ability for
     */
    public static void addSpecialAbility(MerpCharacter character) {
        CategoryCache.getInstance(BackgroundPointFactory.class).debug("Adding a special ability");
        Assertion.isTrue(canAddSpecialAbility(character), "Cannot add special ability! ");
        BackgroundPointEA backgroundPointEA = getBackgroundPointsEA(character);

        backgroundPointEA.spendPoint();
        ClosedPercentileDice die = new ClosedPercentileDice();
        // int roll = die.getRolledValue();
        int roll = 62;
        CategoryCache.getInstance(BackgroundPointFactory.class).debug("Rolled: " + roll);

        // BUGBUG: Check against the race
        RollTable abilityRoller = new RollTable(new File(FileNameGetter.XML_SPECIAL_ABILITY_TABLE));

        SpecialAbility ability = (SpecialAbility)((SpecialAbility) abilityRoller.processValue(roll)).clone();

        if(ability.isValid()) {
            applySpecialAbility(character);
        }
        else {
            backgroundPointEA.setSpecialAbility(ability);
        }

    }

    /**
     * Applies a special ability to the character from the EA.
     *
     * @param character The character to the apply the EA on.
     */
    public static void applySpecialAbility(MerpCharacter character) {
        CategoryCache.getInstance(BackgroundPointFactory.class).debug("Applying ability to character");
        BackgroundPointEA backgroundPointEA = getBackgroundPointsEA(character);
        SpecialAbility specialAbility = backgroundPointEA.getSpecialAbility();
        Assertion.isNotNull(specialAbility, "Character lacks a special ability");
        Assertion.isTrue(specialAbility.isValid(), "Unable to add an invalid "
            + "special ability to the character.");

        character.addSpecialAbility(specialAbility);
        backgroundPointEA.removeSpecialAbility();
    }
}