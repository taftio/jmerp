package com.chaosserver.merp.gui.swing.race;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.assertion.Assertion;
import com.chaosserver.exception.NotFoundX;
import com.chaosserver.merp.rules.race.Race;
import com.chaosserver.merp.rules.race.RaceFinder;
import com.chaosserver.merp.rules.race.RaceList;
import com.chaosserver.merp.rules.race.RaceListIterator;
import com.chaosserver.logging.CategoryCache;

public class RaceSelectorPanel extends Box implements ItemListener, PropertyChangeListener {
    /** The combo box that represents the character's race */
    JComboBox m_raceBox;

    /** Reference the character this is representing */
    private MerpCharacter m_character;

    /**
     * Getter for the character
     *
     * @return The character being represented
     */
    public MerpCharacter getCharacter() {
        return m_character;
    }

    /**
     * Setter for the character
     *
     * @param the character to represent
     */
    private void setCharacter(MerpCharacter character) {
        if(m_character != null) {
            m_character.removePropertyChangeListener(this);
        }

        m_character = character;
        m_character.addPropertyChangeListener(this);
    }

    /**
     * Constructs it
     *
     * @param character The character to represent
     */
    public RaceSelectorPanel(MerpCharacter character) {
        super(BoxLayout.Y_AXIS);
        setCharacter(character);
        m_raceBox = new JComboBox();
        m_raceBox.setEditable(false);

        RaceListIterator rli = RaceList.instance().validIterator(character);
        Assertion.isNotNull(rli, "ValidRaceListIterator went null for some reason");
        while(rli.hasNext()) {
            Race currRace = (Race) rli.next();
            m_raceBox.addItem(currRace.getName());
        }
        m_raceBox.addItemListener(this);

        try {
            getCharacter().setRace(RaceFinder.findByName((String)m_raceBox.getSelectedItem()));
        } catch (NotFoundX e) {
            CategoryCache.getInstance(this).fatal("Race not found.", e);
        } catch (PropertyVetoException e) {
            CategoryCache.getInstance(this).error("Vetod a randomly selected character.", e);
        }

        // BoxLayout bl = new BoxLayout(this, BoxLayout.Y_AXIS);
        add(new JLabel("Choose Race", SwingConstants.CENTER));
        add(m_raceBox);
    }

    /**
     * This listens for when the race is switched and makes a
     * a property change to the character
     *
     * @param evt The event
     */
    public void itemStateChanged(ItemEvent evt) {
        if(ItemEvent.SELECTED == evt.getStateChange()) {
            try {
                Race newRace = RaceFinder.instance().findByName((String)evt.getItem());
                getCharacter().setRace(newRace);
                // System.out.println(evt.getItem());
            } catch (NotFoundX e) {
                CategoryCache.getInstance(this).fatal("Race not found.", e);
            } catch (PropertyVetoException e) {
                m_raceBox.setSelectedItem( ((Race)e.getPropertyChangeEvent().getOldValue()).getName() );
            }
        }
    }

    /**
     * Listens to the character object in case the race changes then updates
     *
     * @param evt The change event
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if(MerpCharacter.P_RACE.equals(evt.getPropertyName())) {
            m_raceBox.setSelectedItem( ((Race)evt.getNewValue()).getName() );

        }
    }

    public void addValidRaces() {

    }

    public void resfreshList() {
        CategoryCache.getInstance(this).info("ENTER");
        m_raceBox.removeItemListener(this);

        m_raceBox.removeAllItems();

        RaceListIterator rli = RaceList.instance().validIterator(getCharacter());
        while(rli.hasNext()) {
            Race currRace = (Race) rli.next();
            m_raceBox.addItem(currRace.getName());
        }

        m_raceBox.setSelectedItem( getCharacter().getRace().getName() );
        m_raceBox.addItemListener(this);
    }

}