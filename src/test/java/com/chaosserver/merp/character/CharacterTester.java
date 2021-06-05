package com.chaosserver.merp.character;

import com.chaosserver.merp.character.attribute.AttributeTester;
import com.chaosserver.merp.tester.MerpTesterBase;
import junit.framework.Test;
import junit.framework.TestSuite;

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
