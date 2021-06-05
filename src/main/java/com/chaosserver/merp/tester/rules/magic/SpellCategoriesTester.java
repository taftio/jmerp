package com.chaosserver.merp.tester.rules.magic;

import java.util.Collection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.chaosserver.assertion.AssertionFailedRX;
import com.chaosserver.merp.rules.magic.SpellCategories;
import com.chaosserver.merp.rules.magic.SpellCategoriesIterator;
import com.chaosserver.merp.tester.MerpTesterBase;


public class SpellCategoriesTester extends MerpTesterBase {
	public static int NUM_OF_CATEGORIES = 6;

	public SpellCategoriesTester(String name) {
		super(name);
	}

    public static Test suite() {
        TestSuite suite = new TestSuite(SpellCategoriesTester.class);

        return suite;
    }

    protected void setUp() {
		SpellCategories spellCategories = SpellCategories.instance();
	}

    public 	void testInstance() {
		SpellCategories spellCategories = SpellCategories.instance();
    }

    public void testFailedConstructor() {
		boolean success = false;
		try {
			SpellCategories spellCategories = new SpellCategories();
		} catch (AssertionFailedRX e) {
			success = true;
		}

		if(!success) {
			fail("Creating a second instance did not cause an assertion exception");
		}
	}

	public void testIterator() {
		SpellCategoriesIterator spellCategoriesIterator = SpellCategories.instance().iterator();
		if(spellCategoriesIterator == null) {
			fail("Got a null spellCategoriesIterator");
		}
	}
}