package com.chaosserver.merp.gui.swing;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.chaosserver.merp.character.stat.standard.StandardCharStatList;
import com.chaosserver.merp.character.stat.standard.StandardCharStatListFactory;
import com.chaosserver.merp.gui.swing.stat.StandardStatRollerPanel;

public class StatRoller {
    public StatRoller() {
        //Create the top-level container and add contents to it.
        StandardCharStatListFactory slf = StandardCharStatListFactory.instance();
        StandardCharStatList csl = (StandardCharStatList) slf.createCharStatList();
        StandardStatRollerPanel sPanel = new StandardStatRollerPanel(csl);

        JFrame frame = new JFrame("MERP Stat Roller");
        frame.getContentPane().add(sPanel, BorderLayout.CENTER);

        //Finish setting up the frame, and show it.
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        ImageIcon ii = new ImageIcon(System.getProperty("MERP_HOME")+"\\merp\\gui\\image\\dice.gif");
        frame.setIconImage(ii.getImage());
        frame.pack();
        frame.setLocation(100,100);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) { }

        //System.out.println(System.getProperty("MERP_HOME"));
        //System.getProperties().list(System.out);
        StatRoller app = new StatRoller();
    }

}

