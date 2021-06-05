package com.chaosserver.merp.gui.swing.skill;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.chaosserver.merp.character.skill.CharSkillList;
import com.chaosserver.merp.character.skill.CharSkillIterator;
import com.chaosserver.merp.character.skill.ICharSkill;

public class SkillListPanel extends JPanel {
    protected CharSkillList m_charSkillList;

    public SkillListPanel(CharSkillList charSkillList) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        m_charSkillList = charSkillList;

        CharSkillIterator charSkillIter = m_charSkillList.iterator();
        while(charSkillIter.hasNext()) {
            ICharSkill currSkill = charSkillIter.next();
            // System.out.println("SkillListPanel - Adding: " + currSkill.getSkill().getName());
            add(new SkillRowPanel(currSkill));
        }
    }
}