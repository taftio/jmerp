package com.chaosserver.merp.gui.swing.magic;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JLabel;

import com.chaosserver.merp.character.magic.CharSpellList;

public class SpellListNamePanel extends JPanel {
    protected CharSpellList charSpellList;

    public SpellListNamePanel(CharSpellList charSpellList) {
        this.charSpellList = charSpellList;

        add(new JLabel(charSpellList.getSpellList().getName(), JLabel.LEFT));
    }

	/*
    public Dimension getPreferredSize() {
        return new Dimension(120, getHeight());
    }
    */
}