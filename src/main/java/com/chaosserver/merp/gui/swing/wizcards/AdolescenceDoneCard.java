package com.chaosserver.merp.gui.swing.wizcards;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.chaosserver.logging.CategoryCache;
import com.chaosserver.merp.data.FileNameGetter;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.character.language.CharLanguageFactory;
import com.chaosserver.merp.gui.swing.image.MerpLogoCanvas;

/**
 * A place holder card for a significant event.
 *
 * This card is used as a place holder in the character generation wizard.
 * it informs the character where they are in the process and warns them
 * that once moving forward they will be unable to go back to the previous
 * steps in the wizard
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class AdolescenceDoneCard extends AbstractCharGenWizPanel {
    /**
     * Builds the static display.
     *
     * This card does not allow a user to change anything, so this method
     * contains all the userful features of the card as static display
     * objects
     *
     * @param mChar The character, though it's not relavant
     */
    public void buildCard(MerpCharacter mChar) {
        Box myBox = new Box(BoxLayout.Y_AXIS);

        myBox.add(MerpLogoCanvas.getInstance());

        JTextArea heading = new JTextArea("Your birth adolescence is done");
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
            + "5) Determine role traits and background.\n"
            + "6) Develop your character's adolescence skills.\n"
            + "   ----> You are Here <-----\n"
            + "7) Develop your character's apprenticeship skills.\n"
            + "8) Outfit your character.\n"
            + "9) Total your character's penalties and bonuses.\n"
            + "10) Develop a persona for your character.\n",
            11, 30);

        inst.setEditable(false);
        inst.setBackground(new Color(206,206,206));
        inst.setLineWrap(true);
        inst.setWrapStyleWord(true);

        myBox.add(inst);
        add(myBox);

    }

    /**
     * See {@link com.chaosserver.merp.gui.swing.wizcards.AbstractCharGenWizPanel#getDescription()}
     */
    public String getDescription() {
        return "Congratulations!  You've finished the second portion of "
            + "character generation.  Once you continue from here you'll "
            + "lose the ability to go back and play with the original "
            + "options";
    }

    /**
     * When this card is the user has finalized on the stats, race and
     * the profession and therefore some processing can occur now that
     * those have been set in stone.  These include:
     * <ul>
     *   <li>Creation of the basic skill ranks from race</li>
     * </ul>
     */
    public void onNext(MerpCharacter mChar) {
        CategoryCache.getInstance(this).info("STARTING");
        CategoryCache.getInstance(this).info("Solidifying the BaseCharLanguageList into a CharLanguageList");
        CharLanguageFactory.createCharLanguageList(mChar);
        // CharSkillListFactory.addBaseRaceSkills(mChar);
    }

}