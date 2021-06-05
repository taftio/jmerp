package com.chaosserver.merp.gui.swing.magic.realm;

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
import com.chaosserver.merp.rules.magic.realm.Realm;
import com.chaosserver.merp.rules.magic.realm.RealmFinder;
import com.chaosserver.merp.rules.magic.realm.RealmList;
import com.chaosserver.merp.rules.magic.realm.RealmListIterator;

public class RealmSelectorPanel extends Box implements ItemListener, PropertyChangeListener {
    /** The combo box that represents the character's realm */
    JComboBox m_realmBox;

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
    public RealmSelectorPanel(MerpCharacter character) {
        super(BoxLayout.Y_AXIS);
        setCharacter(character);
        m_realmBox = new JComboBox();
        m_realmBox.setEditable(false);

        RealmListIterator rli = RealmList.instance().validIterator(character);
        while(rli.hasNext()) {
            Realm currRealm = (Realm) rli.next();
            m_realmBox.addItem(currRealm.getName());
        }
        m_realmBox.addItemListener(this);

        try {
            getCharacter().setRealm(RealmFinder.instance().findByName(
                (String)m_realmBox.getSelectedItem()));

        } catch (NotFoundX e) {
            System.out.println("eating: " + e);
        } catch (PropertyVetoException e) {
            System.out.println("eating: " + e);
        }

        // BoxLayout bl = new BoxLayout(this, BoxLayout.Y_AXIS);
        add(new JLabel("Choose Realm", SwingConstants.CENTER));
        add(m_realmBox);
    }


    public void refreshList() {
        m_realmBox.removeItemListener(this);

        m_realmBox.removeAllItems();

        RealmListIterator rli = RealmList.instance().validIterator(getCharacter());
        while(rli.hasNext()) {
            Realm currRealm = (Realm) rli.next();
            m_realmBox.addItem(currRealm.getName());
        }

        m_realmBox.setSelectedItem( getCharacter().getRealm().getName() );
        m_realmBox.addItemListener(this);

    }

    /**
     * This listens for when the realm is switched and makes a
     * a property change to the character
     *
     * @param evt The event
     */
    public void itemStateChanged(ItemEvent evt) {
        if(ItemEvent.SELECTED == evt.getStateChange()) {
            try {
                Realm newRealm = RealmFinder.instance().findByName((String)evt.getItem());
                getCharacter().setRealm(newRealm);
                // System.out.println(evt.getItem());
            } catch (NotFoundX e) {
                System.out.println("Eating: " + e);
            } catch (PropertyVetoException e) {
                m_realmBox.setSelectedItem( ((Realm)e.getPropertyChangeEvent().getOldValue()).getName() );
            }
        }
    }

    /**
     * Listens to the character object in case the realm changes then updates
     *
     * @param evt The change event
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if(MerpCharacter.P_REALM.equals(evt.getPropertyName())) {
            m_realmBox.setSelectedItem( ((Realm)evt.getNewValue()).getName() );
        }
    }
}