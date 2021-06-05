package com.chaosserver.merp.character;

import com.chaosserver.assertion.AssertionFailedRX;
import com.chaosserver.merp.character.language.BaseCharLanguageList;
import com.chaosserver.merp.character.language.CharLanguageFactory;
import com.chaosserver.merp.rules.magic.realm.Realm;
import com.chaosserver.merp.rules.profession.Profession;
import com.chaosserver.merp.rules.race.Race;
import com.chaosserver.merp.tester.MerpTesterBase;
import junit.framework.Test;
import junit.framework.TestSuite;

public class CharacterFactoryTester extends MerpTesterBase {
   public CharacterFactoryTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(CharacterFactoryTester.class);

        return suite;
    }

    public void testInstance() {
		CharacterFactory characterFactory = CharacterFactory.instance();
	}

    public void testConstructorFail() {
		boolean success = false;

		CharacterFactory characterFactory = CharacterFactory.instance();
		try {
			characterFactory = new CharacterFactory();
		}
		catch (AssertionFailedRX e) {
			success = true;
		}

		if(!success) {
			fail("Instantiated with constructor after instance");
		}
	}

    public void testCreateRandomCharacter() {
        MerpCharacter character = CharacterFactory.createRandomCharacter();
        if(character==null) {
			fail("Created a null reference");
		}
    }

    public void testCreateCharacter() {
		MerpCharacter character = CharacterFactory.createCharacter();
        if(character==null) {
			fail("Created a null reference");
		}
	}

	public void testGetRandomRace() {
		Race race = CharacterFactory.getRandomRace();

		if(race == null) {
			fail("getRandomRace returned a null reference");
		}
	}

	public void testSetRandomRace() {
		MerpCharacter character = CharacterFactory.createCharacter();
		if(character.getRace()!=null) {
			fail("createCharacter should produce a null race");
		}

		CharacterFactory.setRandomRace(character);

		if(character.getRace()==null) {
			fail("setRandomRace did not set the race");
		}
	}

	public void testGetRandomProfession() {
		Profession profession = CharacterFactory.getRandomProfession();

		if(profession == null) {
			fail("getRandomProfession returned a null reference");
		}

	}

	public void testSetRandomProfession() {
		MerpCharacter character = CharacterFactory.createCharacter();
		if(character.getProfession()!=null) {
			fail("createCharacter should produce a null profession");
		}

		CharacterFactory.setRandomProfession(character);

		if(character.getProfession()==null) {
			fail("setRandomProfession did not set the profession");
		}
	}

	public void testGetRandomRealm() {
		Realm realm = CharacterFactory.getRandomRealm();

		if(realm == null) {
			fail("getRandomRealm returned a null reference");
		}

	}

	public void testSetRandomRealm() {
		MerpCharacter character = CharacterFactory.createCharacter();
		if(character.getRealm()!=null) {
			fail("createCharacter should produce a null realm");
		}

		CharacterFactory.setRandomRealm(character);

		if(character.getRealm()==null) {
			fail("setRandomRealm did not set the realm");
		}
	}

	public void testAddRandomLanguageRank() {
		// To be able to add language ranks we must first build a
		// character with a baseCharLangaugeList;
		MerpCharacter character = CharacterFactory.createCharacter();
		CharacterFactory.setRandomRace(character);
		character.setCharLanguageList(CharLanguageFactory.createBaseCharLanguageList(character));
		BaseCharLanguageList baseCharLanguageList = (BaseCharLanguageList) character.getCharLanguageList();
		int freeRanks = baseCharLanguageList.getFreeRanks();

		CharacterFactory.addRandomLanguageRank(character);

		if( freeRanks != (baseCharLanguageList.getFreeRanks() + 1)
		    && freeRanks != 0) {

			fail("The count of free ranks stayed the same");
		}
	}

	public void testAddRandomLanguageRanks() {
		// To be able to add language ranks we must first build a
		// character with a baseCharLangaugeList;
		MerpCharacter character = CharacterFactory.createCharacter();
		CharacterFactory.setRandomRace(character);
		character.setCharLanguageList(CharLanguageFactory.createBaseCharLanguageList(character));

		CharacterFactory.addRandomLanguageRanks(character);

	}
}