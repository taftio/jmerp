package com.chaosserver.merp.gui.swing.specialabilities;

import com.chaosserver.logging.CategoryCache;
import com.chaosserver.gui.FrameHelper;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.character.background.BackgroundPointEA;
import com.chaosserver.merp.character.background.BackgroundPointFactory;
import com.chaosserver.merp.rules.specialabilities.SpecialAbility;
import com.chaosserver.merp.rules.specialabilities.SpecialAbilities;
import com.chaosserver.merp.rules.specialabilities.SpecialAbilityIterator;
import java.awt.event.ActionEvent;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SpecialAbilityBackgroundPanel extends JPanel implements PropertyChangeListener, ActionListener {
    public static final String A_PROCESS_SPECIAL = "Process Special";
    public static final String A_ADD_SPECIAL = "Add special";

    protected MerpCharacter character;
    protected JButton rollSpecial;
    protected BackgroundPointEA backgroundPointEA;
    protected JButton processSpecial;
    protected JDialog specialAbilityDialog;
    protected SpecialAbilityPanel specialAbilityPanel;

    public SpecialAbilityBackgroundPanel(MerpCharacter character) {
        character.addPropertyChangeListener(this);
        BackgroundPointEA backgroundPointEA = BackgroundPointFactory.getBackgroundPointsEA(character);
        backgroundPointEA.addPropertyChangeListener(this);
        setBackgroundPointEA(backgroundPointEA);
        rollSpecial = new JButton("Roll Special Ability");
        rollSpecial.setActionCommand(A_ADD_SPECIAL);
        rollSpecial.addActionListener(this);

        processSpecial = new JButton();
        processSpecial.setActionCommand(A_PROCESS_SPECIAL);
        processSpecial.addActionListener(this);

        setCharacter(character);
        buildPanel();
        setEnabled();
    }

    public void buildPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /**
        SpecialAbilityIterator specialAbilityIterator =
            SpecialAbilities.instance().iterator(getCharacter().getRace());

        while(specialAbilityIterator.hasNext()) {
            add(new JLabel(specialAbilityIterator.next().toString()));
        }
        */

        Collection specialAbilities = getCharacter().getSpecialAbilities();
        Iterator specialAbilitiesIterator = specialAbilities.iterator();
        while(specialAbilitiesIterator.hasNext()) {
            SpecialAbility specialAbility = (SpecialAbility) specialAbilitiesIterator.next();
            add(new JLabel(specialAbility.toString()));
        }

        if(getBackgroundPointEA().hasSpecialAbility()) {
            processSpecial.setText(getBackgroundPointEA().getSpecialAbility().getName());
            add(processSpecial);
        }

        add(rollSpecial);
    }

    public void rebuildPanel() {
        removeAll();
        buildPanel();
        invalidate();
        validate();
        repaint();

    }

    public void setEnabled() {
        rollSpecial.setEnabled(BackgroundPointFactory.canAddSpecialAbility(getCharacter()));
    }

    public void setCharacter(MerpCharacter character) {
        this.character = character;
    }

    public MerpCharacter getCharacter() {
        return this.character;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if(BackgroundPointEA.NO_POINTS_LEFT.equals(evt.getPropertyName())) {
            setEnabled();
        }
    }

    public void actionPerformed(ActionEvent evt) {
        if(A_ADD_SPECIAL.equals(evt.getActionCommand())) {
            BackgroundPointFactory.addSpecialAbility(getCharacter());
            setEnabled();
            rebuildPanel();
        }
        else if(A_PROCESS_SPECIAL.equals(evt.getActionCommand())) {
            if(specialAbilityDialog == null) {
                makeSpecialAbilityPanel();
            }

            specialAbilityDialog.show();
            CategoryCache.getInstance(this).debug("Back from the panel");
            setEnabled();
            rebuildPanel();
        }
        else if(SpecialAbilityPanel.A_APPLY.equals(evt.getActionCommand())) {
            if(specialAbilityPanel.isValid()) {
                CategoryCache.getInstance(this).debug("The panel is valid, lets apply that guy!");
                BackgroundPointFactory.applySpecialAbility(getCharacter());
                specialAbilityDialog.dispose();
                specialAbilityPanel.removeActionListener(this);
                specialAbilityDialog = null;
                specialAbilityPanel = null;
                setEnabled();
                rebuildPanel();
            }
        }
        else if(BackgroundPointEA.NO_POINTS_LEFT.equals(evt.getActionCommand())) {
                setEnabled();
                // rebuildPanel();
        }
        else if(MerpCharacter.P_SPECIAL_ABILITIES.equals(evt.getActionCommand())) {
                setEnabled();
                rebuildPanel();
        }

    }

    protected void makeSpecialAbilityPanel() {
        CategoryCache.getInstance(this).debug("Making a new special ability panel");

        specialAbilityPanel =
            SpecialAbilityPanelFactory.instance().makePanel(getCharacter(), getBackgroundPointEA().getSpecialAbility());

        Frame parentFrame = FrameHelper.getContainingFrame(this);
        Point parentLocation = parentFrame.getLocation();
        specialAbilityDialog = new JDialog(parentFrame, specialAbilityPanel.toString(), true);
        specialAbilityPanel.addActionListener(this);
        specialAbilityDialog.setLocation(parentLocation);
        specialAbilityDialog.getContentPane().add(specialAbilityPanel);
        specialAbilityDialog.pack();
    }

    public BackgroundPointEA getBackgroundPointEA() {
        return this.backgroundPointEA;
    }

    public void setBackgroundPointEA(BackgroundPointEA backgroundPointEA) {
        this.backgroundPointEA = backgroundPointEA;
    }
}