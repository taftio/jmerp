package com.chaosserver.merp.gui.swing;

import com.chaosserver.logging.CategoryCache;
import com.chaosserver.assertion.Assertion;
import com.chaosserver.merp.character.CharacterFactory;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.data.BootStrap;
import com.chaosserver.merp.data.FileNameGetter;
import com.chaosserver.merp.gui.swing.wizcards.AbstractCharGenWizPanel;
import com.chaosserver.merp.gui.swing.wizcards.AdolescenceDoneCard;
import com.chaosserver.merp.gui.swing.wizcards.AdolesenceSpellSelectorCard;
import com.chaosserver.merp.gui.swing.wizcards.BackgroundPointsCard;
import com.chaosserver.merp.gui.swing.wizcards.BaseLanguageCard;
import com.chaosserver.merp.gui.swing.wizcards.BirthDoneCard;
import com.chaosserver.merp.gui.swing.wizcards.FullCharacterDisplayCard;
import com.chaosserver.merp.gui.swing.wizcards.IntroCard;
import com.chaosserver.merp.gui.swing.wizcards.ProfessionSelectorCard;
import com.chaosserver.merp.gui.swing.wizcards.RaceSelectorCard;
import com.chaosserver.merp.gui.swing.wizcards.RealmSelectorCard;
import com.chaosserver.merp.gui.swing.wizcards.StatRollerPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import org.apache.log4j.PropertyConfigurator;

public class CharacterGeneratorWizard implements ActionListener {
    public static String S_NEXT = "CharacterGeneratorWizard.next";
    public static String S_BACK = "CharacterGeneratorWizard.back";
    public static String S_DONE = "CharacterGeneratorWizard.done";

    private ArrayList charGenCards = new ArrayList();
    private ListIterator cardList;
    private AbstractCharGenWizPanel currCard = null;
    private MerpCharacter merpChar = CharacterFactory.createCharacter();

    private JTextArea descText;
    private JPanel cardPanel;
    private JButton nextButton;
    private JButton backButton;

    /** What is the current "set" of cards we're on */
    private int m_currCard = 1;
    private int m_maxCard = 3;

    /** Should the system exit when done is clicked? */
    boolean m_exitOnComplete = true;

    /** Is this a previous or next? */
    boolean m_previous = false;

    /**
     * This is the main constructor.  It will build up the frame and display it.
     */
    public CharacterGeneratorWizard() {
        CategoryCache.getInstance(this).info("ENTER");
        // Start the bootstraping thread as the very first thing in the system
        BootStrap.backgroundThread();

        // Create all the cards
        addCards(m_currCard);

        //Create the top-level container and add contents to it.
        JFrame frame = new JFrame("Character Generator v1.0");

        frame.getContentPane().add(buildMainPanel(), BorderLayout.CENTER);
        // frame.getContentPane().add(new JButton("Test"), BorderLayout.CENTER);

        //Finish setting up the frame, and show it.
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        nextCard();
        ImageIcon ii = new ImageIcon(FileNameGetter.getResourceURL(FileNameGetter.IMAGE_DICE_ICON));
        frame.setIconImage(ii.getImage());
        frame.setLocation(100,100);
        frame.setSize(800,600);
        //frame.pack();
        frame.setVisible(true);
        CategoryCache.getInstance(this).info("EXIT");
    }

    /**
     * This version of the constructor allows you to tell the wizard not to
     * close when you're done
     *
     * @param exitOnComplete Should the program exit when done is clicked
     */
    public CharacterGeneratorWizard(boolean exitOnComplete) {
        this();
        m_exitOnComplete = exitOnComplete;
    }

    /**
     * You know the good old main function.  Creates a new version of the
     * object.
     */
    public static void main(String[] args) {
        PropertyConfigurator.configure(FileNameGetter.PROPERTIES_LOGGING_CONFIG);

        try {
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) { }

        // CharacterGenerator cg = new CharacterGeneratorWizard();
        CharacterGeneratorWizard cgw = new CharacterGeneratorWizard();
    }

    /**
     * Adds all of the needed cards into the object.
     * Cards will be displayed here in the order that they are added.
     */
    private void addCards(int cardSet) {
        charGenCards = new ArrayList(5);
        currCard = null;
        CategoryCache.getInstance(this).info("Loading Set: " + cardSet);
        // Set 1 is the "Birth" set.  Everything that defines a character at birth
        if(cardSet == 1) {
            charGenCards.add(new IntroCard());
            charGenCards.add(new StatRollerPanel());
            charGenCards.add(new RaceSelectorCard());
            charGenCards.add(new ProfessionSelectorCard());
            charGenCards.add(new RealmSelectorCard());
            charGenCards.add(new BirthDoneCard());
        }
        // Set 2 is the adolesence set.  This is all the 0th level stuff
        else if(cardSet == 2) {
            charGenCards.add(new BaseLanguageCard());
            charGenCards.add(new AdolesenceSpellSelectorCard());
            charGenCards.add(new BackgroundPointsCard());
            charGenCards.add(new AdolescenceDoneCard());
        }
        // Set 3 is the aprenticeship set.  This is the 1st level stuff
        else if(cardSet == 3) {
            charGenCards.add(new FullCharacterDisplayCard());
        }
        cardList = charGenCards.listIterator();
    }

