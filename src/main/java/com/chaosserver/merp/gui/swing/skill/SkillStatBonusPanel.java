package com.chaosserver.merp.gui.swing.skill;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.chaosserver.merp.character.skill.ICharSkill;

public class SkillStatBonusPanel extends JPanel implements PropertyChangeListener {
    private ICharSkill charSkill;

    protected JLabel statBonusLabel;

    /**
     * Public constructors takes in the ICharSkill this will
     * represent
     *
     * @param charSkill The skill to represent
     */
    public SkillStatBonusPanel(ICharSkill charSkill) {
        this.charSkill = charSkill;
        this.charSkill.addPropertyChangeListener(this);

        String statAbbr = "XX";
        String statBonus = "xx";
        if(this.charSkill.getSkill().getStat() != null) {
            statAbbr = this.charSkill.getSkill().getStat().getAbbr();
            statBonus = "" + this.charSkill.getStatBonus();
        }
        setLayout(new GridLayout(1,2));
        add(new JLabel(statAbbr, JLabel.LEFT));
        statBonusLabel = new JLabel(statBonus, JLabel.LEFT);
        add(statBonusLabel);
    }

    public Dimension getPreferredSize() {
        return new Dimension(50, getHeight());
    }

    public void refresh() {
        String statBonus = "xx";
        if(charSkill.getSkill().getStat() != null) {
            statBonus = "" + charSkill.getStatBonus();
        }

        statBonusLabel.setText(statBonus);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if(ICharSkill.P_STAT_BONUS.equals(evt.getPropertyName())) {
            refresh();
        }
    }

}