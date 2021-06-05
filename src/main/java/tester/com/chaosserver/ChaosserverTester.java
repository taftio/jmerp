package tester.com.chaosserver;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.chaosserver.merp.tester.MerpTesterBase;
import tester.com.chaosserver.data.DataTester;
import tester.com.chaosserver.merp.MerpTester;

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
