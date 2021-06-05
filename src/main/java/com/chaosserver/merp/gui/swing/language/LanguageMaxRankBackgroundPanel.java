package com.chaosserver.merp.gui.swing.language;

import com.chaosserver.merp.character.background.BackgroundPointFactory;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.character.language.CharLanguage;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class LanguageMaxRankBackgroundPanel extends JPanel {
    private CharLanguage charLanguage;

    /**
     * Public constructors takes in the CharLanguage this will
     * represent
     *
     * @param language The language to represent
     */
    public LanguageMaxRankBackgroundPanel(MerpCharacter character, CharLanguage charLanguage) {
        this.charLanguage = charLanguage;
        add(new JLabel(BackgroundPointFactory.maxLanguageValue(character, charLanguage)+"", JLabel.LEFT));
    }

    public Dimension getPreferredSize() {
        return new Dimension(120, getHeight());
    }
}