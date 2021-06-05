package com.chaosserver.merp.tester.rules;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.chaosserver.merp.rules.language.Language;
import com.chaosserver.merp.rules.language.LanguageFinder;
import com.chaosserver.merp.rules.language.LanguageList;
import com.chaosserver.merp.rules.language.LanguageListIterator;
import com.chaosserver.assertion.AssertionFailedRX;
import com.chaosserver.exception.NotFoundX;
import com.chaosserver.merp.tester.MerpTesterBase;

/**
 * Test suite for the language list
 * <p>
 * This suite includes that will:
 * <ul>
 *   <li>
 *     Load and iterator through the language list validating it
 *     has the right number of entries
 *   </li>
 *   <li>
 *     Look for <code>Westron</code> and <code>Black Speech</code>
 *     with the <code>Finder</code>.
 *   </li>
 *   <li>
 *     Look for <code>Pig Latiny</code> to make sure it can't find it
 *   </li>
 * </ul>
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning 3/31/01
 */
public class LanguageListTester extends MerpTesterBase {
    /** total number of languages */
    protected static int TOTAL_LANGUAGE = 21;

    public LanguageListTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(LanguageListTester.class);

        return suite;
    }

    /**
     * Cycles through the language list countring the languages.
     * <p>
     * This will make sure there are exactly <code>TOTAL_LANGUAGE</code>
     * languages in the list
     * </p>
     */
    public void testLanguageList() {
        System.out.println("LangaugeListTester.testRealmList()");
        int listTotal = 0;
        try {
            LanguageList pl = LanguageList.instance();

            LanguageListIterator myIter = pl.iterator();

            while(myIter.hasNext()) {
                listTotal++;
                Language myLanguage = myIter.next();
            }

        } catch (AssertionFailedRX e) {
            fail(e.toString());
        }

        if(listTotal != TOTAL_LANGUAGE) {
            fail("There should have been [" + TOTAL_LANGUAGE
                 + "] languages, but only [" + listTotal
                 + "were found");
        }
    }

    /**
     * Test the langauge finder success
     * <p>
     * Looks for Westron and Black Speech
     * </p>
     */
    public void testLanguageFinder() {
        System.out.println("LanguageListTester.testLanguageFinder()");
        try {
            LanguageFinder lf = LanguageFinder.instance();
            Language myLanguage = lf.findByName("Westron");
            myLanguage = lf.findByName("Black Speech");
        } catch (AssertionFailedRX e) {
            fail(e.toString());
        } catch (NotFoundX e) {
            fail(e.toString());
        }
    }

    /**
     * Test hte language finder failuer
     * <p>
     * Looks for pig latiny and expects a NotFoundX to be thrown
     * </p>
     */
    public void testLanguageFinderFail() {
        System.out.println("LanguageListTester.testLanguageFinderFail()");
        boolean success = false;
        try {
            LanguageFinder lf = LanguageFinder.instance();
            Language myLanguage = lf.findByName("Pig Latiny");
        } catch (AssertionFailedRX e) {
            fail(e.toString());
        } catch (NotFoundX e) {
            success = true;
        }

        if(!success) {
            fail("Searched for [Pig Latiny] and found it as a language!");
        }
    }

}