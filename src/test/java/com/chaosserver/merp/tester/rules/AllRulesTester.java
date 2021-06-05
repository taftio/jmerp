package com.chaosserver.merp.tester.rules;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllRulesTester extends TestCase {
    public AllRulesTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(RaceListTester.suite());
        suite.addTest(ProfListTester.suite());
        suite.addTest(RealmListTester.suite());
        suite.addTest(RollTableTester.suite());
        suite.addTest(SkillCategoryListTester.suite());
        return suite;
    }

}
