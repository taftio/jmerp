package tester.com.chaosserver.data;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.chaosserver.merp.tester.MerpTesterBase;

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
