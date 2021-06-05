package com.chaosserver.merp.gui.swing.skill;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JLabel;

import com.chaosserver.merp.character.skill.ICharSkill;

public class SkillItemBonusPanel extends JPanel {
    private ICharSkill m_charSkill;

    /**
     * Public constructors takes in the ICharSkill this will
     * represent
     *
     * @param charSkill The skill to represent
     */
    public SkillItemBonusPanel(ICharSkill charSkill) {
        m_charSkill = charSkill;
        add(new JLabel(""+charSkill.getItemBonus(), JLabel.LEFT));
    }

    public Dimension getPreferredSize() {
        return new Dimension(50, getHeight());
    }
}