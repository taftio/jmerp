package com.chaosserver.merp.gui.swing.skill;

import com.chaosserver.merp.character.skill.ICharSkill;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SkillTotalBonusPanel extends JPanel implements PropertyChangeListener {
    protected ICharSkill charSkill;

    JLabel bonusLabel;

    /**
     * Public constructors takes in the ICharSkill this will
     * represent
     *
     * @param charSkill The skill to represent
     */
    public SkillTotalBonusPanel(ICharSkill charSkill) {
        this.charSkill = charSkill;
        bonusLabel = new JLabel(""+charSkill.getTotalBonus(), JLabel.LEFT);
        add(bonusLabel);
        this.charSkill.addPropertyChangeListener(this);
    }

    public void update() {
		bonusLabel.setText(""+charSkill.getTotalBonus());
	}

    public Dimension getPreferredSize() {
        return new Dimension(50, getHeight());
    }

    public void propertyChange(PropertyChangeEvent evt) {
		if(ICharSkill.P_TOTAL_BONUS.equals(evt.getPropertyName())) {
			update();
		}
	}

}