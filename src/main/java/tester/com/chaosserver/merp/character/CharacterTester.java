package tester.com.chaosserver.merp.character;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.chaosserver.merp.tester.MerpTesterBase;
import tester.com.chaosserver.merp.character.attribute.AttributeTester;

public class CharacterTester extends MerpTesterBase {
    public CharacterTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(CharacterFactoryTester.suite());
        suite.addTest(AttributeTester.suite());
        return suite;
    }

}
