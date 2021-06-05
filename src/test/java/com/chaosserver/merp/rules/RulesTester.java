package com.chaosserver.merp.rules;

import com.chaosserver.merp.rules.magic.MagicTester;
import com.chaosserver.merp.tester.MerpTesterBase;
import junit.framework.Test;
import junit.framework.TestSuite;

public class RulesTester extends MerpTesterBase {
    public RulesTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(MagicTester.suite());
        return suite;
    }

}
