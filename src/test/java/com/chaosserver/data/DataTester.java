package com.chaosserver.data;

import com.chaosserver.merp.tester.MerpTesterBase;
import junit.framework.Test;
import junit.framework.TestSuite;

public class DataTester extends MerpTesterBase {
    public DataTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(JavaBeanWriterTester.suite());
        return suite;
    }

}
