package com.chaosserver.merp.gui.swing.wizcards;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.gui.swing.profession.ProfessionSelectorPanel;

public class ProfessionSelectorCard extends AbstractCharGenWizPanel {
    /** This is the panel being represented */
    private ProfessionSelectorPanel m_professionSelectorPanel;

    /**
     * Setter for the profession selector panel
     *
     * @param professionSelectorPanel The new panel value
     */
    private void setProfessionSelectorPanel(ProfessionSelectorPanel professionSelectorPanel) {
        m_professionSelectorPanel = professionSelectorPanel;
    }

    /**
     * Geter for the profession selector panel
     *
     * @return The panel
     */
    public ProfessionSelectorPanel getProfessionSelectorPanel() {
        return m_professionSelectorPanel;
    }


    /**
     * Returns a description of how the user should manipulate this panel
     *
     * @return description
     */
    public String getDescription() {
        return "Select your profession here.  Only allowed professions "
            + "will appear.  The most common reason why a profession will not "
            + "appear would be that it is not allowed by the Race";
    }

    /**
     * this method is called each subsequent time it's built
     */
    protected void rebuildCard(MerpCharacter character) {
        m_professionSelectorPanel.refreshList();
    }


    /**
     * This will build the card to roll the stats.
     * If the character does not have a stat list, it will create a new stat
     * list for the character
     *
     * @param character the character
     */
    public void buildCard(MerpCharacter character) {
        setProfessionSelectorPanel(new ProfessionSelectorPanel(character));
        add(getProfessionSelectorPanel());
    }
}