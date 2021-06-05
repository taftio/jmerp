package com.chaosserver.merp.gui.swing.wizcards;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.gui.swing.character.FullCharacterPanel;

public class FullCharacterDisplayCard extends AbstractCharGenWizPanel {
    /**
     * Returns a description of how the user should manipulate this panel
     *
     * @return description
     */
    public String getDescription() {
        return "This displays you current skill rankings";
    }

    /**
     * this method is called each subsequent time it's built
     */
    protected void rebuildCard(MerpCharacter character) {
        // m_SkillListPanel.refreshList();
    }

    /**
     * This will build the card to roll the stats.
     * If the character does not have a stat list, it will create a new stat
     * list for the character
     *
     * @param character the character
     */
    public void buildCard(MerpCharacter character) {
		add(new FullCharacterPanel(character));
        // setSkillListPanel(new SkillListPanel(character.getCharSkillList()));
        // add(getSkillListPanel());
    }
}