package com.chaosserver.merp.gui.swing.skill;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.character.skill.ICharSkill;
import com.chaosserver.merp.character.background.BackgroundPointFactory;

public class SkillRowBackgroundPointPanel extends JPanel implements PropertyChangeListener {
    protected ICharSkill charSkill;
    protected MerpCharacter character;
    protected SkillNameButtonPanel nameButtonPanel;

    public SkillRowBackgroundPointPanel(ICharSkill charSkill) {
        this.charSkill = charSkill;
        this.character = this.charSkill.getCharSkillList().getCharacter();
        this.charSkill.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        nameButtonPanel = new SkillNameButtonPanel(this.charSkill);
        add(nameButtonPanel);
        add(new SkillRankPanel(this.charSkill));
        add(new SkillRankBonusPanel(this.charSkill));
        add(new SkillStatBonusPanel(this.charSkill));
        add(new SkillProfessionBonusPanel(this.charSkill));
        add(new SkillItemBonusPanel(this.charSkill));
        add(new SkillSpecialBonusPanel(this.charSkill));
        add(new SkillTotalBonusPanel(this.charSkill));

        updateEnabled();
    }

    public void updateEnabled() {
        setEnabled(BackgroundPointFactory.canAddSkillRank(this.character, this.charSkill));
    }

    public void setEnabled(boolean enabled) {
        nameButtonPanel.setEnabled(enabled);
    }

    public void addActionListener(ActionListener l) {
        nameButtonPanel.addActionListener(l);
    }

    public void removeActionListener(ActionListener l) {
        nameButtonPanel.removeActionListener(l);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if(ICharSkill.P_RANKS.equals(evt.getPropertyName())) {
            updateEnabled();
        }
    }

    public void destroy() {
        this.charSkill.removePropertyChangeListener(this);
    }
}