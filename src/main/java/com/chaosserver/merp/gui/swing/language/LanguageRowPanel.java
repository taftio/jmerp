package com.chaosserver.merp.gui.swing.language;

import com.chaosserver.merp.character.language.BaseCharLanguage;
import com.chaosserver.merp.character.language.CharLanguage;
import com.chaosserver.merp.character.language.MaxRanksExceededX;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;

public class LanguageRowPanel extends JPanel {
    protected CharLanguage charLanguage;

    /**
     * Public constructors takes in the CharLanguage this will
     * represent
     *
     * @param language The language to represent
     */
    public LanguageRowPanel(CharLanguage charLanguage) {
        this.charLanguage = charLanguage;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(new LanguageNamePanel(charLanguage));
        add(new LanguageRankPanel(charLanguage));
    }

}