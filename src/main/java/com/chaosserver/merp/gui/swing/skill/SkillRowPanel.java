package com.chaosserver.merp.gui.swing.skill;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.chaosserver.merp.character.skill.ICharSkill;

public class SkillRowPanel extends JPanel {
    ICharSkill m_charSkill;

    public SkillRowPanel(ICharSkill charSkill) {
        m_charSkill = charSkill;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(new SkillNamePanel(m_charSkill));
        add(new SkillRankPanel(m_charSkill));
        add(new SkillRankBonusPanel(m_charSkill));
        add(new SkillStatBonusPanel(m_charSkill));
        add(new SkillProfessionBonusPanel(m_charSkill));
        add(new SkillItemBonusPanel(m_charSkill));
        add(new SkillSpecialBonusPanel(m_charSkill));
        add(new SkillTotalBonusPanel(m_charSkill));
    }
}