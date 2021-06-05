package com.chaosserver.merp.tester.data;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.chaosserver.merp.data.BootStrap;

public class BootStrapTester extends TestCase {
    public BootStrapTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(BootStrapTester.class);

        return suite;
    }

    public void testBackgroundThread() {
        System.out.println("BootStrapTester.BootStrapTester()");
        try {
			BootStrap.backgroundThread().join();
		}
		catch (InterruptedException e) {
			fail("Interupted");
		}
    }

}
