package com.chaosserver.merp.gui.swing.language;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.character.background.BackgroundPointFactory;
import com.chaosserver.merp.character.background.BackgroundPointEA;
import com.chaosserver.merp.character.language.CharLanguage;
import com.chaosserver.merp.character.language.CharLanguage;
import com.chaosserver.merp.character.language.MaxRanksExceededX;
import com.chaosserver.logging.CategoryCache;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;

public class LanguageRowBackgroundPanel extends JPanel implements ActionListener, PropertyChangeListener {
    protected MerpCharacter character;
    protected CharLanguage charLanguage;
    protected JButton addButton;

    public String A_ADD = "LanguageRowBackgroundPanel.Add";

    /**
     * Public constructors takes in the CharLanguage this will
     * represent
     *
     * @param language The language to represent
     */
    public LanguageRowBackgroundPanel(MerpCharacter character, CharLanguage charLanguage) {
        this.charLanguage = charLanguage;
        this.character = character;
        charLanguage.addPropertyChangeListener(this);

        BackgroundPointEA backgroundPointEA = BackgroundPointFactory.getBackgroundPointsEA(character);
        backgroundPointEA.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        addButton = new JButton("Spend Background");
        addButton.setActionCommand(A_ADD);
        addButton.addActionListener(this);
        setButtonEnabled();

        add(new LanguageNamePanel(charLanguage));
        add(new LanguageRankPanel(charLanguage));
        add(new LanguageMaxRankBackgroundPanel(character, charLanguage));
        add(addButton);
    }

    protected void setButtonEnabled() {
        if(BackgroundPointFactory.canIncreaseLanguage(character, charLanguage)) {
            setButtonEnabled(true);
        }
        else {
            setButtonEnabled(false);
        }

    }

    protected void setButtonEnabled(boolean enable) {
        addButton.setEnabled(enable);
    }

    public void actionPerformed(ActionEvent evt) {
        CategoryCache.getInstance(this).info("Catch action: " + evt.getActionCommand());

        if(A_ADD.equals(evt.getActionCommand())) {
            CategoryCache.getInstance(this).debug("Adding rank to: " + charLanguage);
            BackgroundPointFactory.increaseLanguage(character, charLanguage);
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if(CharLanguage.P_RANKS.equals(evt.getPropertyName())) {
            CategoryCache.getInstance(this).debug("Caught a P_RANKS property change");
            setButtonEnabled();
        }
        else if(BackgroundPointEA.NO_POINTS_LEFT.equals(evt.getPropertyName())) {
            setButtonEnabled();
        }
    }

}