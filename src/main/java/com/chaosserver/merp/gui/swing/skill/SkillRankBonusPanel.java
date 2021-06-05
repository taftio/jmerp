package com.chaosserver.merp.gui.swing.skill;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


import com.chaosserver.merp.character.skill.ICharSkill;

public class SkillRankBonusPanel extends JPanel implements PropertyChangeListener {
    protected ICharSkill charSkill;
    protected JLabel rankBonusLabel;

    /**
     * Public constructors takes in the ICharSkill this will
     * represent
     *
     * @param charSkill The skill to represent
     */
    public SkillRankBonusPanel(ICharSkill charSkill) {
        this.charSkill = charSkill;
        this.charSkill.addPropertyChangeListener(this);
        rankBonusLabel = new JLabel(""+this.charSkill.getRankBonus(), JLabel.LEFT);
        add(rankBonusLabel);
    }

    public Dimension getPreferredSize() {
        return new Dimension(50, getHeight());
    }

    public void update() {
		rankBonusLabel.setText(""+this.charSkill.getRankBonus());
	}

    public void propertyChange(PropertyChangeEvent evt) {
		if(ICharSkill.P_RANK_BONUS.equals(evt.getPropertyName())) {
			update();
		}
	}

}