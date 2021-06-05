package com.chaosserver.merp.gui.swing.magic;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JLabel;

import com.chaosserver.merp.character.magic.CharSpellList;

public class SpellListKnownPanel extends JPanel {
    protected CharSpellList charSpellList;

    public SpellListKnownPanel(CharSpellList charSpellList) {
        this.charSpellList = charSpellList;

        add(new JLabel(charSpellList.getPercentKnown()+"", JLabel.LEFT));
    }

	/*
    public Dimension getPreferredSize() {
        return new Dimension(120, getHeight());
    }
    */
}