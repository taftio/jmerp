package tester.com.chaosserver.merp.rules.magic;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class MagicTester extends TestCase {
    public MagicTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(SpellListsTester.suite());
        //suite.addTestSuite(SpellListsTester.);
        suite.addTest(SpellListFinderTester.suite());
        return suite;
    }

}
