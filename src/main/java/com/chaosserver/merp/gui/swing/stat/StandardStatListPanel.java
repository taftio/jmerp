package com.chaosserver.merp.gui.swing.stat;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.chaosserver.merp.character.stat.CharStatListIterator;
import com.chaosserver.merp.character.stat.ICharStat;
import com.chaosserver.merp.character.stat.ICharStatList;
import com.chaosserver.merp.character.stat.NotSwappableX;

/**
 * The standard stat list panel is used to display a CharStatList
 * object to the user.  It just creates a bunch of StandardStatPanel
 * objects and then sets the width of the label to be slightly larger
 * than the biggest of them.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class StandardStatListPanel extends JPanel implements PropertyChangeListener {
    /** Collection of StandardStatPanel objects */
    private ArrayList m_statPanels;

    /** Multiplier of how much to increase the size of the label */
    private static double m_nameWidthMultiplier = 1.20;

    private static double m_valueWidthMultiplier = 2;

    /** Determines if swappable stats should be enabled */
    private boolean m_isSwapAllowed = false;

    /** Holds a reference to the first stat panel of the two being swapped */
    private StandardStatPanel m_firstSSP = null;

    /** Holds a reference to the ICharStatList we're representing */
    private ICharStatList m_iCSL = null;

    /**
     * When creating the panel it's important pass in a built
     * CharStatList that will be represented by the panel.  Be careful
     * to change the referenced CharStatList to have the updates shown.
     * Throwing away the current object and making a new one isn't going
     * to be reflected.
     *
     * @param csl The CharStatList to be represented graphically
     */
    public StandardStatListPanel(ICharStatList csl) {
        double maxNameWidth = 0;
        double maxValueWidth = 0;
        m_iCSL = csl;
        m_statPanels = new ArrayList(7);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        CharStatListIterator cslIter = (CharStatListIterator) csl.iterator();

        // Iterate through the list and create a new StandardStatPanel
        // for each CharStat inside
        while(cslIter.hasNext()) {
            ICharStat currStat = (ICharStat) cslIter.next();
            StandardStatPanel currCSP = new StandardStatPanel(currStat);
            if(currCSP.getNameLabelWidth() > maxNameWidth) {
                maxNameWidth = currCSP.getNameLabelWidth();
            }
            if(currCSP.getValueWidth() > maxValueWidth) {
                maxValueWidth = currCSP.getValueWidth();
            }
            m_statPanels.add(currCSP);
            currCSP.addPropertyChangeListener(this);
        }

        maxNameWidth *= m_nameWidthMultiplier;
        maxValueWidth *= m_valueWidthMultiplier;

        ListIterator pIter = m_statPanels.listIterator();
        while(pIter.hasNext()) {
            StandardStatPanel currCSP = (StandardStatPanel) pIter.next();
            currCSP.setNameLabelWidth(maxNameWidth);
            currCSP.setValueWidth(maxValueWidth);
            add(currCSP);
        }
    }

    /**
     * Getter for widthMultiplier
     *
     * @return The value
     */
    public double getWidthMultiplier() {
        return m_nameWidthMultiplier;
    }

    /**
     * Setter for widthMultiplier
     *
     * @param m The new value
     */
    public void setWidthMultiplier(double m) {
        m_nameWidthMultiplier = m;
    }

    public boolean isSwapAllowed() {
        return m_isSwapAllowed;
    }

    public void setSwapAllowed(boolean swap) {
        ListIterator li = m_statPanels.listIterator();
        StandardStatPanel currCSP;
        while(li.hasNext()) {
            currCSP = (StandardStatPanel) li.next();
            currCSP.setSwapEnabled(swap);
        }
        m_isSwapAllowed = swap;
    }


    /*** EVENT LISTENERS *******************************************/

    /**
     * This method gets called when a bound property of m_cs is changed
     *
     * @param evt A PropertyChangeEvent object describing the event source and the property that has changed.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(StandardStatPanel.P_SWAP_ENABLED)) {
            if(   ( ((Boolean)evt.getOldValue()).booleanValue() == true)
               && ( ((Boolean)evt.getNewValue()).booleanValue() == false) ) {

                if(m_firstSSP==null) {
                    m_firstSSP=(StandardStatPanel)evt.getSource();
                } else {
                    // This is the second, so do the swap, and fix stuff
                    StandardStatPanel secondSSP = (StandardStatPanel)evt.getSource();
                    try {
                        m_iCSL.swap(m_firstSSP.getCharStat(), secondSSP.getCharStat());
                    } catch(NotSwappableX e) {
                        System.out.println("BUGBUG - Stats are swappable");
                    }

                    //m_iCSL.swap();

                    m_firstSSP.setSwapEnabled(true);
                    secondSSP.setSwapEnabled(true);
                    m_firstSSP = null;
                }

            }
            // System.out.println("Swap event");
            // m_statText.setText(((Integer) evt.getNewValue()).toString());
        }
    }

}