package com.chaosserver.merp.gui.swing.skill;

import com.chaosserver.logging.CategoryCache;
import com.chaosserver.merp.character.skill.ICharSkill;
import com.chaosserver.merp.data.FileNameGetter;
import com.chaosserver.merp.rules.skill.Skill;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SkillRankPanel extends JPanel implements PropertyChangeListener {
    protected ICharSkill charSkill;

    protected List checkBoxes = new ArrayList();

    /**
     * Public constructors takes in the ICharSkill this will
     * represent
     *
     * @param charSkill The skill to represent
     */
    public SkillRankPanel(ICharSkill charSkill) {
        this.charSkill = charSkill;
        this.charSkill.addPropertyChangeListener(this);
        int maxRanks = this.charSkill.getSkill().getMaxRanks();

        setLayout(new FlowLayout(FlowLayout.LEFT));
        drawRanks: for(int i=0; i < 15; i++) {
            if(maxRanks != Skill.NO_MAX && maxRanks <= i) {
                break drawRanks;
            }
            JCheckBox currCheckBox = new JCheckBox();
            if(this.charSkill.getRanks() > i) {
                currCheckBox.setSelected(true);
            }
            currCheckBox.setEnabled(false);
            currCheckBox.setBorder(BorderFactory.createEmptyBorder());
            add(currCheckBox);
            checkBoxes.add(currCheckBox);
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(275, 23);
        /*
        System.out.println("SkillRankPanel: " + getHeight() + " by " + getWidth());
        System.out.println("Pref: " + super.getPreferredSize());
        return super.getPreferredSize();
        */
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if(ICharSkill.P_RANKS.equals(evt.getPropertyName())) {
            CategoryCache.getInstance(this).debug("Ranks updated on " + charSkill);
            int i = 0;
            Iterator checkBoxIterator = checkBoxes.iterator();
            while(checkBoxIterator.hasNext() && i < charSkill.getRanks()) {
                JCheckBox checkBox = (JCheckBox) checkBoxIterator.next();
                i++;
                checkBox.setSelected(true);
            }
        }
    }

}