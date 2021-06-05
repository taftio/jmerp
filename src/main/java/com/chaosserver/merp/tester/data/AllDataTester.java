package com.chaosserver.merp.tester.data;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllDataTester extends TestCase {
    public AllDataTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(DataFileTester.suite());
        suite.addTest(BootStrapTester.suite());
        return suite;
    }

}
