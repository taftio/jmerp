package com.chaosserver.merp.gui.swing.wizcards;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.gui.swing.race.RandomRaceSelectorPanel;

public class RaceSelectorCard extends AbstractCharGenWizPanel {
    /** This is the panel being represented */
    private RandomRaceSelectorPanel m_raceSelectorPanel;

    /**
     * Setter for the race selector panel
     *
     * @param raceSelectorPanel The new panel value
     */
    private void setRaceSelectorPanel(RandomRaceSelectorPanel raceSelectorPanel) {
        m_raceSelectorPanel = raceSelectorPanel;
    }

    /**
     * Geter for the race selector panel
     *
     * @return The panel
     */
    public RandomRaceSelectorPanel getRaceSelectorPanel() {
        return m_raceSelectorPanel;
    }


    /**
     * Returns a description of how the user should manipulate this panel
     *
     * @return description
     */
    public String getDescription() {
        return "Select your race here.  Only allowed races will appear.  "
            + "There are many reasons why a race may not be allowed.  Your "
            + "highest stat in a certain value, or you profession may be "
            + "incorrect.";
    }

    /**
     * this method is called each subsequent time it's built
     */
    protected void rebuildCard(MerpCharacter character) {
        m_raceSelectorPanel.refreshList();
    }

    /**
     * This will build the card to roll the stats.
     * If the character does not have a stat list, it will create a new stat
     * list for the character
     *
     * @param character the character
     */
    public void buildCard(MerpCharacter character) {
        setRaceSelectorPanel(new RandomRaceSelectorPanel(character));
        add(getRaceSelectorPanel());
        //if(character.getCharStatList() == null) {
        //    character.createCharStatList(StandardCharStatListFactory.instance());
        //}

        //m_statRollerPanel = new StandardStatRollerPanel(character.getCharStatList());
        //add(new JLabel("Race Selector"));

    }
}