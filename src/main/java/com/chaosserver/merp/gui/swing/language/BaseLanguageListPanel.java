package com.chaosserver.merp.gui.swing.language;

import com.chaosserver.logging.CategoryCache;
import com.chaosserver.merp.character.language.BaseCharLanguage;
import com.chaosserver.merp.character.language.BaseCharLanguageList;
import com.chaosserver.merp.character.language.CharLanguageListIterator;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class BaseLanguageListPanel extends JPanel implements PropertyChangeListener {
    protected BaseCharLanguageList charLanguageList;
    protected Collection languageRows;


    public BaseLanguageListPanel(BaseCharLanguageList charLanguageList) {
        this.languageRows = new ArrayList(17);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.charLanguageList = charLanguageList;
        charLanguageList.addPropertyChangeListener(this);
        CharLanguageListIterator charLanguageListIterator = charLanguageList.iterator();


        add(new BaseLanguageRowTitlePanel());

        BaseLanguageRowPanel baseLanguageRowPanel;
        while(charLanguageListIterator.hasNext()) {
            BaseCharLanguage currBaseCharLanguage = (BaseCharLanguage) charLanguageListIterator.next();
            baseLanguageRowPanel = new BaseLanguageRowPanel(currBaseCharLanguage);
            this.languageRows.add(baseLanguageRowPanel);
            add(baseLanguageRowPanel);
        }

        add(new BaseLanguageListRanksPanel(this.charLanguageList));
    }

    /**
     * Listens for property changes on the character that apply to the panel
     * <p>
     * This currently includes turning of all the add buttons when there
     * are no free ranks in the character
     * </p>
     *
     * @param evt The property event
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if(BaseCharLanguageList.P_FREE_RANKS.equals(evt.getPropertyName())) {
            CategoryCache.getInstance(this).debug("Caught a P_FREE_RANKS property change");
            if( ((Integer) evt.getNewValue()).intValue() <= 0) {
                // There are no free ranks left, so all of the add buttons
                // get turned off
                CategoryCache.getInstance(this).debug("P_FREE_RANKS is less than 1 so turn off the add buttons");
                Iterator panelIter = languageRows.iterator();
                BaseLanguageRowPanel baseLanguageRowPanel;
                while(panelIter.hasNext()) {
                    baseLanguageRowPanel = (BaseLanguageRowPanel) panelIter.next();
                    baseLanguageRowPanel.setButtonEnabled(false);
                }

            }
        }
    }
}