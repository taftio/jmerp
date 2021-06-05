package tester.com.chaosserver.merp.gui.swing.specialabilities;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.chaosserver.merp.tester.MerpTesterBase;
import com.chaosserver.merp.gui.swing.specialabilities.SpecialAbilityPanelFactory;

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