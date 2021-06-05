package com.chaosserver.merp.gui.swing.language;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.character.language.CharLanguage;
import com.chaosserver.merp.character.language.CharLanguageList;
import com.chaosserver.merp.character.language.CharLanguageListIterator;
import com.chaosserver.logging.CategoryCache;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class LanguageListBackgroundPanel extends JPanel {
    protected CharLanguageList charLanguageList;

    protected MerpCharacter character;


    public LanguageListBackgroundPanel(MerpCharacter character, CharLanguageList charLanguageList) {
        CategoryCache.getInstance(this).info("Building a new LanguageListPanel");
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.charLanguageList = charLanguageList;
        this.character = character;

        CharLanguageListIterator charLanguageListIterator = charLanguageList.iterator();


        LanguageRowBackgroundPanel languageRowBackgroundPanel;
        while(charLanguageListIterator.hasNext()) {
            CharLanguage currCharLanguage = charLanguageListIterator.next();
            CategoryCache.getInstance(this).debug("Adding language: " + currCharLanguage.getLanguage());
            languageRowBackgroundPanel = new LanguageRowBackgroundPanel(character, currCharLanguage);
            add(languageRowBackgroundPanel);
        }
    }
}