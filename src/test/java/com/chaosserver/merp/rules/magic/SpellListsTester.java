package com.chaosserver.merp.rules.magic;

import com.chaosserver.assertion.AssertionFailedRX;
import com.chaosserver.merp.tester.MerpTesterBase;
import java.util.Collection;
import junit.framework.Test;
import junit.framework.TestSuite;

public class SpellListsTester extends MerpTesterBase {
	public static int SPELL_LIST_COUNT = 40;
	public SpellListsTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(SpellListsTester.class);

        return suite;
    }

    public void testInstance() {
		SpellLists spellLists = SpellLists.instance();

    }

    public void testConstructorFail() {
		boolean success = false;

		SpellLists spellLists = SpellLists.instance();
		try {
			spellLists = new SpellLists();
		}
		catch (AssertionFailedRX e) {
			success = true;
		}

		if(!success) {
			fail("Instantiated with constructor after instance");
		}
	}

	public void testGetSpellLists() {
		Collection spellLists = SpellLists.instance().getSpellLists();

		if(! (SPELL_LIST_COUNT == spellLists.size())) {
			fail("Wrong count of spell lists");
		}
	}
}