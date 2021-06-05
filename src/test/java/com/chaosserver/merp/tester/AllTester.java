package com.chaosserver.merp.tester;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.chaosserver.merp.tester.data.AllDataTester;
import com.chaosserver.merp.tester.rules.AllRulesTester;
import com.chaosserver.merp.tester.character.AllCharacterTester;

public class AllTester extends TestCase {
    public AllTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(AllDataTester.suite());
        suite.addTest(AllRulesTester.suite());
        suite.addTest(AllCharacterTester.suite());
        return suite;
    }

}
