package com.chaosserver.merp.gui.swing.skill;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.character.background.BackgroundPointFactory;
import com.chaosserver.merp.character.skill.ICharSkill;
import com.chaosserver.merp.rules.skill.Skill;
import com.chaosserver.merp.character.skill.CharSkillListFactory;


/**
 * A row used to add a new a skill to a character.
 * <p>
 * This box will list out all of the skills a character may add and give
 * a button that a user can click to add it.  Not ranks are added to the
 * skill, it only creates a CharSkill from it and adds it to the character.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class NewSkillRowBackgroundPointPanel extends JPanel implements ActionListener {

    /** The character to add a skill to. */
    protected MerpCharacter character;

    /** The button for adding. */
    protected JButton addButton;

    /** The combo that lists all available skills. */
    protected AvailableSkillComboBox availableSkillComboBox;

    /**
     * Constructor.
     *
     * @param character the character to add a skill to
     */
    public NewSkillRowBackgroundPointPanel(MerpCharacter character) {
        setCharacter(character);

        addButton = new JButton("Add Skill");
        addButton.addActionListener(this);
        add(addButton);

        availableSkillComboBox = new AvailableSkillComboBox(character);
        add(availableSkillComboBox);
    }

    /**
     * Setter for the character.
     *
     * @param character The character this adds skill to
     * @see #getCharacter
     */
    public void setCharacter(MerpCharacter character) {
        this.character = character;
    }

    /**
     * Getter for the character.
     *
     * @return The character this adds skill to
     * @see #setCharacter
     */
    public MerpCharacter getCharacter() {
        return this.character;
    }


    // Inherits javadoc from interace
    public void actionPerformed(ActionEvent evt) {
        Skill newSkill = availableSkillComboBox.getSelectedSkill();
        ICharSkill newCharSkill = CharSkillListFactory.createCharSkill(newSkill);

        getCharacter().getCharSkillList().add(newCharSkill);
    }

    /**
     * Enabled or disables the component.
     *
     * @param enabled Sets the enabled status of the item.
     */
    public void setEnabled(boolean enabled) {
        addButton.setEnabled(enabled);
        availableSkillComboBox.setEnabled(enabled);
    }


    public void destroy() {
        addButton.removeActionListener(this);
    }
}