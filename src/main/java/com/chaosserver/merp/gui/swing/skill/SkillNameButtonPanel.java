package com.chaosserver.merp.gui.swing.skill;

import java.awt.event.ActionListener;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.chaosserver.merp.character.skill.ICharSkill;

public class SkillNameButtonPanel extends JPanel {
    protected ICharSkill m_charSkill;

    protected JButton skillButton;

    /**
     * Public constructors takes in the ICharSkill this will
     * represent
     *
     * @param charSkill The skill to represent
     */
    public SkillNameButtonPanel(ICharSkill charSkill) {
        m_charSkill = charSkill;
        skillButton = new JButton(charSkill.getSkill().getName());
        add(skillButton);
    }

	public void setEnabled(boolean enabled) {
		skillButton.setEnabled(enabled);
	}

    public Dimension getPreferredSize() {
        return new Dimension(145, 32);
    }

    public void addActionListener(ActionListener l) {
		skillButton.addActionListener(l);
	}

	public void removeActionListener(ActionListener l) {
		skillButton.removeActionListener(l);
	}
}