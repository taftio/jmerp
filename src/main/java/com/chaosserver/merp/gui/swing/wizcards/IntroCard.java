package com.chaosserver.merp.gui.swing.wizcards;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.chaosserver.merp.data.FileNameGetter;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.gui.swing.image.MerpLogoCanvas;

public class IntroCard extends AbstractCharGenWizPanel {
    public void buildCard(MerpCharacter mChar) {
        Box myBox = new Box(BoxLayout.Y_AXIS);

        myBox.add(MerpLogoCanvas.getInstance());

        JTextArea heading = new JTextArea("Character Creation Steps");
        Font myFont = heading.getFont();
        myFont = new Font(myFont.getFontName(), myFont.getStyle(), myFont.getSize()*2);
        heading.setFont(myFont);
        heading.setEditable(false);
        heading.setBackground(new Color(206,206,206));
        heading.setLineWrap(true);
        heading.setWrapStyleWord(true);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        myBox.add(heading);

        JTextArea inst = new JTextArea(""
            + "1) Decide in general what type of character to play.\n"
            + "2) Roll and assign your character's stats.\n"
            + "3) Choose a race for your character.\n"
            + "4) Choose a profession for your character.\n"
            + "Pause\n"
            + "5) Determine role traits and background.\n"
            + "6) Develop your character's adolescence skills.\n"
            + "Pause\n"
            + "7) Develop your character's apprenticeship skills.\n"
            + "Pause\n"
            + "8) Outfit your character.\n"
            + "9) Total your character's penalties and bonuses.\n"
            + "10) Develop a persona for your character.\n",
            13, 30);

        inst.setEditable(false);
        inst.setBackground(new Color(206,206,206));
        inst.setLineWrap(true);
        inst.setWrapStyleWord(true);

        myBox.add(inst);
        add(myBox);

    }

    public String getDescription() {
        return "This area will provide help as you move forward.";
    }
}