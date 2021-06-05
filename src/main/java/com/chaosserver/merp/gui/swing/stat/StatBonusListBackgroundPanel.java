package com.chaosserver.merp.gui.swing.stat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

import com.chaosserver.merp.character.stat.ICharStat;
import com.chaosserver.merp.character.stat.ICharStatList;
import com.chaosserver.merp.character.stat.CharStatListIterator;
import com.chaosserver.merp.character.MerpCharacter;

public class StatBonusListBackgroundPanel extends JPanel {
	protected ICharStatList charStatList;
	protected MerpCharacter character;
	protected Collection m_listPanel;
	double maxWidths[] = {0,0,0,0,0,0};

	public StatBonusListBackgroundPanel(MerpCharacter character) {
		this.character = character;
		this.charStatList = character.getCharStatList();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		m_listPanel = new ArrayList(7);

		CharStatListIterator cslIter = charStatList.iterator();

		while(cslIter.hasNext()) {
			ICharStat charStat = (ICharStat) cslIter.next();
			StatBonusBackgroundPanel statBonusBackgroundPanel = new StatBonusBackgroundPanel(character, charStat);
			setMaxWidths(statBonusBackgroundPanel.getPreferredWidths());
			m_listPanel.add(statBonusBackgroundPanel);
			add(statBonusBackgroundPanel);
		}

		Iterator listPanelIter = m_listPanel.iterator();

		while(listPanelIter.hasNext()) {
			StatBonusBackgroundPanel statBonusBackgroundPanel = (StatBonusBackgroundPanel) listPanelIter.next();
			statBonusBackgroundPanel.setPreferredWidths(maxWidths);
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