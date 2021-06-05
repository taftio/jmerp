package com.chaosserver.merp.gui.swing.language;

import com.chaosserver.merp.character.language.BaseCharLanguage;
import com.chaosserver.merp.character.language.CharLanguage;
import com.chaosserver.merp.character.language.MaxRanksExceededX;
import com.chaosserver.logging.CategoryCache;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;

public class BaseLanguageRowPanel extends JPanel implements ActionListener, PropertyChangeListener {
    protected BaseCharLanguage baseCharLanguage;
    protected JButton addButton;

    public String A_ADD = "BaseLanguageRowPanel.Add";

    /**
     * Public constructors takes in the CharLanguage this will
     * represent
     *
     * @param language The language to represent
     */
    public BaseLanguageRowPanel(BaseCharLanguage baseCharLanguage) {
        this.baseCharLanguage = baseCharLanguage;
        baseCharLanguage.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        addButton = new JButton("Add Rank");
        addButton.setActionCommand(A_ADD);
        addButton.addActionListener(this);
        setButtonEnabled();

        add(new LanguageNamePanel(baseCharLanguage));
        add(new LanguageRankPanel(baseCharLanguage));
        add(new LanguageMaxRankPanel(baseCharLanguage));
        add(addButton);
    }

    protected void setButtonEnabled() {
        if(baseCharLanguage.getRanks() >= baseCharLanguage.getMaxRanks()) {
            CategoryCache.getInstance(this).debug("Hit max ranks, setting turning off Add Button for: " + baseCharLanguage.getLanguage());
            setButtonEnabled(false);
        }
        /*
        else {
            setButtonEnabled(true);
        }
        */
    }

    protected void setButtonEnabled(boolean enable) {
        addButton.setEnabled(enable);
    }

    public void actionPerformed(ActionEvent evt) {
        CategoryCache.getInstance(this).info("Catch action: " + evt.getActionCommand());

        if(A_ADD.equals(evt.getActionCommand())) {
            CategoryCache.getInstance(this).debug("Adding rank to: " + baseCharLanguage.getLanguage());
            try {
                baseCharLanguage.addRank();
            }
            catch (MaxRanksExceededX e) {
                CategoryCache.getInstance(this).error("UI should allow max ranks to be exceeded", e);
            }
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if(CharLanguage.P_RANKS.equals(evt.getPropertyName())) {
            CategoryCache.getInstance(this).debug("Caught a P_RANKS property change");
            setButtonEnabled();
        }
    }

}