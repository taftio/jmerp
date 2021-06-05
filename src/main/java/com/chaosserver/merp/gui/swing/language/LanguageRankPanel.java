package com.chaosserver.merp.gui.swing.language;

import com.chaosserver.merp.character.language.CharLanguage;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class LanguageRankPanel extends JPanel implements PropertyChangeListener {
    protected CharLanguage charLanguage;
    protected JLabel rankLabel;

    /**
     * Public constructors takes in the CharLanguage this will
     * represent
     *
     * @param language The language to represent
     */
    public LanguageRankPanel(CharLanguage charLanguage) {
        this.charLanguage = charLanguage;
        this.charLanguage.addPropertyChangeListener(this);
        rankLabel = new JLabel(charLanguage.getRanks()+"", JLabel.LEFT);
        add(rankLabel);
    }

	/*
    public Dimension getPreferredSize() {
        return new Dimension(120, getHeight());
    }
    */

    public void propertyChange(PropertyChangeEvent evt) {
		if(CharLanguage.P_RANKS.equals(evt.getPropertyName())) {
			rankLabel.setText(evt.getNewValue().toString());
		}
	}
}