package com.chaosserver.merp.data;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.logging.CategoryCache;

import java.io.InputStream;
import java.net.URL;

/**
 * Holds information about the resource files.
 * <p>
 * There are a collection of static attributes that can be referenced
 * from external classes that wish to load them up as well as some
 * wrappers for system to calls to load the resources
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class FileNameGetter {
    /** Directory for the Merp Home. */
    public static String MERP_HOME = System.getProperty("MERP_HOME");

    /** Root package for merp. */
    public static String MERP_PACKAGE_HOME = "merp";

    /** Directory for race files. */
    public static String RACE_DIR = MERP_HOME+"\\data\\game\\race";

    /** Directory for statistics files. */
    public static String STAT_DIR = MERP_HOME+"\\data\\game\\stat";

    /** Directory for magic files. */
    public static String MAGIC_DIR = MERP_HOME+"\\data\\game\\magic";

    /** Directory for realm files. */
    public static String REALM_DIR = MAGIC_DIR+"\\realm";

    /** Directory for skill files. */
    public static String SKILL_DIR = MERP_HOME+"\\data\\game\\skill";

    /** Directory for profession files. */
    public static String PROFESSION_DIR = MERP_HOME+"\\data\\game\\profession";

    /** Directory for language files. */
    public static String LANGUAGE_DIR = MERP_HOME+"\\data\\game\\language";

    /** Directory for special ability files. */
    public static String SPECIAL_ABILITY_DIR = MERP_HOME+"\\data\\game\\specialabilities";

    /** Race List XML File.. */
    public static String XML_RACELIST = RACE_DIR+"\\RaceList.xml";

    /** Statistic List XML File. */
    public static String XML_STATLIST = STAT_DIR+"\\StatList.xml";

    /** Profession list XML file. */
    public static String XML_PROFESSIONLIST = PROFESSION_DIR+"\\ProfessionList.xml";

    /** Spell categories list XML file. */
    public static String XML_SPELL_CATEGORIES = MAGIC_DIR+"\\SpellCategories.xml";

    /** Spell Lists list XML file. */
    public static String XML_SPELLLIST = MAGIC_DIR+"\\SpellLists.xml";

    /** Realm List XML file. */
    public static String XML_REALMLIST = REALM_DIR+"\\RealmList.xml";

    /** Language list XML file. */
    public static String XML_LANGUAGELIST = LANGUAGE_DIR+"\\LanguageList.xml";

    /** Array of skill list XML files. */
    public static String XML_SKILLISTS[] = {
        SKILL_DIR+"\\MovementManeuverCategory.xml",
        SKILL_DIR+"\\WeaponCategory.xml",
        SKILL_DIR+"\\GeneralCategory.xml",
        SKILL_DIR+"\\SubterfugeCategory.xml",
        SKILL_DIR+"\\MagicalCategory.xml",
        SKILL_DIR+"\\MiscellaneousCategory.xml"
    };

    /** Specil ability list XML file. */
    public static String XML_SPECIAL_ABILITY_LIST = SPECIAL_ABILITY_DIR+"\\SpecialAbilities.xml";

    /** Image location in classpath. */
    public static String IMAGE_DIR=MERP_PACKAGE_HOME+"/data/image";

    /** Location of Merp logo image. */
    public static String IMAGE_MERP_LOGO = IMAGE_DIR+"/merplogo.gif";

    /** Location of dice icon image. */
    public static String IMAGE_DICE_ICON = IMAGE_DIR+"/dice.gif";

    /** Location of race selector table XML file. */
    public static String XML_RACE_SELECTOR_TABLE = RACE_DIR+"\\RaceSelectorTable.xml";

    /** Location of profession selector table XML file. */
    public static String XML_PROFESSION_SELECTOR_TABLE = PROFESSION_DIR+"\\ProfessionSelectorTable.xml";

    /** Location of realm selector table xml file. */
    public static String XML_REALM_SELECTOR_TABLE = REALM_DIR+"\\RealmSelectorTable.xml";

    /** Location of the special ability table xml file. */
    public static String XML_SPECIAL_ABILITY_TABLE = SPECIAL_ABILITY_DIR+"\\SpecialAbilityTable.xml";

    /** Location of the special ability panels factory xml file. */
    public static String XML_SPECIAL_ABILITY_PANEL_FACTORY = SPECIAL_ABILITY_DIR+"\\SpecialAbilityPanelFactory.xml";

    /** Location of stat bonus lookup table. */
    public static String XML_STAT_BONUS_LOOKUP = STAT_DIR+"\\StatBonusLookup.xml";

    /** Configuration directory. */
    public static String CONFIG_DIR=MERP_HOME+"\\data\\config";

    /** Location of the logging XML configuration. */
    public static String XML_LOGGING_CONFIG = CONFIG_DIR+"\\logging.xml";

    /** Location of the logging properties file. */
    public static String PROPERTIES_LOGGING_CONFIG = CONFIG_DIR+"\\logging.properties";

    /**
     * Classloads the resourceName as a URL.
     *
     * @param resourceName the resource name to load
     * @return The resource URL
     */
    public static URL getResourceURL(String resourceName) {
        ClassLoader classLoader = resourceName.getClass().getClassLoader();
        if(classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }

        CategoryCache.getInstance(FileNameGetter.class).info("Looking up: " + resourceName);
        URL result = classLoader.getResource(resourceName);

        Assertion.isNotNull(result, "Unable to load resource through system classloader.  "
            + "If you are in development, you have probably forgotten to compile the data "
            + "tree so that the images are moved into the classpath: " + resourceName);

        return result;

    }

    /**
     * Classloads the resourceName as an inputstream.
     *
     * @param resourceName the resource name to load
     * @return The input of the resource
     */
    public static InputStream getInputStream(String resourceName) {
        ClassLoader classLoader = resourceName.getClass().getClassLoader();
        if(classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }

        CategoryCache.getInstance(FileNameGetter.class).info("Looking up: " + resourceName);
        return classLoader.getSystemResourceAsStream(resourceName);
    }


}