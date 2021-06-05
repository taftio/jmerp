package com.chaosserver.merp.gui.swing.language;

import com.chaosserver.merp.character.language.BaseCharLanguage;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class LanguageMaxRankPanel extends JPanel {
    private BaseCharLanguage baseCharLanguage;

    /**
     * Public constructors takes in the CharLanguage this will
     * represent
     *
     * @param language The language to represent
     */
    public LanguageMaxRankPanel(BaseCharLanguage baseCharLanguage) {
        this.baseCharLanguage = baseCharLanguage;
        add(new JLabel(baseCharLanguage.getMaxRanks()+"", JLabel.LEFT));
    }

    public Dimension getPreferredSize() {
        return new Dimension(120, getHeight());
    }
}