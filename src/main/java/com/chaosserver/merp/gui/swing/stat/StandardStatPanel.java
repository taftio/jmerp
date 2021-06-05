package com.chaosserver.merp.gui.swing.stat;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.chaosserver.merp.character.stat.CharStat;
import com.chaosserver.merp.character.stat.ICharStat;
import com.chaosserver.merp.character.stat.standard.StandardCharStat;
import com.chaosserver.assertion.Assertion;
import com.chaosserver.merp.rules.stat.Stat;

/**
 * This is a panel bean to display information about a CharStat.
 * Constructing this requires passing in CharStat reference and then this bean
 * will display the current information held in the CharStat object.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class StandardStatPanel extends JPanel implements PropertyChangeListener, ActionListener, ChangeListener {
    public static String A_STAT_BUTTON_PRESSED = "STAT_BUTTON_PRESSED";
    public static String P_SWAP_ENABLED = "SWAP_ENABLED";

    /** The label for the name of the stat */
    private JButton m_statLabel;

    /** The text field containing the value of the stat */
    private JTextField m_statText;

    /** The slider for adjust the stat value */
    private JSlider m_adjustSlider;

    /** The CharStat object this StandardStatPanel is representing */
    private ICharStat m_cs;

    /** This details if the stat has been selected for swap */
    private boolean m_swapSelected = false;

    /** This details if the slider has been adjusted */

    /** Holds the reference to the property change listener */
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);


    /**
     * The constructor requires one to pass in a CharStat object.  It will
     * then provide a visual representation of that object.
     *
     * @param cs The object being represented
     * @throws InvalidParameterExceptionRX If the CharStat object is null
     */
    public StandardStatPanel(ICharStat cs) {
        Assertion.isNotNull(cs, "Cannot represent a null object!");

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        m_statLabel = new JButton(cs.getStat().getName());
        setSwapEnabled(false);
        m_statLabel.setToolTipText("You can use these buttons to swap. "
            + "Just click on a Stat name, and then click on the stat name "
            + "you wish to swap with.");

        m_statLabel.setActionCommand(A_STAT_BUTTON_PRESSED);
        m_statLabel.addActionListener(this);
        add(m_statLabel);

        int maxAdjust = cs.getStat().getMaxAdjust();
            m_adjustSlider = new JSlider(-maxAdjust, maxAdjust, 0);
            m_adjustSlider.setMajorTickSpacing(10);
            m_adjustSlider.setMinorTickSpacing(1);
            m_adjustSlider.setPaintTicks(true);
            m_adjustSlider.setSnapToTicks(true);
            m_adjustSlider.setPaintLabels(true);
        if(maxAdjust <= 0) {
            m_adjustSlider.setEnabled(false);
        }

        m_adjustSlider.addChangeListener(this);
        add(m_adjustSlider);

        m_statText = new JTextField(cs.getValue()+"");
        m_statText.setEditable(false);
        m_statText.setHorizontalAlignment(JTextField.RIGHT);
        m_statText.setMaximumSize(m_statText.getPreferredSize());
        m_statText.setToolTipText("This is the value currently in the stat.");
        add(m_statText);

        setCS(cs);
    }

    /**
     * This method gets called when a bound property of m_cs is changed
     *
     * @param evt A PropertyChangeEvent object describing the event source and the property that has changed.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        // System.out.println("StandardStatPanel.propertyChange - Got: " + evt.getPropertyName());
        if( evt.getPropertyName().equals(CharStat.P_VALUE) ) {
            m_statText.setText(((Integer) evt.getNewValue()).toString());
        }
        else if (evt.getPropertyName().equals(CharStat.P_STAT)) {
            m_statLabel.setText( ((Stat) evt.getNewValue()).getName() );
        }
        else if (evt.getPropertyName().equals(StandardCharStat.P_ADJUST_AMOUNT)) {
			m_adjustSlider.setValue( ((Integer) evt.getNewValue()).intValue() );
		}
    }

    /**
     * This method gets called when the button is clicked upon
     */
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals(StandardStatPanel.A_STAT_BUTTON_PRESSED)) {
            // getae.getSource());
            setSwapEnabled(false);
            boolean swap = isSwapSelected();
            setSwapSelected(true);
        }
    }

    /**
     * Setter method of cs.  This method removes the PropertyChangeListener
     * from the old cs and adds it to the new one.
     *
     * @param The new cs value
     */
    public void setCS(ICharStat cs) {
        if(m_cs != null) {
            m_cs.removePropertyChangeListener(this);
        }
        m_cs = cs;
        m_cs.addPropertyChangeListener(this);
    }

    /**
     * Allows one to set the preffered dimension of the name label
     *
     * @param d The new preferred size of the label
     */
    public void setNameLabelWidth(double d) {
        m_statLabel.setPreferredSize(new Dimension( (int) d, (int) m_statLabel.getPreferredSize().getHeight()) );
    }

    /**
     * Allows one to get the preferred width of the name label
     *
     * @return Preferred width
     */
    public double getNameLabelWidth() {
        return m_statLabel.getPreferredSize().getWidth();
    }

    /**
     * Allows one to set the preffered dimension of the value field
     *
     * @param d The new preferred size of the label
     */
    public void setValueWidth(double d) {
        m_statText.setPreferredSize(new Dimension( (int) d, (int) m_statLabel.getPreferredSize().getHeight()) );
    }

    /**
     * Allows one to get the preferred width of the value field
     *
     * @return Preferred width
     */
    public double getValueWidth() {
        return m_statText.getPreferredSize().getWidth();
    }

    /**
     * Allows setting of the statvalue text field to be editable
     *
     * @param b the boolean to be set
     */
    public void setEditable(boolean b) {
        m_statText.setEditable(b);
    }

    /**
     * Aloows getting of the editable-ness of the stat value text field
     *
     * @return the boolean value
     */
    public boolean isEditable() {
        return m_statText.isEditable();
    }

    public boolean isSwapEnabled() {
        return m_statLabel.isEnabled();
    }

    public void setSwapEnabled(boolean enabled) {
        boolean oldEnabled = isSwapEnabled();
        if(enabled && m_cs.getStat().isSwappable()) {
            m_statLabel.setEnabled(enabled);
        } else if(!enabled) {
            m_statLabel.setEnabled(false);
        }

        if(oldEnabled != isSwapEnabled()) {
            changes.firePropertyChange(P_SWAP_ENABLED, oldEnabled, isSwapEnabled());
        }
    }

    public ICharStat getCharStat() {
        return m_cs;
    }

    public boolean isSwapSelected() {
        return m_swapSelected;
    }

    public void setSwapSelected(boolean swapSelected) {
        m_swapSelected = swapSelected;
    }

    /**
     * Add a PropertyChangeListener to the listener list. The listener is registered for all properties.
     *
     * @param l The PropertyChangeListener to be added
     */
    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    /**
     * Remove a PropertyChangeListener from the listener list.
     * <p>
     * This removes a PropertyChangeListener that was registered for all properties.
     * </p>
     *
     * @param l The PropertyChangeListener to be removed
     */
    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }

    public void stateChanged(ChangeEvent ev) {
        if(m_adjustSlider.equals(ev.getSource())  && ((JSlider)(ev.getSource())).getValueIsAdjusting()==false) {
            // Try and bump the value
            try {
                ((StandardCharStat)m_cs).setAdjustAmount(((JSlider)ev.getSource()).getValue());
            } catch (PropertyVetoException e) {
                // Couldn't set the adjust amount, so we'll restore
                ((JSlider)ev.getSource()).setValue(m_cs.getAdjustAmount());
            }
        }

    }
}