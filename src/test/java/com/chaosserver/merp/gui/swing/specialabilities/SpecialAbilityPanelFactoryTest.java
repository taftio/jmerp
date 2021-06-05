package com.chaosserver.merp.gui.swing.specialabilities;

import com.chaosserver.merp.tester.MerpTesterBase;
import junit.framework.Test;
import junit.framework.TestSuite;

public class SpecialAbilityPanelFactoryTest extends MerpTesterBase {
   public SpecialAbilityPanelFactoryTest(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(SpecialAbilityPanelFactoryTest.class);

        return suite;
    }

    public void testInstance() {
        SpecialAbilityPanelFactory.instance();
    }
}