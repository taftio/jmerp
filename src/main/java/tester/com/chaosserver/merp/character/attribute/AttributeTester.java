package tester.com.chaosserver.merp.character.attribute;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AttributeTester extends TestCase {
    public AttributeTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(ExtendedAttributeTester.suite());
        return suite;
    }

}
