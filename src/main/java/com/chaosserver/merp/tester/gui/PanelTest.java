package com.chaosserver.merp.tester.gui;

import com.chaosserver.merp.character.language.*;
import com.chaosserver.merp.rules.language.*;
import com.chaosserver.merp.rules.race.*;
import com.chaosserver.merp.gui.swing.*;
import com.chaosserver.merp.gui.swing.character.*;
import com.chaosserver.merp.gui.swing.language.*;
import com.chaosserver.merp.gui.swing.magic.*;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.character.CharacterFactory;
import com.chaosserver.merp.character.skill.ICharSkill;
import com.chaosserver.merp.data.FileNameGetter;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.apache.log4j.PropertyConfigurator;

public class PanelTest {
    public static void main(String[] args) throws Exception {
		PropertyConfigurator.configure(FileNameGetter.PROPERTIES_LOGGING_CONFIG);

        try {
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) { }

        PanelTest pt = new PanelTest();
    }

    public PanelTest() throws Exception {
        //Create the top-level container and add contents to it.
        JFrame frame = new JFrame("Panel Tester");

        MerpCharacter character = CharacterFactory.createRandomCharacter();
        System.out.println("Realm: " + character.getRealm());
        System.out.println("Profession: " + character.getProfession());
        JPanel panel = new BackgroundPointsCharacterPanel(character);


        frame.getContentPane().add(panel);
        //Finish setting up the frame, and show it.
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setLocation(100,100);
        frame.setSize(800,600);
        //frame.pack();
        frame.setVisible(true);
    }

}