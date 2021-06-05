package com.chaosserver.merp.gui.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PackPanel extends JPanel {
    public PackPanel(Component comp) {
        setLayout(new BorderLayout());
        JPanel inPanel = new JPanel(new BorderLayout());
        JPanel outPanel = new JPanel(new BorderLayout());
        inPanel.add(comp, BorderLayout.WEST);
        add(inPanel, BorderLayout.NORTH);
    }
}