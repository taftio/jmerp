package com.chaosserver.merp.tester.rules.magic;

import java.util.Collection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.chaosserver.assertion.AssertionFailedRX;
import com.chaosserver.exception.NotFoundX;
import com.chaosserver.merp.tester.MerpTesterBase;
import com.chaosserver.merp.rules.magic.SpellCategories;
import com.chaosserver.merp.rules.magic.SpellCategoryFinder;
import com.chaosserver.merp.rules.magic.SpellCategory;

public class SpellCategoryFinderTester extends MerpTesterBase {
   public SpellCategoryFinderTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(SpellCategoryFinderTester.class);

        return suite;
    }

    protected void setUp() {
		SpellCategories spellCategories = SpellCategories.instance();
	}

	public void testInstance() {
		SpellCategoryFinder spellCategoryFinder = SpellCategoryFinder.instance();
		if(spellCategoryFinder == null) {
			fail("Failed to get instance of spellCategoryFinder");
		}

	}

	public void testFindCorrectStatic() {
		SpellCategory spellCategory;

		// First find by static method
		try {
			spellCategory = SpellCategoryFinder.findByName("Open Essence");
			spellCategory = SpellCategoryFinder.findByName("Bard");
		} catch (NotFoundX e) {
			fail("Failed to find 'Open Essence' with static finder."  + e.toString());
		}
	}

	public void testFindCorrect() {
		SpellCategoryFinder spellCategoryFinder = SpellCategoryFinder.instance();
		SpellCategory spellCategory;

		// First find by static method
		try {
			spellCategory = spellCategoryFinder.findByName("Open Channeling");
			spellCategory = spellCategoryFinder.findByName("Mage");
		} catch (NotFoundX e) {
			fail("Failed to find 'Open Essence' with non-static finder."  + e.toString());
		}
	}

	public void testNotFoundStatic() {
		SpellCategory spellCategory;
		boolean success = false;

		// First find by static method
		try {
			spellCategory = SpellCategoryFinder.findByName("Tom");
		} catch (NotFoundX e) {
			success = true;
		}

		if(!success) {
			fail("Somehow I found a spell category: Tom");
		}
	}

	public void testNotFound() {
		SpellCategoryFinder spellCategoryFinder = SpellCategoryFinder.instance();
		SpellCategory spellCategory;
		boolean success = false;

		// First find by static method
		try {
			spellCategory = spellCategoryFinder.findByName("Jerry");
		} catch (NotFoundX e) {
			success = true;
		}

		if(!success) {
			fail("Somehow I found a spell category: Jerry");
		}
	}

}