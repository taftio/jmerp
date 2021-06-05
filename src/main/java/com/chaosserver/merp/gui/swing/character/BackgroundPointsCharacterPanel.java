package com.chaosserver.merp.gui.swing.character;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.gui.swing.PackPanel;
import com.chaosserver.merp.gui.swing.language.LanguageListBackgroundPanel;
import com.chaosserver.merp.gui.swing.magic.SpellListsPanel;
import com.chaosserver.merp.gui.swing.skill.SkillListBackgroundPointPanel;
import com.chaosserver.merp.gui.swing.stat.StatBonusListBackgroundPanel;
import com.chaosserver.merp.character.background.BackgroundPointEA;
import com.chaosserver.merp.character.background.BackgroundPointFactory;
import com.chaosserver.merp.gui.swing.specialabilities.SpecialAbilityBackgroundPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class BackgroundPointsCharacterPanel extends JPanel {
    public static final String A_HOBBY_SKILL = "hobby skill";

    private MerpCharacter character;

    public BackgroundPointsCharacterPanel(MerpCharacter character) {
        setCharacter(character);

        // Get the background points
        BackgroundPointEA backgroundPointEA = BackgroundPointFactory.getBackgroundPointsEA(character);

        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.add("Hobby Skill Ranks",new JScrollPane(new SkillListBackgroundPointPanel(character.getCharSkillList(), backgroundPointEA)));
        tabbedPane.add("Stat Increases", new PackPanel(new StatBonusListBackgroundPanel(character)));
        tabbedPane.add("Languages", new PackPanel(new LanguageListBackgroundPanel(character, character.getCharLanguageList())));
        tabbedPane.add("Special Abilities", new SpecialAbilityBackgroundPanel(character));
        tabbedPane.add("Special Items", new JLabel("Not Implemented"));
        tabbedPane.add("Money Option", new JLabel("Not Implemented"));
        tabbedPane.add("Spell Lists", new PackPanel(new SpellListsPanel(character.getCharSpellLists())));
        add(tabbedPane);

        tabbedPane.setSelectedIndex(3);
    }

    public void setCharacter(MerpCharacter character) {
        this.character = character;
    }

    public MerpCharacter getCharacter() {
        return this.character;
    }
}
