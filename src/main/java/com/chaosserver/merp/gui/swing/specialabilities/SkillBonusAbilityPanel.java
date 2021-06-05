package com.chaosserver.merp.gui.swing.specialabilities;

import com.chaosserver.logging.CategoryCache;
import com.chaosserver.assertion.Assertion;
import com.chaosserver.merp.gui.swing.skill.AllSkillListDropDown;
import com.chaosserver.merp.rules.skill.Skill;
import com.chaosserver.merp.rules.specialabilities.SkillBonusAbility;
import com.chaosserver.merp.rules.specialabilities.SpecialAbility;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;

public class SkillBonusAbilityPanel extends SpecialAbilityPanel implements ItemListener {
    protected JComboBox allSkillListDropDown;

    public SkillBonusAbilityPanel() {
        allSkillListDropDown = new AllSkillListDropDown();
        allSkillListDropDown.addItemListener(this);
        add(allSkillListDropDown);
        add(applyButton);
        updateSpecialAbility();
    }

    public void setSpecialAbility(SpecialAbility specialAbility) {
        Assertion.isTrue(specialAbility instanceof SkillBonusAbility,
            "Should have been a SkillBonusAbility, but was a : " + specialAbility.getClass().getName());

        super.setSpecialAbility(specialAbility);
        updateSpecialAbility();
    }

    public void updateSpecialAbility() {
        CategoryCache.getInstance(this).debug("Updating");

        SkillBonusAbility skillBonusAbility = (SkillBonusAbility) getSpecialAbility();
        if(skillBonusAbility != null) {
            Skill skill = (Skill) allSkillListDropDown.getSelectedItem();
            CategoryCache.getInstance(this).debug("Setting the skill: " + skill);
            skillBonusAbility.setSkill(skill);
        }

        setEnabled();
    }

    public void itemStateChanged(ItemEvent ev) {
        updateSpecialAbility();
    }
}