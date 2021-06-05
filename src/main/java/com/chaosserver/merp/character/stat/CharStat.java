package com.chaosserver.merp.character.stat;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.data.FileNameGetter;
import com.chaosserver.data.JavaBeanLoader;
import com.chaosserver.data.JavaBeanLoaderExceptionX;
import com.chaosserver.data.Lookup;
import com.chaosserver.assertion.Assertion;
import com.chaosserver.merp.rules.race.Race;
import com.chaosserver.merp.rules.stat.Stat;
import com.chaosserver.merp.rules.profession.Profession;

/**
 * Holds a characters value in a single stat.
 *
 * Each <code>MerpCharacter</code> will have a collection of
 * statistics that are stored in a <code>CharStatList</code>.
 * This holds information about the characters value in a
 * single <code>Stat</code>
 *
 * The standard <code>CharStat</code> is probably not robust
 * enough for character creation uses since it does not allow
 * for stat adjustments.
 *
 * @author Jordan Reed
 * @version 1.0
 *
 * @see com.chaosserver.merp.rules.stat.Stat
 */

public class CharStat implements ICharStat, PropertyChangeListener {
    /** Holds the value of that stat. */
    protected int m_value;

    /** Holds a reference to the stat. */
    private volatile Stat m_stat;

    /** Holds a reference to the character it describes. */
    private MerpCharacter m_character;

    /** Holds the reference to the property change supporter. */
    protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /** Holds the reference to the vetoable change supporter. */
    protected VetoableChangeSupport vetoes = new VetoableChangeSupport(this);

    // javadocs inherits from the interface
    public void setCharacter(MerpCharacter character) {
        // System.out.println("CharStat.setCharacter - Setting character");
        int old_value = getValue();
        if(m_character != null) {
            m_character.removePropertyChangeListener(this);
        }
        m_character = character;
        // System.out.println("CharStat.setCharacter - Adding myself as a property change listener on character");
        m_character.addPropertyChangeListener(this);
        changes.firePropertyChange(P_VALUE, old_value, getValue());
    }

    /**
     * Returns the character this this stat is applying to.
     *
     * @return The character this stat applies to
     * @see #setCharacter
     */
    public MerpCharacter getCharacter() {
        return m_character;
    }

    /**
     * Constructor requires a stat and value.
     *
     * @param stat A reference to the stat this is a value for
     * @param value The value of the stat
     */
    public CharStat(Stat stat, int value) {
        setStat(stat);
        setValue(value);
    }

    // javadocs inherit from interface
    public Stat getStat() {
        return m_stat;
    }


    /**
     * Setter for the Stat reference.
     *
     * @param Stat the stat property
     */
    void setStat(Stat stat) {
        Assertion.isNotNull(stat, "CharStat cannot be a null value for Stat");

        Stat old_stat = m_stat;
        m_stat = stat;
        m_stat = stat;

        changes.firePropertyChange(P_STAT, old_stat, m_stat);
    }

    /**
     * Getter for the value property.
     *
     * If this character is the primary stat for the profession the
     * value will get automatically raised to a 90.
     *
     * @return The stat value
     * @see #setValue
     */
    public int getValue() {
        if(m_value < 90 && isPrimeStat()) {
            return 90;
        }

        return m_value;
    }

    /**
     * Determines if this is the prime stat for a character based on profession.
     *
     * @return Is this a prime stat
     */
    protected boolean isPrimeStat() {
        if(m_character != null) {
            return isPrimeStat(m_character.getProfession());
        } else {
            return false;
        }
    }

    /**
     * Would this stat be the prime stat for the profession.
     *
     * @param profession The profession to test against
     * @return Would this be the prime stat for the profession
     */
    protected boolean isPrimeStat(Profession profession) {
        if(profession != null) {
            return getStat().equals(profession.getPrimeStat());
        } else {
            return false;
        }
    }

    /**
     * Setter for the value property.
     *
     * @param value The stat value
     * @see #getValue
     */
    public void setValue(int value) {
        int old_value = getValue();
        m_value = value;
        changes.firePropertyChange(P_VALUE, old_value, m_value);
    }

    /**
     * Always returns 0.
     * <p>
     * Because the basic character stat does not allow for adjustment, this
     * method will always return 0.
     * </p>
     *
     * @return 0
     */
    public int getAdjustAmount() {
        return 0;
    }

    /**
     * Adjusts the stat value (note: this is not the adjust amount).
     */
    public void adjust(int amount) {
        int value = getValue();
        setValue(value + amount);
    }

    /**
     * Gets the stat's normal bonus.
     * <p>
     * Uses the <code>XML_STAT_BONUS_LOOKUP</code> lookup table to
     * determine the normal bonus given the value of the stat
     * </p>
     *
     * @return Normal bonus
     */
    public int getNormalBonus() {
        Lookup lp = null;
        try {
            lp = (Lookup) JavaBeanLoader.getBean(FileNameGetter.XML_STAT_BONUS_LOOKUP);
        }
        catch (JavaBeanLoaderExceptionX e) {
            System.out.println(e);
        }
        return ((Integer)(lp.get(new Integer(getValue())))).intValue();
    }

    // javadocs inherit from the interface
    public int getRaceBonus() {
        if(getCharacter()!=null) {
            Race race = getCharacter().getRace();
            if(race != null) {
                return race.getRaceStatBonus(getStat());
            }
        }
        return 0;
    }

    // javadocs inherit from the interface
    public int getTotalBonus() {
        return getNormalBonus() + getRaceBonus();
    }

    /**
     * Add a PropertyChangeListener to the listener list. The listener is registered for all properties.
     *
     * @param l The PropertyChangeListener to be added
     * @see #removePropertyChangeListener
     */
    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    /**
     * Remove a PropertyChangeListener from the listener list. This removes a PropertyChangeListener
     * that was registered for all properties.
     *
     * @param l The PropertyChangeListener to be removed
     * @see #addPropertyChangeListener
     */
    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }

    /**
     * Add a VetoableChangeListener to the listener list. The listener is registered for all properties.
     *
     * @param v The VetoableChangeListener to be added
     * @see #removeVetoableChangeListener
     */
    public void addVetoableChangeListener(VetoableChangeListener v) {
      vetoes.addVetoableChangeListener(v);
    }

    /**
     * Remove a VetoableChangeListener from the listener list. This removes a
     * VetoableChangeListener that was registered for all properties.
     *
     * @param v The VetoableChangeListener to be removed
     * @see #addVetoableChangeListener
     */
    public void removeVetoableChangeListener(VetoableChangeListener v) {
      vetoes.removeVetoableChangeListener(v);
    }

    /**
     * Listens for property changes that may affect this.
     * <p>
     * If the profession is changed in the merp character, and this was
     * or now is the prime stat, a property change is fired
     * </p>
     *
     * @param evt The change event
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if(MerpCharacter.P_PROFESSION.equals(evt.getPropertyName())) {
            if(isPrimeStat((Profession) evt.getOldValue()) || isPrimeStat((Profession) evt.getNewValue()) ) {
                changes.firePropertyChange(P_VALUE, m_value, getValue());
            }
        }
    }

}