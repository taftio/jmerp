package com.chaosserver.merp.character.skill;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.exception.NotFoundX;
import com.chaosserver.logging.CategoryCache;
import com.chaosserver.merp.character.MerpCharacter;
import com.chaosserver.merp.rules.skill.Skill;
import com.chaosserver.merp.rules.skill.modifier.ISkillModifier;
import com.chaosserver.merp.rules.skill.modifier.SkillModifierList;

/**
 * A list of all of the <code>CharSkill</code>s that a character has.
 * <p>
 * Each skill known by the character is represented in this list.
 * Each element must be a <code>ICharSkill</code> object, by the
 * exact implementation of the object will vary.  Certain skills,
 * like Body Development or Base Spell OB must have their own
 * special implementation.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class CharSkillList {
    /** Collection holding the character skills. */
    private Collection m_charSkillList;

    /** Reference to the property change supporter. */
    protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /** Fired when the skill list is modified. */
    public static String P_CHAR_SKILL_LIST = "com.chaosserver.merp.character.skill.CharSkillList.CharSkillList";

    /**
     * This is the skill modifier list.  It holds references to category
     * based skill modifiers.
     */
    private SkillModifierList m_skillModifierList = new SkillModifierList();

    /** This is a refernce to the character this list applies to. */
    protected MerpCharacter m_character = null;

    /**
     * Main constructor initializes to nothing.
     */
    public CharSkillList() {
        m_charSkillList = new TreeSet();
    }

    /**
     * Sets the character to all the skills in the list.
     * <p>
     * Skills may need to know the character to calculate
     * various bonus appropriately.  Especially if there
     * character has special abilities or items that will
     * affect the skill.
     * </p>
     *
     * @param character The character these skills apply to
     * @see #getCharacter
     */
    public void setCharacter(MerpCharacter character) {
        CharSkillIterator iterator;
        this.m_character = character;
        iterator = iterator();
        while(iterator.hasNext()) {
            iterator.next().setCharacter(getCharacter());
        }
    }

    /**
     * Returns the character these skills apply to.
     *
     * @return The character these skills apply to
     * @see #setCharacter
     */
    public MerpCharacter getCharacter() {
        return m_character;
    }

    /**
     * Adds a new ICharSkill object to the list.
     * <p>
     * The skills <code>setCharacter</code> method
     * will be called associating it with the correct character.
     * </p>
     *
     * @param charSkill Skill to be added to this character
     */
    public void add(ICharSkill charSkill) {
        m_charSkillList.add(charSkill);
        charSkill.setCharSkillList(this);
        charSkill.setCharacter(getCharacter());
        changes.firePropertyChange(P_CHAR_SKILL_LIST, null, m_charSkillList);
    }

    /**
     * Gets an iterator for the list.
     *
     * @return The iterator
     */
    public CharSkillIterator iterator() {
        return new CharSkillIterator(this);
    }

    /**
     * Gets the internal collection holding the skills.
     * <p>
     * Only the iterator should ever need to make
     * this call.
     * </p>
     *
     * @return The internal collection
     */
    Collection getCharSkillList() {
        return m_charSkillList;
    }

    /**
     * Returns the complete skill modifier list.
     *
     * @return the skill modifier list
     */
    public SkillModifierList getSkillModifierList() {
        return m_skillModifierList;
    }

    /**
     * Returns a skill modifier list filtered by BonusType.
     *
     * @param bonusType The type of bonus to include in the list
     * @return Skill modifier list containing only the skills with the given
     *         bonus type
     */
    public SkillModifierList getSkillModifierList(int bonusType) {
        CategoryCache.getInstance(this).debug("Getting a skill modifier list for: " + bonusType);
        SkillModifierList skillModifierList = new SkillModifierList();

        Iterator skillModIter = getSkillModifierList().iterator();
        while(skillModIter.hasNext()) {
            ISkillModifier currSkillMod = (ISkillModifier) skillModIter.next();
            if(bonusType == currSkillMod.getBonusType()) {
                CategoryCache.getInstance(this).debug("Adding in: " + currSkillMod);
                skillModifierList.add(currSkillMod);
            }
        }

        return skillModifierList;
    }

    /**
     * Returns true if this collection contains the specified element.
     *
     * @param charSkill The character skill to see if this list contains
     * @return if the collection contains the element
     */
    public boolean contains(ICharSkill charSkill) {
        return m_charSkillList.contains(charSkill);
    }

    /**
     * Returns true if this collection contains the specified element.
     *
     * @param skill The skill to check if this character has a skill list for
     * @return if the collection contains the element
     */
    public boolean contains(Skill skill) {
        boolean result = true;

        try {
            CharSkillFinder.findBySkill(skill, this);
        }
        catch (NotFoundX e) {
            result = false;
        }

        return result;
    }

    /**
     * Retreives the profession bonus for a particular skill.  This is
     * done by first getting all skill modifiers that apply to this
     * skill and adding in their bonus.
     *
     * @param charSkill The skill to get the profession bonus on
     * @return The profession bonus for this skill
     */
    public int getProfessionBonus(ICharSkill charSkill) {
        int professionBonus = 0;

        // Get the skill modifier list for profession bonus
        SkillModifierList professionSkillModifierList =
            getSkillModifierList(ISkillModifier.PROFESSION_BONUS);

        // Iterator through and total in any bonus that is relavant
        // to this skill
        Iterator skillModIter = professionSkillModifierList.iterator();
        while(skillModIter.hasNext()) {
            ISkillModifier currModifier = (ISkillModifier) skillModIter.next();
            if(currModifier.isRelevant(charSkill)) {
                professionBonus += currModifier.getValue() * (getCharacter().getLevel());
            }
        }
        return professionBonus;
    }

    /**
     * Retreives the bonus for a skill for a given bonus type.
     * <p>
     * This is done by first getting all skill modifiers that apply to this
     * skill and adding it to the total bonus.
     * </p>
     *
     * @param charSkill The skill to get the bonus for
     * @param bonusType The type of bonus to get.
     * @return the bonus for the skill
     */
    public int getBonus(ICharSkill charSkill, int bonusType) {
        int bonus = 0;

        // Get the skill modifier list for profession bonus
        SkillModifierList professionSkillModifierList =
            getSkillModifierList(bonusType);

        // Iterator through and total in any bonus that is relavant
        // to this skill
        Iterator skillModIter = professionSkillModifierList.iterator();
        while(skillModIter.hasNext()) {
            ISkillModifier currModifier = (ISkillModifier) skillModIter.next();
            if(currModifier.isRelevant(charSkill)) {
                bonus += currModifier.getValue();
            }
        }
        return bonus;
    }


    /**
     * Adds all of the skill modifiers in a list to the character.
     * <p>
     * This method will be invoked by the character object each
     * time something with the <code>ISkillModifiable</code> interface
     * is added to it.
     * </p>
     *
     * @param skillModifierList The list of skill modifiers to add to
     *        this character
     *
     * @see com.chaosserver.merp.rules.skill.modifier.ISkillModifiable
     */
    public void addSkillModifiers(SkillModifierList skillModifierList) {
        m_skillModifierList.addAll(skillModifierList);

        // Cycle through all the modifiers and fire property changes
        Iterator skillModifierListIterator = skillModifierList.iterator();
        while(skillModifierListIterator.hasNext()) {
            ISkillModifier skillModifier = (ISkillModifier) skillModifierListIterator.next();
            CharSkillIterator charSkillIterator = iterator();
            while(charSkillIterator.hasNext()) {
                ICharSkill charSkill = charSkillIterator.next();
                if(skillModifier.isRelevant(charSkill)) {
                    charSkill.fireBonusChange(skillModifier.getBonusType());
                }
            }
        }
    }

    /**
     * Removes all of the skill modifiers in the given list from the character.
     * <p>
     * This method will be invoked by the character object each
     * time something with the <code>ISkillModifiable</code> interface
     * is added to it.
     * </p>
     *
     * @param skillModifierList The list of skill modifiers to add to
     *        this character
     *
     * @see com.chaosserver.merp.rules.skill.modifier.ISkillModifiable
     */
    public void removeSkillModifiers(SkillModifierList skillModifierList) {
        m_skillModifierList.removeAll(skillModifierList);
    }

    /**
     * Add a PropertyChangeListener to the listener list. The listener is registered for all properties.
     *
     * @param l The PropertyChangeListener to be added
     * @see #removePropertyChangeListener
     */
    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    /**
     * Remove a PropertyChangeListener from the listener list. This removes a PropertyChangeListener
     * that was registered for all properties.
     *
     * @param l The PropertyChangeListener to be removed
     * @see #addPropertyChangeListener
     */
    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }

}