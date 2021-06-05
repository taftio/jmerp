package com.chaosserver.merp.gui.swing.magic;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

// import com.chaosserver.merp.character.skill.ICharSkill;
import com.chaosserver.merp.character.magic.CharSpellList;

public class SpellListRowPanel extends JPanel {
    CharSpellList charSpellList;

    public SpellListRowPanel(CharSpellList charSpellList) {
        this.charSpellList = charSpellList;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		add(new SpellListNamePanel(charSpellList));
		add(new SpellListKnownPanel(charSpellList));
    }
}