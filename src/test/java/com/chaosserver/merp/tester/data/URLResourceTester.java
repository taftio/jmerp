package com.chaosserver.merp.tester.data;

import java.io.InputStream;
import java.net.URL;

import com.chaosserver.merp.data.FileNameGetter;
import com.chaosserver.data.JavaBeanLoader;
import com.chaosserver.data.JavaBeanLoaderExceptionX;
import com.chaosserver.merp.rules.race.RaceList;

public class URLResourceTester {
	public static void main(String args[]) {
		System.out.println("Okay, let's load a resource!");

		// URL resource = FileNameGetter.getResourceURL("merp/data/game/stat/StatBonusLookup.xml");
		URL resource = FileNameGetter.getResourceURL("merp/data/game/race/RaceList.xml");

		InputStream inputStream = FileNameGetter.getInputStream("merp/data/game/race/RaceList.xml");

		// URL resource = FileNameGetter.getResourceURL(FileNameGetter.IMAGE_MERP_LOGO);

		try {
			// RaceList raceList = (RaceList) JavaBeanLoader.getBean(inputStream);
			RaceList raceList = (RaceList) JavaBeanLoader.getBean(resource);
			// RaceList raceList = (RaceList) JavaBeanLoader.getBean(FileNameGetter.XML_RACELIST);
		}
		catch (JavaBeanLoaderExceptionX e) {
			System.out.println(e);
			e.printStackTrace();
		}


		// RaceList raceList = RaceList.instance();
		/*
		try {
			Race race = RaceFinder.findByName("Dwarf");
			Map map = race.getStatBonusMap();
			System.out.println("Size: " + map.size());
		} catch (Throwable e) {
			System.out.println(e.toString());
		}
		*/

	}
}