package com.chaosserver.merp.gui.swing.language;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class BaseLanguageRowTitlePanel extends JPanel {
    /**
     * Public constructors takes in the CharLanguage this will
     * represent
     *
     * @param language The language to represent
     */
    public BaseLanguageRowTitlePanel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		JLabel currLabel;
		Dimension d;

		currLabel = new JLabel("Name");
		d = currLabel.getPreferredSize();
		currLabel.setPreferredSize(new Dimension(120, (int) d.getHeight()));
		add(currLabel);

		currLabel = new JLabel("Rank");
		d = currLabel.getPreferredSize();
		currLabel.setPreferredSize(new Dimension(120, (int) d.getHeight()));
		add(currLabel);

		currLabel = new JLabel("Max Ranks");
		d = currLabel.getPreferredSize();
		currLabel.setPreferredSize(new Dimension(120, (int) d.getHeight()));
		add(currLabel);
    }
}