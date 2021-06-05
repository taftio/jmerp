package com.chaosserver.merp.gui.swing.wizcards;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.character.stat.standard.StandardCharStatListFactory;
import com.chaosserver.merp.gui.swing.stat.StandardStatRollerPanel;

public class StatRollerPanel extends AbstractCharGenWizPanel {
    /** This is the panel being represented */
    private StandardStatRollerPanel m_statRollerPanel;


    /**
     * Returns a description of how the user should manipulate this panel
     *
     * @return description
     */
    public String getDescription() {
        return "Roll your stats here by clicking the rollbutton.  You can swap "
            + "the values of two stats by clicking on the name of the first, "
            + "and then the name of the second.  Use the sliders to adjust the "
            + "value of the stats.";
    }

    /**
     * This will build the card to roll the stats.
     * If the character does not have a stat list, it will create a new stat
     * list for the character
     *
     * @param character the character
     */
    public void buildCard(MerpCharacter character) {
        if(character.getCharStatList() == null) {
            character.createCharStatList(StandardCharStatListFactory.instance());
        }

        m_statRollerPanel = new StandardStatRollerPanel(character.getCharStatList());
        add(m_statRollerPanel);

        // add(new JLabel("I'm a stat roller card"));
    }
}