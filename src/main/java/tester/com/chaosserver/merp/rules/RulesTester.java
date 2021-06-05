package tester.com.chaosserver.merp.rules;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import tester.com.chaosserver.merp.rules.magic.MagicTester;
import com.chaosserver.merp.tester.MerpTesterBase;

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
