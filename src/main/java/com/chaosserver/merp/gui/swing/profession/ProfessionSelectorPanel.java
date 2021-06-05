package com.chaosserver.merp.gui.swing.profession;

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
import com.chaosserver.exception.NotFoundX;
import com.chaosserver.merp.rules.profession.Profession;
import com.chaosserver.merp.rules.profession.ProfessionFinder;
import com.chaosserver.merp.rules.profession.ProfessionList;
import com.chaosserver.merp.rules.profession.ProfessionListIterator;

public class ProfessionSelectorPanel extends Box implements ItemListener, PropertyChangeListener {
    /** The combo box that represents the character's profession */
    JComboBox m_professionBox;

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
    public ProfessionSelectorPanel(MerpCharacter character) {
        super(BoxLayout.Y_AXIS);
        setCharacter(character);
        m_professionBox = new JComboBox();
        m_professionBox.setEditable(false);

        ProfessionListIterator rli = ProfessionList.instance().validIterator(character);
        while(rli.hasNext()) {
            Profession currProfession = (Profession) rli.next();
            m_professionBox.addItem(currProfession.getName());
        }
        m_professionBox.addItemListener(this);

        try {
            getCharacter().setProfession(ProfessionFinder.instance().findByName(
                (String)m_professionBox.getSelectedItem()));

        } catch (NotFoundX e) {
            System.out.println("eating: " + e);
        } catch (PropertyVetoException e) {
            System.out.println("eating: " + e);
        }

        // BoxLayout bl = new BoxLayout(this, BoxLayout.Y_AXIS);
        add(new JLabel("Choose Profession", SwingConstants.CENTER));
        add(m_professionBox);
    }


    public void refreshList() {
        m_professionBox.removeItemListener(this);

        m_professionBox.removeAllItems();

        ProfessionListIterator rli = ProfessionList.instance().validIterator(getCharacter());
        while(rli.hasNext()) {
            Profession currProfession = (Profession) rli.next();
            m_professionBox.addItem(currProfession.getName());
        }

        m_professionBox.setSelectedItem( getCharacter().getProfession().getName() );
        m_professionBox.addItemListener(this);

    }

    /**
     * This listens for when the profession is switched and makes a
     * a property change to the character
     *
     * @param evt The event
     */
    public void itemStateChanged(ItemEvent evt) {
        if(ItemEvent.SELECTED == evt.getStateChange()) {
            try {
                Profession newProfession = ProfessionFinder.instance().findByName((String)evt.getItem());
                getCharacter().setProfession(newProfession);
                // System.out.println(evt.getItem());
            } catch (NotFoundX e) {
                System.out.println("Eating: " + e);
            } catch (PropertyVetoException e) {
                m_professionBox.setSelectedItem( ((Profession)e.getPropertyChangeEvent().getOldValue()).getName() );
            }
        }
    }

    /**
     * Listens to the character object in case the profession changes then updates
     *
     * @param evt The change event
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if(MerpCharacter.P_PROFESSION.equals(evt.getPropertyName())) {
            m_professionBox.setSelectedItem( ((Profession)evt.getNewValue()).getName() );

        }
    }

}