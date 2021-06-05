package com.chaosserver.merp.gui.swing.language;

import com.chaosserver.merp.character.language.CharLanguage;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class LanguageNamePanel extends JPanel {
    private CharLanguage charLanguage;

    /**
     * Public constructors takes in the CharLanguage this will
     * represent
     *
     * @param language The language to represent
     */
    public LanguageNamePanel(CharLanguage charLanguage) {
        this.charLanguage = charLanguage;
        add(new JLabel(charLanguage.getLanguage().getName(), JLabel.LEFT));
    }

    public Dimension getPreferredSize() {
		Dimension d = super.getPreferredSize();
        return new Dimension(120, (int) d.getHeight());
    }

}