package com.chaosserver.merp.gui.swing.skill;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JLabel;

import com.chaosserver.merp.character.skill.ICharSkill;

public class SkillSpecialBonusPanel extends JPanel implements PropertyChangeListener {
    private ICharSkill m_charSkill;
    protected JLabel specialBonusLabel;

    /**
     * Public constructors takes in the ICharSkill this will
     * represent
     *
     * @param charSkill The skill to represent
     */
    public SkillSpecialBonusPanel(ICharSkill charSkill) {
        m_charSkill = charSkill;
        m_charSkill.addPropertyChangeListener(this);
        specialBonusLabel = new JLabel(""+charSkill.getSpecialBonus(), JLabel.LEFT);
        add(specialBonusLabel);
    }

    public Dimension getPreferredSize() {
        return new Dimension(50, getHeight());
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if(ICharSkill.P_SPECIAL_BONUS.equals(evt.getPropertyName())) {
            specialBonusLabel.setText(""+m_charSkill.getSpecialBonus());
        }
    }
}