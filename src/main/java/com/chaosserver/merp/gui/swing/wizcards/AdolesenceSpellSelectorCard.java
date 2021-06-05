package com.chaosserver.merp.gui.swing.wizcards;

import com.chaosserver.merp.character.CharacterFactory;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.character.magic.CharSpellListFactory;
import com.chaosserver.merp.gui.swing.magic.AdolesenceSpellListSelectorPanel;

public class AdolesenceSpellSelectorCard extends AbstractCharGenWizPanel {
    /** This is the panel being represented */
    private AdolesenceSpellListSelectorPanel adolesenceSpellListSelectorPanel;

    /**
     * Setter for the realm selector panel
     *
     * @param realmSelectorPanel The new panel value
     */
    private void setAdolesenceSpellListSelectorPanel(AdolesenceSpellListSelectorPanel adolesenceSpellListSelectorPanel) {
        this.adolesenceSpellListSelectorPanel = adolesenceSpellListSelectorPanel;
    }

    /**
     * Geter for the realm selector panel
     *
     * @return The panel
     */
    public AdolesenceSpellListSelectorPanel getAdolesenceSpellListSelectorPanel() {
        return adolesenceSpellListSelectorPanel;
    }


    /**
     * Returns a description of how the user should manipulate this panel
     *
     * @return description
     */
    public String getDescription() {
        return "You're character learn a spell list during adolesence. "
        	+ "You should pick that list here.";
    }

    /**
     * this method is called each subsequent time it's built
     */
    protected void rebuildCard(MerpCharacter character) {
        getAdolesenceSpellListSelectorPanel().refreshList();
    }


    /**
     * This will build the card to roll the stats.
     * If the character does not have a stat list, it will create a new stat
     * list for the character
     *
     * @param character the character
     */
    public void buildCard(MerpCharacter character) {
        setAdolesenceSpellListSelectorPanel(new AdolesenceSpellListSelectorPanel(character, "Add List"));
        add(getAdolesenceSpellListSelectorPanel());
    }

    public boolean isSkippable(MerpCharacter character) {
		return !CharSpellListFactory.hasAdolesenceSpellList(character);
	}
}