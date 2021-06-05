package com.chaosserver.merp.rules.magic;

import com.chaosserver.merp.tester.MerpTesterBase;
import junit.framework.Test;
import junit.framework.TestSuite;

public class SpellListFinderTester extends MerpTesterBase {
    public SpellListFinderTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(SpellListFinderTester.class);

        return suite;
    }

    public void testInstance() {
        SpellListFinder spellListFinder = SpellListFinder.instance();
    }

    public void testFindAll() {
        SpellListFinder spellListFinder = SpellListFinder.instance();
        ISpellListIterator spellListIterator = spellListFinder.findAll();

        int count = 0;
        while(spellListIterator.hasNext()) {
            count++;
            spellListIterator.next();
        }

        if( !(SpellListsTester.SPELL_LIST_COUNT==count) ) {
            fail("Wrong count of spell lists");
        }

    }

}