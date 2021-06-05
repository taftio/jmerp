package com.chaosserver.merp.gui.swing.stat;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;

import com.chaosserver.merp.character.stat.ICharStat;

/**
 * Provides some base functionality for panels displaying information about a CharStat.
 * This includes holding a reference to the charstat, listening for changes in it,
 * and calling refresh methods on the concrete when appropriate.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public abstract class BaseStatPanel extends JPanel implements PropertyChangeListener {
	/** Holds a reference to the charstat this is representing */
	protected ICharStat m_charStat;

	/**
	 * Setter for the CharStat
	 * This will register the object as a property change listener on this
	 *
	 * @param charStat The stat to represent
	 */
	public void setCharStat(ICharStat charStat) {
		if(getCharStat() != null) {
			getCharStat().removePropertyChangeListener(this);
		}

		m_charStat = charStat;

		if(getCharStat() != null) {
			getCharStat().addPropertyChangeListener(this);
		}
	}

	/** Getter for the CharStat */
	public ICharStat getCharStat() {
		return m_charStat;
	}

	/**
	 * Constructor requires the charStat this will represent
	 */
	public BaseStatPanel(ICharStat charStat) {
		setCharStat(charStat);
	}

	/** Refreshes the display */
	public abstract void refresh();

	/**
	 * If the character stat sends a property change this will refresh the display
	 */
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(ICharStat.P_VALUE)) {
			refresh();
		}
    }
}