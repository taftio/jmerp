package com.chaosserver.merp.gui.swing.skill;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.rules.skill.AvailableSkillListIterator;
import com.chaosserver.merp.rules.skill.Skill;
import com.chaosserver.merp.rules.skill.SkillCategoryList;


/**
 * Combo Box that lists all of the skills in the
 * system skill list that the character does not already
 * have in its internal skill list.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class AvailableSkillComboBox extends JPanel {
    /** The character to build the skill list against. */
    protected MerpCharacter character;

    /** The combo box listing all of the skills. */
    protected JComboBox skillList;

    /**
     * Constructor.
     *
     * @param character The character to build the list off of
     */
    public AvailableSkillComboBox(MerpCharacter character) {
        Assertion.isNotNull(character, "Need a character to get skill lists for");

        setCharacter(character);

        skillList = new JComboBox();
        add(skillList);

        updateSkillList();
    }

    /**
     * Updates the skills listed in the combo box.
     */
    protected void updateSkillList() {
        skillList.removeAllItems();
        SkillCategoryList skillCategoryList = SkillCategoryList.instance();
        AvailableSkillListIterator availableSkillListIterator = skillCategoryList.availableSkillListIterator(getCharacter());
        while(availableSkillListIterator.hasNext()) {
            skillList.addItem(availableSkillListIterator.next());
        }
    }

    /**
     * Setter for the character.
     *
     * @param character the character
     * @see #getCharacter
     */
    public void setCharacter(MerpCharacter character) {
        this.character = character;
    }

    /**
     * Getter for the character.
     *
     * @return the character
     * @see #setCharacter
     */
    public MerpCharacter getCharacter() {
        return this.character;
    }

    /**
     * Gets the skill that's currently selected.
     *
     * @return skill current selected
     */
    public Skill getSelectedSkill() {
        return (Skill) skillList.getSelectedItem();
    }

}