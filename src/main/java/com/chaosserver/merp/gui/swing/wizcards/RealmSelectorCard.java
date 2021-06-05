package com.chaosserver.merp.gui.swing.wizcards;

import com.chaosserver.logging.CategoryCache;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.gui.swing.magic.realm.RealmSelectorPanel;
import com.chaosserver.merp.rules.magic.realm.Realm;
import com.chaosserver.merp.rules.magic.realm.RealmList;
import com.chaosserver.merp.rules.magic.realm.RealmListIterator;

import java.beans.PropertyVetoException;

public class RealmSelectorCard extends AbstractCharGenWizPanel {
    /** This is the panel being represented */
    private RealmSelectorPanel m_realmSelectorPanel;

    /**
     * Setter for the realm selector panel
     *
     * @param realmSelectorPanel The new panel value
     */
    private void setRealmSelectorPanel(RealmSelectorPanel realmSelectorPanel) {
        m_realmSelectorPanel = realmSelectorPanel;
    }

    /**
     * Geter for the realm selector panel
     *
     * @return The panel
     */
    public RealmSelectorPanel getRealmSelectorPanel() {
        return m_realmSelectorPanel;
    }


    /**
     * Returns a description of how the user should manipulate this panel
     *
     * @return description
     */
    public String getDescription() {
        return "Select your realm here.  Only allowed realms "
            + "will appear.  The most common reason why a realm will not "
            + "appear would be that it is not allowed by the Race";
    }

    /**
     * this method is called each subsequent time it's built
     */
    protected void rebuildCard(MerpCharacter character) {
        m_realmSelectorPanel.refreshList();
    }


    /**
     * This will build the card to roll the stats.
     * If the character does not have a stat list, it will create a new stat
     * list for the character
     *
     * @param character the character
     */
    public void buildCard(MerpCharacter character) {
        setRealmSelectorPanel(new RealmSelectorPanel(character));
        add(getRealmSelectorPanel());
    }

    /**
     * Skips if there is a no choice to be made
     * <p>
     * Some professions (mage, animist, etc.) limit the realms a character
     * can choose to one.  So if this is limited to only one realm, then
     * this card will be skipped
     * </p>
     *
     * @return True if there is no choice
     */
    public boolean isSkippable(MerpCharacter character) {
        RealmListIterator rli = RealmList.instance().validIterator(character);
        Realm realm = null;
        int count = 0;
        boolean skippable = true;
        while(rli.hasNext()) {
            realm = (Realm) rli.next();
            count++;
            if(count>1) {
                skippable = false;
                break;
            }
        }
        if(skippable) {
            try {
                character.setRealm(realm);
            } catch (PropertyVetoException e) {
                CategoryCache.getInstance(this).error("Could not set realm", e);
            }
        }
        return skippable;
    }
}