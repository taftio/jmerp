package com.chaosserver.merp.gui.swing.race;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.rules.race.Race;
import com.chaosserver.merp.rules.restriction.RestrictionList;
import com.chaosserver.merp.data.FileNameGetter;
import com.chaosserver.data.RollTable;

/**
 * The random selector panel extends the functionality of that basic
 * panel by adding a button that will use the race roller table
 * to random select a race according to the Merp Books distribution
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class RandomRaceSelectorPanel extends Box implements ActionListener {
    private static String A_RACE_PICK = "RANDOM_RACE_SELECTOR_PANEL.RACE_PICK";

    private RaceSelectorPanel m_raceSelectorPanel;
    private JButton m_selectButton;
    private RollTable m_raceRoller;
    private MerpCharacter m_mChar;

    /**
     * The main constructor takes in a merp character.  This panel
     * simply adds a random roll button to the RaceSelectorPanel
     * that it encapsulates.
     *
     * @param mChar The merp character to represent
     */
    public RandomRaceSelectorPanel(MerpCharacter mChar) {
        super(BoxLayout.Y_AXIS);
        m_raceSelectorPanel = new RaceSelectorPanel(mChar);
        m_selectButton = new JButton("Random Race");
        m_selectButton.setActionCommand(A_RACE_PICK);
        m_selectButton.setToolTipText("Click this button to pick a race randomly");
        m_selectButton.addActionListener(this);

        add(m_raceSelectorPanel);

        add(m_selectButton);

        m_mChar = mChar;
    }

    public void actionPerformed(ActionEvent evt) {
        if(A_RACE_PICK.equals(evt.getActionCommand())) {
            if(m_raceRoller == null) {
                m_raceRoller = new RollTable(new File(FileNameGetter.XML_RACE_SELECTOR_TABLE));
            }

            Race newRace = null;
            RestrictionList restrictionList = null;

            do {
                newRace = (Race) m_raceRoller.getNewResult();
                restrictionList = newRace.getRestrictionList();
            } while( (restrictionList != null) && !restrictionList.isValid(m_mChar) );

            try {
                m_mChar.setRace(newRace);
            } catch (PropertyVetoException e) {
                System.out.println("Wow, property veto exception!");
                e.printStackTrace();
            }
        }
    }

    public void refreshList() {
        m_raceSelectorPanel.resfreshList();
    }

}