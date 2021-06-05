package com.chaosserver.merp.gui.swing.wizcards;

import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;
import javax.swing.JPanel;

import com.chaosserver.merp.character.MerpCharacter;

/**
 * A generic wizard card for use in one of the wizard applications.
 *
 * The base wizard card holds the various hook methods that a wizard
 * will use to notify a card that something has occurred in the main
 * wixard applications.
 *
 * Cards extending from this one are exected to represent data inside
 * a <code>MerpCharacter</code> object and possibly manipulate
 * the data inside that character
 *
 * @author Jordan Reed
 * @version 1.0
 */
public abstract class AbstractCharGenWizPanel extends JPanel implements Serializable {
    /** Holds the reference to the property change supporter */
    protected PropertyChangeSupport changes = new PropertyChangeSupport(this);
    /** Holds the reference to the vetoable change supporter */
    protected VetoableChangeSupport vetoes = new VetoableChangeSupport(this);

    /** Is this panel valid, and ready to go to next? */
    private boolean m_nextValid = false;

    /** Property change event description for nextValid */
    public static String P_NEXT_VALID = "AbstractCharGenWizPanel.nextValid";

    /**
     * Getter for nextAllowed
     *
     * @return next allowed
     */
    public boolean isNextValid() {
        return m_nextValid;
    }

    /**
     * Setter for next Allowed
     */
    public void setNextValid(boolean nextValid) {
        boolean old_nextValid = isNextValid();
        m_nextValid = nextValid;
        changes.firePropertyChange(P_NEXT_VALID, old_nextValid, m_nextValid);
    }


    /** This defines if the card has ever been build before */
    private boolean m_built = false;

    /**
     * This is expected to return a description of what this panel is used
     * for and will be displayed to the users.
     *
     * @return a description of the use for this panel
     */
    public abstract String getDescription();

    /**
     * This methods checks to see if the form is valid to go to next
     * Next allowed is a virtual property
     *
     * @return Whether or not you can move forward
     */
    public boolean isNextAllowed() {
        if(getValidationError() == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method returns a description of what is not valid.  If
     * everything is valid, it will return null.
     *
     * @return Description of validation error
     */
    public String getValidationError() {
        return null;
    }

    /**
     * This method is called when the wizard leaves this panel to go
     * to the next.
     */
    public void onNext(MerpCharacter character) {
    }

    /**
     * This method is called when the wizard leaves this panel to go
     * to the previous panel
     */
    public void onPrev() {
    }

    /**
     * This method is called the first time it's built.
     *
     */
    protected abstract void buildCard(MerpCharacter character);

    /**
     * this method is called each subsequent time it's built
     */
    protected void rebuildCard(MerpCharacter character) {
    }

    /**
     * This methods will call the childs cardBuild the first time it's used.
     *
     */
    public void build(MerpCharacter character) {
        if(m_built) {
            rebuildCard(character);
        } else {
            // Build it for the first time
            buildCard(character);
            m_built = true;
        }
    }

	/**
	 * This method is called to see if the card needs to be presented
	 * to the user.
	 * <p>
	 * The base implementation if false
	 * </p>
	 *
	 * @return false
	 */
	public boolean isSkippable(MerpCharacter character) {
		return false;
	}
}