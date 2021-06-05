package com.chaosserver.merp.gui.swing.wizcards;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.character.language.BaseCharLanguageList;
import com.chaosserver.merp.character.language.CharLanguageFactory;
import com.chaosserver.merp.gui.swing.language.BaseLanguageListPanel;
import com.chaosserver.merp.rules.language.BaseLanguageList;

/**
 */
public class BaseLanguageCard extends AbstractCharGenWizPanel {
	protected BaseLanguageListPanel baseLanguageListPanel;

	public void buildCard(MerpCharacter mChar) {
		mChar.setCharLanguageList(CharLanguageFactory.createBaseCharLanguageList(mChar.getRace()));
		baseLanguageListPanel = new BaseLanguageListPanel((BaseCharLanguageList) mChar.getCharLanguageList());
		add(baseLanguageListPanel);
	}

	public String getDescription() {
		return "Listed here are the languages that your race learns "
			+ "at birth.  Each person in your race has different upbringing.  "
			+ "because of this, there's a usually a little bit of flexibility "
			+ "in the languages you being with.  You'll see the amount of "
			+ "flex ranks at the bottom.  Add them into the languages above";
	}
}