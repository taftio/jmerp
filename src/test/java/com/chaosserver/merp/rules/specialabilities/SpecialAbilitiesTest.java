package com.chaosserver.merp.rules.specialabilities;

import com.chaosserver.merp.tester.MerpTesterBase;
import java.util.Collection;
import junit.framework.Test;
import junit.framework.TestSuite;

public class SpecialAbilitiesTest extends MerpTesterBase {
    protected int SPECIAL_ABILITY_COUNT = 11;

    public SpecialAbilitiesTest(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("SpecialAbilitiesTest");
        suite.addTestSuite(SpecialAbilitiesTest.class);

        return suite;
    }

    public void testInstance() {
        SpecialAbilities specialAbilities = SpecialAbilities.instance();

        Collection abilities = specialAbilities.getSpecialAbilities();

        if(SPECIAL_ABILITY_COUNT != abilities.size()) {
            fail("Expected " + SPECIAL_ABILITY_COUNT + " abilities, but "
                + "got: " + abilities.size());
        }
    }

    public void testIterator() {
        SpecialAbilities specialAbilities = SpecialAbilities.instance();

        SpecialAbilityIterator specialAbilityIterator = specialAbilities.iterator();

        while(specialAbilityIterator.hasNext()) {
            System.out.println(specialAbilityIterator.next());
        }
    }
}