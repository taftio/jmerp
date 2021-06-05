package com.chaosserver;

import com.chaosserver.data.DataTester;
import com.chaosserver.merp.MerpTester;
import com.chaosserver.merp.tester.MerpTesterBase;
import junit.framework.Test;
import junit.framework.TestSuite;

public class ChaosserverTester extends MerpTesterBase {
    public ChaosserverTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(DataTester.suite());
        suite.addTest(MerpTester.suite());
        return suite;
    }
}
