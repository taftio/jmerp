package com.chaosserver.merp.gui.swing.magic;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.rules.magic.SpellList;
import com.chaosserver.merp.rules.magic.SpellListIterator;
import com.chaosserver.merp.rules.magic.SpellLists;
import com.chaosserver.logging.CategoryCache;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 * This panel is used to select a new spell list for a character.
 * <p>
 * It provides a drop down list of all spells lists in the system
 * that are valid for the character to add to it's repotoire.
 * When the user selects the spell lists and hits the button,
 * a property change is fired.
 * </p>
 */
public abstract class BaseSpellListSelectorPanel extends JPanel implements ActionListener, ItemListener {
    /** Holds the character being manipulated */
    protected MerpCharacter character;

    /** The combo box of spelllists */
    protected JComboBox spellListBox;

    /** Action fired when button pressed */
    public static String A_BUTTON = "com.chaosserver.merp.gui.swing.magic.BaseSpellListSelectorPanel.a_button";

    /** The button on the screen */
    JButton button;

    /**
     * Main constructor takes in the character to select for
     *
     * @param character The character to select a new spelllist for
     */
    public BaseSpellListSelectorPanel(MerpCharacter character, String buttonName) {
        setCharacter(character);
        buildSpellListBox();

        if(buttonName != null) {
            button = new JButton(buttonName);
            button.setActionCommand(A_BUTTON);
            button.addActionListener(this);
            add(button);
        }

        add(spellListBox);
        spellListBox.addItemListener(this);


    }

    /**
     * Setter for the character
     *
     * @param character The character being manipulated
     */
    public void setCharacter(MerpCharacter character) {
        this.character = character;
    }

    /**
     * Getter for the character
     *
     * @return the character being manipulated
     */
    public MerpCharacter getCharacter() {
        return this.character;
    }

    /**
     * Builds the jcombobox by adding in all of the spell lists
     * that are valid for a character to add
     */
    public void buildSpellListBox() {
        this.spellListBox = new JComboBox();

        SpellListIterator spellListIterator = SpellLists.instance().validIterator(getCharacter());
        while(spellListIterator.hasNext()) {
            spellListBox.addItem(spellListIterator.next());
        }
    }

    /**
     * Rebuilds the spell lists in the combo box
     */
    public void refreshList() {
        SpellList currentSelection = getSelectedSpellList();
        spellListBox.removeAllItems();
        SpellListIterator spellListIterator = SpellLists.instance().validIterator(getCharacter());
        while(spellListIterator.hasNext()) {
            spellListBox.addItem(spellListIterator.next());
        }

        setSelectedSpellList(currentSelection);
    }

    public abstract void handleButtonPress();
    public abstract void handleSpellListSelected();

    public void actionPerformed(ActionEvent evt) {
        if(A_BUTTON.equals(evt.getActionCommand())) {
            handleButtonPress();
        }
    }

    public void itemStateChanged(ItemEvent evt) {
        if(ItemEvent.SELECTED == evt.getStateChange()) {
            CategoryCache.getInstance(this).debug("Selected : " + evt.getItem());
            handleSpellListSelected();
        }
    }

    public SpellList getSelectedSpellList() {
        return (SpellList) spellListBox.getSelectedItem();
    }

    public void setSelectedSpellList(SpellList spellList) {
        spellListBox.setSelectedItem(spellList);
    }
}