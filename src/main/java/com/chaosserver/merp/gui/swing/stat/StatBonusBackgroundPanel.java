package com.chaosserver.merp.gui.swing.stat;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.character.stat.ICharStat;
import com.chaosserver.merp.character.background.BackgroundPointEA;
import com.chaosserver.merp.character.background.BackgroundPointFactory;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Represents a single stat including the Standard, Race and total bonus
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class StatBonusBackgroundPanel extends BaseStatPanel implements ActionListener {
    protected MerpCharacter character;

    protected static String oneAction = "ONE";
    protected static String twoAction = "TWO";
    protected static int ADD_ONE = 1;
    protected static int ADD_TWO = 2;
    protected JButton oneButton;
    protected JButton twoButton;
    protected Collection m_panels = new ArrayList(6);

    public StatBonusBackgroundPanel(MerpCharacter character, ICharStat charStat) {
        super(charStat);

        this.character = character;

        BackgroundPointEA backgroundPointEA = BackgroundPointFactory.getBackgroundPointsEA(character);
        backgroundPointEA.addPropertyChangeListener(this);

        oneButton = new JButton("+1");
        oneButton.addActionListener(this);
        oneButton.setActionCommand(oneAction);
        add(oneButton);

        twoButton = new JButton("+2");
        twoButton.addActionListener(this);
        twoButton.setActionCommand(twoAction);
        add(twoButton);

        refresh();

        JPanel currPanel;

        currPanel = new StatNamePanel(getCharStat());
        m_panels.add(currPanel);
        add(currPanel);

        currPanel = new StatAbbrPanel(getCharStat());
        m_panels.add(currPanel);
        add(currPanel);

        currPanel = new StatValuePanel(getCharStat());
        m_panels.add(currPanel);
        add(currPanel);

        currPanel = new StatNormalBonusPanel(getCharStat());
        m_panels.add(currPanel);
        add(currPanel);

        currPanel = new StatRaceBonusPanel(getCharStat());
        m_panels.add(currPanel);
        add(currPanel);

        currPanel = new StatTotalBonusPanel(getCharStat());
        m_panels.add(currPanel);
        add(currPanel);
    }

    public void refresh() {
        oneButton.setEnabled(BackgroundPointFactory.canStatIncrease(character, getCharStat(), ADD_ONE));
        twoButton.setEnabled(BackgroundPointFactory.canStatIncrease(character, getCharStat(), ADD_TWO));
    }

    public double[] getPreferredWidths() {
        Iterator iter = m_panels.iterator();
        double widths[] = new double[6];
        int i = 0;
        while(iter.hasNext()) {
            JPanel currPanel = (JPanel) iter.next();
            widths[i] = currPanel.getPreferredSize().getWidth();
            i++;
        }

        return widths;
    }

    public void setPreferredWidths(double[] widths) {
        Iterator iter = m_panels.iterator();
        int i = 0;
        while(iter.hasNext()) {
            JPanel currPanel = (JPanel) iter.next();
            Dimension d = currPanel.getPreferredSize();
            //System.out.println(currPanel.getClass().getName() + " - " + d.getHeight() );
            currPanel.setPreferredSize(new Dimension((int)widths[i], (int)d.getHeight()));
            i++;
        }
    }

    public void actionPerformed(ActionEvent ev) {
        String actionCommand = ev.getActionCommand();
        if(twoAction.equals(actionCommand)) {
            BackgroundPointFactory.increaseStat(character, getCharStat(), ADD_TWO);
        }
        else if (oneAction.equals(actionCommand)) {
            BackgroundPointFactory.increaseStat(character, getCharStat(), ADD_ONE);
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt);

        refresh();

        //if(evt.getPropertyName().equals(BackgroundPointEA.NO_POINTS_LEFT)) {
        //    refresh();
        //}
    }

}