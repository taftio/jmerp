package com.chaosserver.merp.gui.swing.language;

import com.chaosserver.merp.character.language.BaseCharLanguageList;
import com.chaosserver.logging.CategoryCache;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BaseLanguageListRanksPanel extends JPanel implements PropertyChangeListener {
    protected BaseCharLanguageList baseCharLanguageList;
    protected JLabel ranksLabel;

    public BaseLanguageListRanksPanel(BaseCharLanguageList baseCharLanguageList) {
        this.baseCharLanguageList = baseCharLanguageList;
        baseCharLanguageList.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(new JLabel("Ranks left to spend: "));
        ranksLabel = new JLabel(baseCharLanguageList.getFreeRanks()+"");
        add(ranksLabel);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if(BaseCharLanguageList.P_FREE_RANKS.equals(evt.getPropertyName())) {
            CategoryCache.getInstance(this).debug("Caught a P_FREE_RANKS property change");
            ranksLabel.setText(evt.getNewValue() + "");
        }
    }

}