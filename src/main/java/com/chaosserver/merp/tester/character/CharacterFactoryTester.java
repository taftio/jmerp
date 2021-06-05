package com.chaosserver.merp.tester.character;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.character.CharacterFactory;
import com.chaosserver.merp.tester.MerpTesterBase;

public class CharacterFactoryTester extends MerpTesterBase {
   public CharacterFactoryTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(CharacterFactoryTester.class);

        return suite;
    }

    public void testRandomCharacter() {
		System.out.println("CharacterFactoryTester.testRandomCharacter()");
        //System.out.println("Getting factory");
        MerpCharacter character = CharacterFactory.createRandomCharacter();

        //System.out.println("\nStats:");
        //System.out.println(character.getCharStatList().toString());

        //System.out.println("\nRace:");
        //System.out.println(character.getRace().getName());

        //System.out.println("\nProfession:");
        //System.out.println(character.getProfession().getName());

    }
}