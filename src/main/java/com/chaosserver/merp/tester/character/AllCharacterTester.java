package com.chaosserver.merp.tester.character;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllCharacterTester extends TestCase {
    public AllCharacterTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(CharacterFactoryTester.suite());
        return suite;
    }

}
