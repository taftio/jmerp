package com.chaosserver.merp.gui.swing.magic;

import com.chaosserver.merp.character.magic.CharSpellListIterator;
import com.chaosserver.merp.character.magic.CharSpellList;
import com.chaosserver.merp.character.magic.CharSpellLists;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class SpellListsPanel extends JPanel {
    protected CharSpellLists charSpellLists;

    public SpellListsPanel(CharSpellLists charSpellLists) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.charSpellLists = charSpellLists;

        CharSpellListIterator charSpellListIterator = this.charSpellLists.iterator();
        while(charSpellListIterator.hasNext()) {
            CharSpellList charSpellList = charSpellListIterator.next();
            //System.out.println("SpellListsPanel - Adding: " + charSpellList);
        	add(new SpellListRowPanel(charSpellList));
        }
    }
}