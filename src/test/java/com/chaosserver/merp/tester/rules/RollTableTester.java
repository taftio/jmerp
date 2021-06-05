package com.chaosserver.merp.tester.rules;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.chaosserver.merp.data.FileNameGetter;
import com.chaosserver.data.RollTable;
import com.chaosserver.merp.rules.race.Race;
import com.chaosserver.merp.tester.MerpTesterBase;

public class RollTableTester extends MerpTesterBase {
    public RollTableTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(RollTableTester.class);
        return suite;
    }

    public void testRaceSelectorTable() {
        System.out.println("RollTableTester.testRaceSelectorTable()");
        RollTable raceRoller = new RollTable(new File(FileNameGetter.XML_RACE_SELECTOR_TABLE));

        for(int i=0;i<1000;i++) {
            Race result = (Race) raceRoller.getNewResult();
            if(result != null) {
                // System.out.println("Got: " + result.getName());
            } else {
                // System.out.println("Null Result!");
            }
        }
    }


}