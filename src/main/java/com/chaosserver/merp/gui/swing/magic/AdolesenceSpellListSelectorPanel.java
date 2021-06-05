package com.chaosserver.merp.gui.swing.magic;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.character.magic.CharSpellList;
import com.chaosserver.merp.character.magic.CharSpellLists;
import com.chaosserver.merp.character.magic.CharSpellListFactory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class AdolesenceSpellListSelectorPanel extends BaseSpellListSelectorPanel implements PropertyChangeListener {
	protected CharSpellList charSpellList;

	public AdolesenceSpellListSelectorPanel(MerpCharacter character, String buttonName) {
		super(character, null);

		this.charSpellList = CharSpellListFactory.addCharSpellList(character, getSelectedSpellList(), 100);
		this.charSpellList.addPropertyChangeListener(this);
	}

	public void handleButtonPress() {
	}

	public void handleSpellListSelected() {
		charSpellList.setSpellList(getSelectedSpellList());
	}

    public void propertyChange(PropertyChangeEvent evt) {
		if(CharSpellList.P_SPELL_LIST.equals(evt.getPropertyName())) {
			evt.getNewValue();
		}
	}

}