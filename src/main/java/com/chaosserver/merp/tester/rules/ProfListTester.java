package com.chaosserver.merp.tester.rules;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.chaosserver.merp.rules.profession.Profession;
import com.chaosserver.merp.rules.profession.ProfessionFinder;
import com.chaosserver.merp.rules.profession.ProfessionList;
import com.chaosserver.merp.rules.profession.ProfessionListIterator;
import com.chaosserver.assertion.AssertionFailedRX;
import com.chaosserver.exception.NotFoundX;
import com.chaosserver.merp.tester.MerpTesterBase;


public class ProfListTester extends MerpTesterBase {
    public ProfListTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(ProfListTester.class);

        return suite;
    }

    public void testProfList() {
        System.out.println("ProfListTester.testProfList()");
        try {
            ProfessionList pl = ProfessionList.instance();

            ProfessionListIterator myIter = pl.iterator();

            while(myIter.hasNext()) {
                Profession myProf = (Profession) myIter.next();
            }

        } catch (AssertionFailedRX e) {
            fail(e.toString());
        }
    }

    public void testProfessionFinder() {
        System.out.println("ProfListTester.testProfessionFinder()");
        try {
            ProfessionFinder rf = ProfessionFinder.instance();
            Profession myProfession = rf.findByName("Warrior");
            myProfession = rf.findByName("Scout");
        } catch (AssertionFailedRX e) {
            fail(e.toString());
        } catch (NotFoundX e) {
            fail(e.toString());
        }
    }

}