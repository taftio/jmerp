package com.chaosserver.merp.gui.swing.skill;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JLabel;

import com.chaosserver.merp.character.skill.ICharSkill;

public class SkillProfessionBonusPanel extends JPanel {
    private ICharSkill m_charSkill;

    /**
     * Public constructors takes in the ICharSkill this will
     * represent
     *
     * @param charSkill The skill to represent
     */
    public SkillProfessionBonusPanel(ICharSkill charSkill) {
        m_charSkill = charSkill;
        add(new JLabel(""+charSkill.getProfessionBonus(), JLabel.LEFT));
    }

    public Dimension getPreferredSize() {
        return new Dimension(50, getHeight());
    }
}