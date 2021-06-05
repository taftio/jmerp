package com.chaosserver.merp.gui.swing.skill;

import com.chaosserver.merp.character.MerpCharacter;
import javax.swing.JComboBox;
import com.chaosserver.merp.rules.skill.SkillListIterator;


public class AllSkillListDropDown extends JComboBox {
    public AllSkillListDropDown() {
        buildDropDown();
    }

    protected void buildDropDown() {
        SkillListIterator skillListIterator = new SkillListIterator();
        while(skillListIterator.hasNext()) {
            addItem(skillListIterator.next());
        }
    }
}