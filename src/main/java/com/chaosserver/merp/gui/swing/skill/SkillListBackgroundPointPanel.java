package com.chaosserver.merp.gui.swing.skill;

import com.chaosserver.logging.CategoryCache;
import com.chaosserver.assertion.Assertion;
import com.chaosserver.exception.NotFoundX;
import com.chaosserver.merp.character.background.BackgroundPointEA;
import com.chaosserver.merp.character.background.BackgroundPointFactory;
import com.chaosserver.merp.character.skill.CharSkillFinder;
import com.chaosserver.merp.character.skill.CharSkillIterator;
import com.chaosserver.merp.character.skill.CharSkillList;
import com.chaosserver.merp.character.skill.ICharSkill;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class SkillListBackgroundPointPanel extends JPanel implements ActionListener, PropertyChangeListener {
    protected CharSkillList charSkillList;
    protected BackgroundPointEA backgroundPointEA;
    protected Collection skillRowBackgroundPointPanels;
    protected NewSkillRowBackgroundPointPanel newSkillRowBackgroundPointPanel;

    public SkillListBackgroundPointPanel(CharSkillList charSkillList, BackgroundPointEA backgroundPointEA) {
        Assertion.isNotNull(charSkillList, "Need a skill list to view");
        Assertion.isNotNull(backgroundPointEA, "Need a background point EA to hold state.");
        charSkillList.addPropertyChangeListener(this);
        backgroundPointEA.addPropertyChangeListener(this);

        this.backgroundPointEA = backgroundPointEA;
        this.charSkillList = charSkillList;
        build();
    }

    public void build() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        CharSkillIterator charSkillIter = this.charSkillList.iterator();
        skillRowBackgroundPointPanels = new ArrayList();

        // Iterator through all the skills and add them to the panel.
        while(charSkillIter.hasNext()) {
            ICharSkill currSkill = charSkillIter.next();
            SkillRowBackgroundPointPanel skillRowBackgroundPointPanel = new SkillRowBackgroundPointPanel(currSkill);
            skillRowBackgroundPointPanel.addActionListener(this);

            add(skillRowBackgroundPointPanel);
            skillRowBackgroundPointPanels.add(skillRowBackgroundPointPanel);
        }

        // Add the ability to create a new skill
        newSkillRowBackgroundPointPanel = new NewSkillRowBackgroundPointPanel(this.charSkillList.getCharacter());
        add(newSkillRowBackgroundPointPanel);
        invalidate();
        validate();
        repaint();
      }

    public void rebuild() {
        // Get rid of all the rows.
        Iterator skillRowIterator = skillRowBackgroundPointPanels.iterator();
        while(skillRowIterator.hasNext()) {
            SkillRowBackgroundPointPanel skillRowBackgroundPointPanel =
                (SkillRowBackgroundPointPanel) skillRowIterator.next();

            skillRowBackgroundPointPanel.destroy();
            remove(skillRowBackgroundPointPanel);
        }

        // Finally remove the last row
        newSkillRowBackgroundPointPanel.destroy();
        remove(newSkillRowBackgroundPointPanel);

        build();
    }

    // Inherits javadoc from interface
    public void actionPerformed(ActionEvent evt) {
        String actionCommand = evt.getActionCommand();
        CategoryCache.getInstance(this).debug("Increasing skill of: " + actionCommand);

        // Find the correct skill
        try {
            ICharSkill charSkill = CharSkillFinder.findByName(actionCommand, this.charSkillList);
            BackgroundPointFactory.addSkillRank(this.charSkillList.getCharacter(), charSkill);
        }
        catch (NotFoundX e) {
            // Not found in the list, that means that this is a new skill a character is trying
            // to add.
            CategoryCache.getInstance(this).error(e);
        }
    }

    // Inherits javadoc from interface
    public void propertyChange(PropertyChangeEvent evt) {
        if(BackgroundPointEA.NO_POINTS_LEFT.equals(evt.getPropertyName())) {
            Iterator iterator = skillRowBackgroundPointPanels.iterator();
            while(iterator.hasNext()) {
                SkillRowBackgroundPointPanel skillRowBackgroundPointPanel =
                    (SkillRowBackgroundPointPanel) iterator.next();

                skillRowBackgroundPointPanel.setEnabled(false);
            }

            newSkillRowBackgroundPointPanel.setEnabled(false);
        }
        else if(CharSkillList.P_CHAR_SKILL_LIST.equals(evt.getPropertyName())) {
            CategoryCache.getInstance(this).debug("Char Skill List changed... rebuilding");
            rebuild();
        }
    }

    public void destroy() {
        charSkillList.removePropertyChangeListener(this);
        backgroundPointEA.removePropertyChangeListener(this);
    }

}