    /**
     * This builds the main panel for the wizard and returns it
     *
     * @return The main panel
     */
    private JPanel buildMainPanel() {
        backButton = new JButton("<< Back");
        backButton.setActionCommand(S_BACK);
        backButton.setEnabled(false);
        backButton.addActionListener(this);

        nextButton = new JButton("Next >>");
        nextButton.setActionCommand(S_NEXT);
        nextButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        BoxLayout bl = new BoxLayout(buttonPanel, BoxLayout.X_AXIS);
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        JPanel sPanel = new JPanel();
        sPanel.setLayout(new BorderLayout());
        sPanel.add(buildCardPanel(), BorderLayout.CENTER);
        sPanel.add(buttonPanel, BorderLayout.SOUTH);
        return sPanel;

    }

    /**
     * This builds up the default card panel and returns it.
     *
     * @return the card panel
     */
    private JPanel buildCardPanel() {
        descText = new JTextArea(5, 80);
        descText.setEditable(false);
        descText.setLineWrap(true);
        descText.setWrapStyleWord(true);

        cardPanel = new JPanel();
        cardPanel.setLayout(new BorderLayout());
        cardPanel.add(descText, BorderLayout.SOUTH);
        return cardPanel;
    }

    /**
     * Moves the layout back to the previous card
     */
    private void prevCard() {
        CategoryCache.getInstance(this).info("ENTER");
        Assertion.isNotNull(cardList, "Cardlist cannot be null on previous card call");
        AbstractCharGenWizPanel discard = null;

        CategoryCache.getInstance(this).debug("Moving backward two cards");
        if(cardList.hasPrevious()) {
            discard = (AbstractCharGenWizPanel) cardList.previous();
        }

        if(cardList.hasPrevious()) {
            discard = (AbstractCharGenWizPanel) cardList.previous();
        }

        while(discard.isSkippable(merpChar)) {
            discard = (AbstractCharGenWizPanel) cardList.previous();
        }

        m_previous = true;
        nextCard();
        CategoryCache.getInstance(this).info("EXIT");
    }

    /**
     * Moves the layout to the next card
     */
    private void nextCard() {
        CategoryCache.getInstance(this).info("ENTER");
        Assertion.isNotNull(cardList, "Cardlist cannot be null on next card call");

        // The is currently a card being displayed
        if(currCard != null) {
            // Trigger the action on the card
            if(m_previous) {
                currCard.onPrev();
            } else {
                currCard.onNext(merpChar);
            }
            m_previous = false;
            CategoryCache.getInstance(this).debug("Removing the current card from the display: " + currCard.getClass().getName());
            cardPanel.remove(currCard);
        }

        if(cardList.hasPrevious()) {
            backButton.setEnabled(true);
        } else {
            backButton.setEnabled(false);
        }

        if(cardList.hasNext()) {
            do {
                currCard = (AbstractCharGenWizPanel) cardList.next();
            } while(currCard.isSkippable(merpChar));

            CategoryCache.getInstance(this).debug("Add in the next card to the display: " + currCard.getClass().getName());
            descText.setText(currCard.getDescription());
            currCard.build(merpChar);
            cardPanel.add(currCard, BorderLayout.CENTER);
            cardPanel.repaint();

            CategoryCache.getInstance(this).debug("Modifying the button text appropriately");
            if(cardList.hasNext() || m_currCard < m_maxCard) {
                nextButton.setText("Next >>");
                nextButton.setActionCommand(S_NEXT);
            } else {
                nextButton.setText("Done");
                nextButton.setActionCommand(S_DONE);
            }
        }
        else if(m_currCard < m_maxCard) {
            m_currCard++;
            addCards(m_currCard);
            nextCard();
        }
        CategoryCache.getInstance(this).info("EXIT");
    }

    /**
     * Watched for actions performed.  The only actions that are watched
     * for the two buttons on the screen
     *
     * @param evt The action event
     */
    public void actionPerformed(ActionEvent evt) {
        CategoryCache.getInstance(this).debug("ENTER");
        if(evt.getActionCommand().equals(S_NEXT)) {
            CategoryCache.getInstance(this).debug("S_NEXT action caught");
            nextCard();
        } else if (evt.getActionCommand().equals(S_BACK)) {
            CategoryCache.getInstance(this).debug("S_BACK action caught");
            prevCard();
        } else if (evt.getActionCommand().equals(S_DONE)) {
            CategoryCache.getInstance(this).debug("S_DONE action caught");
            if(m_exitOnComplete) {
                System.exit(0);
            }
        }
    }
}

