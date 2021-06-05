package com.chaosserver.merp.character.stat.standard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.util.ArrayList;
import java.util.ListIterator;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.character.stat.CharStat;
import com.chaosserver.merp.character.stat.CharStatListIterator;
import com.chaosserver.merp.character.stat.ICharStat;
import com.chaosserver.merp.character.stat.ICharStatList;
import com.chaosserver.merp.character.stat.NotSwappableX;
import com.chaosserver.assertion.Assertion;

/**
 * This contains the list of stats for a character.
 *
 * @author Jordan Reed
 * @version 1.0
 */

public class StandardCharStatList implements ICharStatList,
                                             ActionListener,
                                             PropertyChangeListener,
                                             VetoableChangeListener {

    /** Holds the reference to the property change supporter. */
    protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /** Holds the reference to the vetoable change supporter. */
    protected VetoableChangeSupport vetoes = new VetoableChangeSupport(this);


    /** The array list holding the actual charstats. */
    ArrayList m_charStats;

    // PROPERTIES ///////////////////////////////////////////////////

    /** Property: Holds the pool of points to share. */
    private int m_pool = 0;

    /** Property change event. */
    public static String P_POOL = "P_POOL";

    /**
     * Getter for the original value property.
     *
     * @return the pool value
     */
    public int getPool() {
        ListIterator li = m_charStats.listIterator();
        int pool = 0;
        while(li.hasNext()) {
            StandardCharStat cs = (StandardCharStat) li.next();
            pool += cs.getAdjustAmount();
        }
        return (-pool);
    }

    // END PROPERTIES ///////////////////////////////////////////////

    /**
     * Gets a reference to the list.
     *
     * @return the list of stats
     */
    public ArrayList getCharStatList() {
        return m_charStats;
    }

    /**
     * The construct takes a list of charStats.
     * This should only be created by a factory in the package hence the
     * friendly access.
     *
     * @param charStats The list of CharStat objects
     */
    StandardCharStatList(ArrayList charStats) {
        Assertion.isNotNull(charStats, "Cannot create a stat list with null charstats");
        Assertion.isTrue(charStats.size() > 0, "Cannot create a stat list with blanks list");

        m_charStats = charStats;

        ListIterator li = m_charStats.listIterator();
        while(li.hasNext()) {
            CharStat cs = (CharStat) li.next();
            cs.addVetoableChangeListener(this);
            cs.addPropertyChangeListener(this);
        }
    }

    /**
     * This rerolls the values inside of the list.
     */
    public void reroll() {
        StandardCharStatListFactory.instance().createCharStatList(this);
    }

    /**
     * This will swap the values of two CharStats in the list, if swapping
     * is allowed.
     *
     * @param s1 The first stat
     * @param s2 The second stat
     * @throws NotSwappableX If the stats cannot be swapped
     */
    public void swap(ICharStat s1, ICharStat s2) throws NotSwappableX {
        StandardCharStat cs1 = (StandardCharStat) s1;
        StandardCharStat cs2 = (StandardCharStat) s2;
        // BUGBUG - Check if these two stats are in the list
        if( (cs1.getStat().isSwappable()) && (cs2.getStat().isSwappable()) ) {
            if(cs1.getAdjustAmount() <= cs2.getAdjustAmount()) {
                int tempValue = ((StandardCharStat) cs2).getValue();
                int origValue = ((StandardCharStat) cs2).getOrigValue();
                cs2.setValue(cs1.getValue(), cs1.getOrigValue());
                cs1.setValue(tempValue, origValue);
            } else {
                int tempValue = ((StandardCharStat) cs1).getValue();
                int origValue = ((StandardCharStat) cs1).getOrigValue();
                cs1.setValue(cs2.getValue(), cs2.getOrigValue());
                cs2.setValue(tempValue, origValue);
            }
        } else {
            throw new NotSwappableX("Cannot swap these stats");
        }


    }

    /**
     * Gets an iterator over the collection.
     *
     * @return The iterator
     */
    public CharStatListIterator iterator() {
        return new CharStatListIterator(this);
    }

    /**
     *
     */
    public void setCharacter(MerpCharacter character) {
        CharStatListIterator cslIter = iterator();
        while(cslIter.hasNext()) {
            ((ICharStat)cslIter.next()).setCharacter(character);
        }

    }


    /**
     * Returns the rank of a stat.
     *
     * @param charStat the CharStat to rank
     * @return the rank
     */
    public int getRank(ICharStat charStat) {
        int greater = 0;

        CharStatListIterator cslIter = (CharStatListIterator) iterator();
        while(cslIter.hasNext()) {
            CharStat cs = (CharStat) cslIter.next();
            if(cs.getValue() > charStat.getValue()) {
                greater++;
            }
        }
        return greater+1;
    }


    // Inherits from interface
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals(A_ROLL)) {
            reroll();
        }
    }

    // Inherits from interface
    public void propertyChange(PropertyChangeEvent evt) {
    }

    // Inherits from interface
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        if(evt.getPropertyName().equals(StandardCharStat.P_ADJUST_AMOUNT)) {
            int change = ((Integer)evt.getOldValue()).intValue() - ((Integer)evt.getNewValue()).intValue();
            if( getPool()+change < 0 ) {
                //  System.out.println("StandardCharStatList.vetoableChange() - VETOING adjust amount");
                throw new PropertyVetoException("Cannot have negative pool!", evt);
            } else {
                // System.out.println("StandardCharStatList.vetoableChange() - Looks okay");
            }
        }
    }

    /**
     * Converts into a plaintext list of name=value for the stats.
     *
     * @return String of the stats
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        CharStatListIterator iterator = this.iterator();

        while(iterator.hasNext()) {
            ICharStat cStat = (ICharStat) iterator.next();
            sb.append(cStat.getStat().getName() + "=" + cStat.getValue() + ", ");
        }

        return sb.toString();
    }
}