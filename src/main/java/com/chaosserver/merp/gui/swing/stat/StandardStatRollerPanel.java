package com.chaosserver.merp.gui.swing.stat;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.chaosserver.merp.character.stat.ICharStatList;

/**
 * This panel allows one to roll basic stats for the a character.
 * There are various ways to implement this gui.  The recommended
 * way is to pass in a ICharStatList object.
 * @author Jordan Reed
 * @version 1.0
 */
public class StandardStatRollerPanel extends JPanel {
    private StandardStatListPanel sslp;
    public static String A_SWAP = "SWAP";
    private ICharStatList m_iCharStatList;


    public StandardStatRollerPanel(ICharStatList csl) {
        m_iCharStatList = csl;
        setLayout(new BorderLayout());
        sslp = new StandardStatListPanel(csl);
        sslp.setSwapAllowed(true);
        add(sslp, BorderLayout.CENTER);


        JButton rollButton = new JButton("Roll");
        rollButton.setToolTipText("Reroll all of you stats to get a new fresh set.");
        rollButton.setActionCommand(ICharStatList.A_ROLL);
        rollButton.addActionListener((ActionListener) csl);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(rollButton);

        add(buttonPanel, BorderLayout.SOUTH);


    }

}