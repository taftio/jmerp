package com.chaosserver.merp.tester.rules;

import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.chaosserver.merp.rules.skill.Skill;
import com.chaosserver.merp.rules.skill.SkillCategory;
import com.chaosserver.merp.rules.skill.SkillCategoryIterator;
import com.chaosserver.merp.rules.skill.SkillCategoryList;
import com.chaosserver.merp.rules.skill.SkillFinder;
import com.chaosserver.assertion.AssertionFailedRX;
import com.chaosserver.exception.NotFoundX;


public class SkillCategoryListTester extends TestCase {
    public SkillCategoryListTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(SkillCategoryListTester.class);

        return suite;
    }

    public void testSkillCategoryList() {
        System.out.println("SkillCategoryListTester.testSkillCategoryList");
        try {
            SkillCategoryList scl = SkillCategoryList.instance();


            SkillCategoryIterator myIter = scl.iterator();

            while(myIter.hasNext()) {
                SkillCategory mySkillCateg = (SkillCategory) myIter.next();
                // System.out.println("Name: " + mySkillCateg.getName());
                Iterator mySkillIter = mySkillCateg.iterator();
                while(mySkillIter.hasNext()) {
                    Skill mySkill = (Skill) mySkillIter.next();
                    // System.out.println("  Name: " + ((Skill)(mySkillIter.next())).getName());
                }
            }


        } catch (AssertionFailedRX e) {
            fail(e.toString());
        }
    }

    public void testSkillFinder() {
        System.out.println("SkillCategoryListTester.testSkillFinder()");
        try {
            SkillFinder sf = SkillFinder.instance();
            Skill mySkill = sf.findByName("Thrown");
            mySkill = sf.findByName("Pick Lock");
        } catch (AssertionFailedRX e) {
            fail(e.toString());
        } catch (NotFoundX e) {
            fail(e.toString());
        }
    }

}