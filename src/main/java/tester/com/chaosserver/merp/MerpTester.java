package tester.com.chaosserver.merp;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.chaosserver.merp.tester.MerpTesterBase;
import tester.com.chaosserver.merp.rules.RulesTester;
import tester.com.chaosserver.merp.character.CharacterTester;

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
