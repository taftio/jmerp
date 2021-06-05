package com.chaosserver.merp.tester.rules;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.chaosserver.merp.rules.magic.realm.Realm;
import com.chaosserver.merp.rules.magic.realm.RealmFinder;
import com.chaosserver.merp.rules.magic.realm.RealmList;
import com.chaosserver.merp.rules.magic.realm.RealmListIterator;
import com.chaosserver.merp.tester.MerpTesterBase;
import com.chaosserver.assertion.AssertionFailedRX;
import com.chaosserver.exception.NotFoundX;


public class RealmListTester extends MerpTesterBase {
    public RealmListTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(RealmListTester.class);

        return suite;
    }

    public void testRealmList() {
        System.out.println("RealmListTester.testRealmList()");
        try {
            RealmList pl = RealmList.instance();

            RealmListIterator myIter = pl.iterator();

            while(myIter.hasNext()) {
                Realm myRealm = (Realm) myIter.next();
            }

        } catch (AssertionFailedRX e) {
            fail(e.toString());
        }
    }

    public void testRealmFinder() {
        System.out.println("RealmListTester.testRealmFinder()");
        try {
            RealmFinder rf = RealmFinder.instance();
            Realm myRealm = rf.findByName("Essence");
            myRealm = rf.findByName("Channeling");
        } catch (AssertionFailedRX e) {
            fail(e.toString());
        } catch (NotFoundX e) {
            fail(e.toString());
        }
    }

}