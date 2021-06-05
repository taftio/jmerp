package com.chaosserver.merp.rules.magic;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.exception.NotFoundX;
import com.chaosserver.logging.CategoryCache;

/**
 * Use this package to find a realm.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class SpellCategoryFinder {
    /** Singleton self reference. */
    private static SpellCategoryFinder self;

    /** Protected constructor to force all access through the instance method. */
    protected SpellCategoryFinder() {
        Assertion.isTrue(self == null, "Should not call constructor of "
            + "a singleton after it has been constructed");
    }

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static SpellCategoryFinder instance() {
        if(self == null) {
            self = new SpellCategoryFinder();
        }

        return self;
    }

    /**
     * Finds the spell category by name from the system spell category list.
     *
     * @param name The name to find by
     * @return the spell category with the given name
     * @throws NotFoundX If the spell category cannot be found
     */
    public static SpellCategory findByName(String name) throws NotFoundX {
        return findByName(name, SpellCategories.instance());

    }

    /**
     * Finds the spell category by name from the given spell category list.
     *
     * @param name The name to find by
     * @param spellCategories the spell categories to look in
     * @return the spell category with the given name
     * @throws NotFoundX If the spell category cannot be found
     */
    public static SpellCategory findByName(String name, SpellCategories spellCategories) throws NotFoundX {
        CategoryCache.getInstance(SpellCategoryFinder.class).debug("Searching for spell category: " + name);
        SpellCategoriesIterator spellCategoriesIterator = spellCategories.iterator();

        while(spellCategoriesIterator.hasNext()) {
            SpellCategory spellCategory = (SpellCategory) spellCategoriesIterator.next();
            if(spellCategory.getName().equals(name)) {
                return spellCategory;
            }
        }

        CategoryCache.getInstance(SpellCategoryFinder.class).info("Searched for non-existent spell category: " + name);
        throw new NotFoundX("Couldn't find spell category named: " + name);
    }

}