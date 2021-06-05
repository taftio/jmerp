package com.chaosserver.merp;

import com.chaosserver.merp.character.CharacterTester;
import com.chaosserver.merp.rules.RulesTester;
import com.chaosserver.merp.tester.MerpTesterBase;
import junit.framework.Test;
import junit.framework.TestSuite;

public class MerpTester extends MerpTesterBase {
    public MerpTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(RulesTester.suite());
        suite.addTest(CharacterTester.suite());
        return suite;
    }

}
