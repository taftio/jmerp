package com.chaosserver.merp.tester.rules;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.chaosserver.assertion.AssertionFailedRX;
import com.chaosserver.exception.NotFoundX;
import com.chaosserver.merp.rules.race.Race;
import com.chaosserver.merp.rules.race.RaceFinder;
import com.chaosserver.merp.rules.race.RaceList;
import com.chaosserver.merp.rules.race.RaceListIterator;
import com.chaosserver.merp.rules.skill.BaseSkillValue;
import com.chaosserver.merp.tester.MerpTesterBase;

public class RaceListTester extends MerpTesterBase {
    /** The number of races that SHOULD be in the raceList */
    public static int RACELIST_SIZE = 28;

    public RaceListTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(RaceListTester.class);

        return suite;
    }

    public void testRaceList() {
        System.out.println("RaceListTester.testRaceList()");
        try {
            RaceList rl = RaceList.instance();
            RaceListIterator li = rl.iterator();

            if(rl.size() != RACELIST_SIZE) {
                fail("The race list is not the correct size.  "
                     + "It should be " + RACELIST_SIZE + ", but instead"
                     + "it is: " + rl.size());
            }

            int rlCount = 0;
            while(li.hasNext()) {
                rlCount++;
                Race race = (Race) li.next();
                Collection skill = race.getBaseSkillList();
                if(skill != null) {
                    // System.out.println("Name: " + race.getName());
                    Iterator skillIter = skill.iterator();
                    while(skillIter.hasNext()) {
                        BaseSkillValue mySkill = (BaseSkillValue) skillIter.next();
                        // System.out.println(mySkill.toString());
                    }
                }
            }

            if(rlCount != RACELIST_SIZE) {
                fail("The race list iterator did not go over the correct"
                    + " number of races.  It should have iterator over "
                    + RACELIST_SIZE + ", but instead it did: " + rlCount);
            }

        } catch (AssertionFailedRX e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void testRaceFinder() {
        System.out.println("RaceListTester.testRaceFinder()");
        RaceFinder rf = RaceFinder.instance();

        try {
            rf.findByName("Noldo Elf");
            rf.findByName("Hobbit");
        } catch (AssertionFailedRX e) {
            fail(e.toString());
        } catch (NotFoundX e) {
            fail(e.toString());
        }

        // Now test a bad value
        boolean success = true;
        try {
            rf.findByName("Gupata");
            success = false;
        } catch (AssertionFailedRX e) {
            fail(e.toString());
        } catch (NotFoundX e) {
            // This is the expected result
        }

        if(success == false) {
            fail("Tried to find Gupata, and did not throw NotFoundX");
        }

    }
}