package com.chaosserver.merp.tester.rules.magic;

import java.util.Collection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.chaosserver.assertion.AssertionFailedRX;
import com.chaosserver.merp.rules.magic.SpellCategories;
import com.chaosserver.merp.rules.magic.SpellCategoriesIterator;
import com.chaosserver.merp.rules.magic.SpellCategory;
import com.chaosserver.merp.tester.MerpTesterBase;

public class SpellCategoriesIteratorTester extends MerpTesterBase {
   public SpellCategoriesIteratorTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(SpellCategoriesIteratorTester.class);

        return suite;
    }

    protected void setUp() {
		SpellCategories spellCategories = SpellCategories.instance();
	}

	public void testCycleAll() {
		SpellCategoriesIterator spellCategoriesIterator = SpellCategories.instance().iterator();
		int count = 0;

		while(spellCategoriesIterator.hasNext()) {
			count++;
			SpellCategory spellCategory = spellCategoriesIterator.next();
		}

		if(count != SpellCategoriesTester.NUM_OF_CATEGORIES) {
			fail("Expected " + SpellCategoriesTester.NUM_OF_CATEGORIES
				+ " categories, but found: " + count);
		}
	}
}