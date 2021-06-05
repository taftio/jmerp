package com.chaosserver.merp.character.background;

import com.chaosserver.merp.character.CharacterFactory;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.rules.race.Race;
import com.chaosserver.merp.tester.MerpTesterBase;
import junit.framework.Test;
import junit.framework.TestSuite;

public class BackgroundPointFactoryTest extends MerpTesterBase {
	public BackgroundPointFactoryTest(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("BackgroundPointFactoryTest");
        suite.addTestSuite(BackgroundPointFactoryTest.class);

        return suite;
    }

	public void testGetBackgroundPointsEA() {
		MerpCharacter character = CharacterFactory.createRandomCharacter();
		Race race = character.getRace();
		int result = race.getBackgroundOptions();

		if(result != BackgroundPointFactory.getBackgroundPointsEA(character).getCurrentPoints()) {
			fail("Factory did not return right number of background points");
		}

		if(result != BackgroundPointFactory.getBackgroundPointsEA(character).getCurrentPoints()) {
			fail("Factory did not return right number of background points");
		}


	}

}