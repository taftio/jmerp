package com.chaosserver.merp.data;

import com.chaosserver.logging.CategoryCache;
import com.chaosserver.data.JavaBeanLoader;
import com.chaosserver.data.JavaBeanLoaderExceptionX;
import com.chaosserver.merp.rules.language.LanguageList;
import com.chaosserver.merp.rules.magic.realm.RealmList;
import com.chaosserver.merp.rules.profession.ProfessionList;
import com.chaosserver.merp.rules.race.RaceList;
import com.chaosserver.merp.rules.skill.SkillCategoryList;
import com.chaosserver.merp.rules.stat.StatList;

/**
 * Boot strapping loads up all the various objects in the system.
 * <p>
 * This can be done either directly giving the user a pause or
 * one can spawn background threads to bootstrap.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class BootStrap implements Runnable {
    /** Flag to hold if the system has been bootstrapped. */
    private static boolean m_isBootStrapped = false;

    /**
     * Invokes instances of all the singletons.
     * <p>
     * <ol>
     *   <li>StatList</li>
     *   <li>RealmList</li>
     *   <li>SkillCategoryList</li>
     *   <li>ProfessionList</li>
     *   <li>LanguageList</li>
     *   <li>RaceList</li>
     *   <li>XML_STAT_BONUS_LOOKUP</li>
     * </ol>
     * </p>
     */
    public void run() {
        CategoryCache.getInstance(this).info("START");

        // No dependencies
        CategoryCache.getInstance(this).info("Loading StatList");
        StatList.instance();

        // No dependencies
        CategoryCache.getInstance(this).info("Loading RealmList");
        RealmList.instance();

        // Depends on:
        //   StatList
        CategoryCache.getInstance(this).info("Loading SkillCategoryList");
        SkillCategoryList.instance();

        // Depends on:
        //   StatList
        //   RealmList
        //   SkillList
        CategoryCache.getInstance(this).info("Loading ProfessionList");
        ProfessionList.instance();

        // No dependencies
        CategoryCache.getInstance(this).info("Loading LanguageList");
        LanguageList.instance();

        // Depends on
        //   StatList
        //   SkillList
        //   ProfessionList
        //   LanguageList
        CategoryCache.getInstance(this).info("Loading RaceList");
        RaceList.instance();

        try {
            CategoryCache.getInstance(this).info("Loading Stat Bonus Lookup");
            JavaBeanLoader.getBean(FileNameGetter.XML_STAT_BONUS_LOOKUP);
        }
        catch (JavaBeanLoaderExceptionX e) {
            CategoryCache.getInstance(this).error("Failed to load " + FileNameGetter.XML_STAT_BONUS_LOOKUP, e);
        }

        CategoryCache.getInstance(this).info("EXIT");
        m_isBootStrapped = true;
    }

    /**
     * Creates a thread at <code>MIN_PRIORITY</code> to run the bootstrap process.
     *
     * @return The started thread
     */
    public static Thread backgroundThread() {
        if(m_isBootStrapped == false) {
            Thread t = new Thread(new BootStrap(), "BootStrap");
            t.setPriority(Thread.MIN_PRIORITY);
            t.start();
            return t;
        }
        else {
            return null;
        }

    }

}