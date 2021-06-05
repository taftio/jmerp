package com.chaosserver.merp.gui.swing.stat;

import javax.swing.JLabel;

import com.chaosserver.merp.character.stat.ICharStat;

/**
 * This panel displays the name of a character stat
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class StatRaceBonusPanel extends BaseStatPanel {
	/** This is the label that displays */
	protected JLabel m_nameLabel;

	public StatRaceBonusPanel(ICharStat charStat) {
		super(charStat);
		m_nameLabel = new JLabel();
		add(m_nameLabel);
		refresh();
	}

	public void refresh() {
		m_nameLabel.setText(""+getCharStat().getRaceBonus());
	}

}