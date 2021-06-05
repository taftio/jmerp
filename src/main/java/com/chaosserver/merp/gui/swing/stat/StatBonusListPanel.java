package com.chaosserver.merp.gui.swing.stat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

import com.chaosserver.merp.character.stat.ICharStat;
import com.chaosserver.merp.character.stat.ICharStatList;
import com.chaosserver.merp.character.stat.CharStatListIterator;

public class StatBonusListPanel extends JPanel {
	protected ICharStatList m_charStatList;
	protected Collection m_listPanel;
	double maxWidths[] = {0,0,0,0,0,0};

	public StatBonusListPanel(ICharStatList charStatList) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		m_listPanel = new ArrayList(7);

		CharStatListIterator cslIter = charStatList.iterator();

		while(cslIter.hasNext()) {
			ICharStat charStat = (ICharStat) cslIter.next();
			StatBonusPanel statBonusPanel = new StatBonusPanel(charStat);
			setMaxWidths(statBonusPanel.getPreferredWidths());
			m_listPanel.add(statBonusPanel);
			add(statBonusPanel);
		}

		Iterator listPanelIter = m_listPanel.iterator();

		while(listPanelIter.hasNext()) {
			StatBonusPanel statBonusPanel = (StatBonusPanel) listPanelIter.next();
			statBonusPanel.setPreferredWidths(maxWidths);
		}
	}

	protected void setMaxWidths(double[] widths) {
		for(int i=0; i<6; i++) {
			if(widths[i] > maxWidths[i]) {
				maxWidths[i] = widths[i];
			}
		}
	}

}