package com.chaosserver.merp.gui.swing.character;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.gui.swing.PackPanel;
import com.chaosserver.merp.gui.swing.language.LanguageListPanel;
import com.chaosserver.merp.gui.swing.magic.SpellListsPanel;
import com.chaosserver.merp.gui.swing.skill.SkillListPanel;
import com.chaosserver.merp.gui.swing.stat.StatBonusListPanel;

public class FullCharacterPanel extends JPanel {
	public FullCharacterPanel(MerpCharacter character) {
		setLayout(new BorderLayout());
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.add("Stats", new PackPanel(new StatBonusListPanel(character.getCharStatList())));
		tabbedPane.add("Skills", new PackPanel(new SkillListPanel(character.getCharSkillList())));
		tabbedPane.add("Languages", new PackPanel(new LanguageListPanel(character.getCharLanguageList())));
		tabbedPane.add("Spell Lists", new PackPanel(new SpellListsPanel(character.getCharSpellLists())));
		add(tabbedPane);
	}
}
