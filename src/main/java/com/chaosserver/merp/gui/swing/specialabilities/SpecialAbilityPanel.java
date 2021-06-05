package com.chaosserver.merp.gui.swing.specialabilities;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.rules.specialabilities.SpecialAbility;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public abstract class SpecialAbilityPanel extends JPanel implements ActionListener {
    public static final String A_APPLY = "apply button";

    protected SpecialAbility specialAbility;
    protected MerpCharacter character;
    protected JButton applyButton;

    public SpecialAbilityPanel() {
        applyButton = new JButton("Apply");
        applyButton.setActionCommand(A_APPLY);
        applyButton.addActionListener(this);
        setEnabled();
    }

    public void setEnabled() {
        applyButton.setEnabled(isValid());
    }


    public void setCharacter(MerpCharacter character) {
        this.character = character;
    }

    public MerpCharacter getCharacter() {
        return this.character;
    }

    public void setSpecialAbility(SpecialAbility specialAbility) {
        this.specialAbility = specialAbility;
    }

    public SpecialAbility getSpecialAbility() {
        return this.specialAbility;
    }

    public String toString() {
        return "Select skill to apply bonus to";
    }

    public boolean isValid() {
        boolean result;
        if(specialAbility != null) {
          result = specialAbility.isValid();
        }
        else {
            result = false;
        }
        return result;
    }

    public void addActionListener(ActionListener al) {
        applyButton.addActionListener(al);
    }

    public void removeActionListener(ActionListener al) {
        applyButton.removeActionListener(al);
    }

    public void actionPerformed(ActionEvent ev) {
        setEnabled();
    }
}