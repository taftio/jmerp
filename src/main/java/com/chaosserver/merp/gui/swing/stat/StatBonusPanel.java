package com.chaosserver.merp.gui.swing.stat;

import java.awt.Dimension;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JPanel;

import com.chaosserver.merp.character.stat.ICharStat;

/**
 * Represents a single stat including the Standard, Race and total bonus
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class StatBonusPanel extends BaseStatPanel {
	Collection m_panels = new ArrayList(6);

	public StatBonusPanel(ICharStat charStat) {
		super(charStat);

		JPanel currPanel;

		currPanel = new StatNamePanel(getCharStat());
		m_panels.add(currPanel);
		add(currPanel);

		currPanel = new StatAbbrPanel(getCharStat());
		m_panels.add(currPanel);
		add(currPanel);

		currPanel = new StatValuePanel(getCharStat());
		m_panels.add(currPanel);
		add(currPanel);

		currPanel = new StatNormalBonusPanel(getCharStat());
		m_panels.add(currPanel);
		add(currPanel);

		currPanel = new StatRaceBonusPanel(getCharStat());
		m_panels.add(currPanel);
		add(currPanel);

		currPanel = new StatTotalBonusPanel(getCharStat());
		m_panels.add(currPanel);
		add(currPanel);
	}

	public void refresh() {
		// Does nothing
	}

	public double[] getPreferredWidths() {
		Iterator iter = m_panels.iterator();
		double widths[] = new double[6];
		int i = 0;
		while(iter.hasNext()) {
			JPanel currPanel = (JPanel) iter.next();
			widths[i] = currPanel.getPreferredSize().getWidth();
			i++;
		}

		return widths;
	}

	public void setPreferredWidths(double[] widths) {
		Iterator iter = m_panels.iterator();
		int i = 0;
		while(iter.hasNext()) {
			JPanel currPanel = (JPanel) iter.next();
			Dimension d = currPanel.getPreferredSize();
			//System.out.println(currPanel.getClass().getName() + " - " + d.getHeight() );
			currPanel.setPreferredSize(new Dimension((int)widths[i], (int)d.getHeight()));
			i++;
		}
	}


}