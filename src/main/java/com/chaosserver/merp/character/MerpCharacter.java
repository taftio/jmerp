package com.chaosserver.merp.character;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.logging.CategoryCache;
import com.chaosserver.merp.character.attribute.AttributeMap;
import com.chaosserver.merp.character.attribute.ExtendedAttribute;
import com.chaosserver.merp.character.language.CharLanguageList;
import com.chaosserver.merp.character.magic.CharSpellLists;
import com.chaosserver.merp.character.skill.CharSkillList;
import com.chaosserver.merp.character.stat.ICharStatList;
import com.chaosserver.merp.character.stat.ICharStatListFactory;
import com.chaosserver.merp.rules.magic.SpellCategory;
import com.chaosserver.merp.rules.magic.SpellList;
import com.chaosserver.merp.rules.magic.realm.Realm;
import com.chaosserver.merp.rules.profession.Profession;
import com.chaosserver.merp.rules.race.Race;
import com.chaosserver.merp.rules.restriction.IRestricted;
import com.chaosserver.merp.rules.restriction.RestrictionList;
import com.chaosserver.merp.rules.skill.modifier.ISkillModifiable;
import com.chaosserver.merp.rules.skill.modifier.SkillModifierList;
import com.chaosserver.merp.rules.specialabilities.SpecialAbility;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The main class that stores information about a unique character.
 *
 * Building this class up from a new character should be done by a wizard
 * or some other type of character creation facility.
 *
 * @author Jordan Reed
 * @version 1.0
 *
 * @see com.chaosserver.merp.gui.swing.CharacterGeneratorWizard
 */
public class MerpCharacter implements Cloneable, Serializable, PropertyChangeListener {
    /** Reference to the property change supporter. */
    protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /** Reference to the vetoable change supporter. */
    protected VetoableChangeSupport vetoes = new VetoableChangeSupport(this);

    /** Property of the stat list. */
    protected ICharStatList m_charStatList;

    /** Property of the character race. */
    protected Race m_race;

    /** Event label for the property. */
    public static String P_RACE = "MerpCharacter.Race";

    /** Property for the character profession. */
    protected Profession m_profession;

    /** Event label for the profession property. */
    public static String P_PROFESSION = "MerpCharacter.Profession";

    /** Property for the characater skill list. */
    protected CharSkillList m_charSkillList;

    /** Event label for the character skill list. */
    public static String P_CHAR_SKILL_LIST = "MerpCharacter.CharSkillList";

    /** Property for the character language list. */
    protected CharLanguageList charLanguageList;

    /** Event label for the character spell lists. */
    public static String P_CHAR_SPELL_LIST = "MerpCharacter.CharSpellList";

    /** Property for the character spell lists. */
    protected CharSpellLists charSpellLists;

    /** Property for the magical realm of the character. */
    protected Realm realm;

    /** Event label for the realm. */
    public static String P_REALM = "MerpCharacter.Realm";

    /** Event label for the special abilities collection. */
    public static String P_SPECIAL_ABILITIES = "MerpCharacter.SpecialAbilities";

    /** Collection of special abilities of the character. */
    protected Collection specialAbilities;

    /**
     * Property for the character level.
     */
    protected int level = 0;

    /**
     * This contains any extended attributes for a character.
     */
    protected AttributeMap attributeMap;

    /**
     * Creates an empty Merp Character.
     *
     * This constructor should only be used if you are going to use a factory
     * or wizard to fill in the character with the appropriate details
     */
    public MerpCharacter() {
        addPropertyChangeListener(this);
        specialAbilities = new ArrayList();
    }

    /**
     * Setter for statlist.
     *
     * @param charStatList the new statlist
     * @see #getCharStatList
     */
    protected void setCharStatList(ICharStatList charStatList) {
        m_charStatList = charStatList;
        m_charStatList.setCharacter(this);
    }

    /**
     * Getter for the statlist.
     *
     * @return the statlist of this character
     * @see #setCharStatList
     */
    public ICharStatList getCharStatList() {
        return m_charStatList;
    }

    /**
     * Uses a given factory to create the default statlist.
     * No stat list should currently exists for the character.  If one
     * does, you should get the current StatList and call the roll
     * method on it.
     *
     * @param statListFactory the factory to create the statlist
     */
    public void createCharStatList(ICharStatListFactory statListFactory) {
        Assertion.isTrue(getCharStatList() == null, "Cannot create a stat list when one already exists");

        setCharStatList(statListFactory.createCharStatList());
    }

    /**
     * getter for the current race.
     *
     * @return the current race
     * @see #setRace
     */
    public Race getRace() {
        return m_race;
    }

    /**
     * Setter for the current race.
     *
     * @param race The new race
     * @throws PropertyVetoException If one of the properties on this
     *         character restricts this race
     * @see #getRace
     */
    public void setRace(Race race) throws PropertyVetoException {
        Assertion.isTrue(race != null, "Race cannot be null");
        vetoes.fireVetoableChange(P_RACE, getRace(), race);
        // removeRestrictions(getRace());
        Race old_race = getRace();
        m_race = race;
        // addRestrictions(race);
        CategoryCache.getInstance(this).info("Setting race to: " + race);
        changes.firePropertyChange(P_RACE, old_race, getRace());
    }

    /**
     * Getter for the current level.
     *
     * @return The current level
     * @see #setLevel
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Setter for the level.
     *
     * @param level The current level
     * @see #getLevel
     */
    public void setLevel(int level) {
        CategoryCache.getInstance(this).info("Setting level: " + level);
        this.level = level;
    }

    /**
     * Setter for the extended attribute map for bean loader.
     *
     * @param attributeMap The AttributeMap
     * @see #getAttributeMap
     */
    public void setAttributeMap(AttributeMap attributeMap) {
        this.attributeMap = attributeMap;
    }

    /**
     * Getter for the extended attribute map for bean writer.
     *
     * @return the attribute map
     * @see #setAttributeMap
     */
    public AttributeMap getAttributeMap() {
        return this.attributeMap;
    }

    /**
     * Sets an extended attribute on the system.
     *
     * @param attribute The attribute to add
     * @see #getAttribute
     */
    public void putAttribute(ExtendedAttribute attribute) {
        CategoryCache.getInstance(this).info("Adding EA: " + attribute);
        this.attributeMap.put(attribute);
    }

    /**
     * Gets an extended attribute on the system.
     *
     * @param name The name of the extended attribute to get
     * @return the extended attribute or NULL if no attribute exists
     *         with this name
     * @see #putAttribute
     */
    public ExtendedAttribute getAttribute(String name) {
        return this.attributeMap.get(name);
    }

    /**
     * Checks to see if the race being passed in violates any of the
     * restrictions.  This is done by sending a Vetoable Change event
     * and checking for responses.
     *
     * @param race The race to check against
     * @return Is the race valid?
     */
    public boolean isValidRace(Race race) {
        boolean result = false;
        try {
            if(null != race) {
                vetoes.fireVetoableChange(P_RACE, getRace(), race);
                result = true;
            }
            else {
                result = false;
            }
        }
        catch (PropertyVetoException e) {
            result = false;
        }
        finally {
            return result;
        }
    }

    /**
     * Getter for profession.
     *
     * @return the character profession
     * @see #setProfession
     */
    public Profession getProfession() {
        return m_profession;
    }

    /**
     * Setter for profession.
     *
     * @param profession The new profession
     * @throws PropertyVetoException If one of the properties on this
     *         character restricts this profession
     * @see #getProfession
     */
    public void setProfession(Profession profession) throws PropertyVetoException {
        Assertion.isTrue(profession != null, "Cannot set a null profession");

        vetoes.fireVetoableChange(P_PROFESSION, getProfession(), profession);
        Profession old_profession = getProfession();
        m_profession = profession;
        CategoryCache.getInstance(this).info("Set profession to: " + m_profession);
        changes.firePropertyChange(P_PROFESSION, old_profession, getProfession());
    }

    /**
     * Checks to see if the given profession is valid.
     *
     * @param profession The profession to check
     * @return If it's valid
     */
    public boolean isValidProfession(Profession profession) {
        boolean result = false;
        try {
            if(null != profession) {
                vetoes.fireVetoableChange(P_PROFESSION, getProfession(), profession);
                result = true;
            }
            else {
                result = false;
            }
        }
        catch (PropertyVetoException e) {
            result = false;
        }
        finally {
            return result;
        }
    }

    /**
     * Checks to see if the given realm is valid.
     *
     * @param realm The realm to check
     * @return If it's valid
     */
    public boolean isValidRealm(Realm realm) {
        boolean result = false;
        try {
            if(null != realm) {
                vetoes.fireVetoableChange(P_REALM, getRealm(), realm);
                result = true;
            }
            else {
                result = false;
            }
        }
        catch (PropertyVetoException e) {
            result = false;
        }
        finally {
            return result;
        }
    }

    /**
     * Checks to see if a given spell list if valid to add
     * to a character.
     *
     * @param spellList The spell list to check
     * @return if it's valid
     */
    public boolean isValidSpellList(SpellList spellList) {
        Assertion.isNotNull(spellList, "Cannot validate against an empty spell list");
        boolean result = false;
        SpellCategory spellCategory = spellList.getSpellCategory();
        String spellCategoryType = spellCategory.getCategoryType();

        if(SpellCategory.PROFESSION_CATEGORY.equals(spellCategoryType)) {
            Assertion.isNotNull(getProfession(), "Cannot test profession restriction on null profession");
            if(getProfession().getName().equals(spellCategory.getName())) {
                result = true;
            }
        }
        else if(SpellCategory.REALM_CATEGORY.equals(spellCategoryType)) {
            Assertion.isNotNull(getRealm(), "Cannot test realm restriction on null profession");
            if(spellCategory.getName().indexOf(getRealm().getName())>=0) {
                result = true;
            }
        }
        else {
            Assertion.shouldNotReach("Reached a unknown category type");
        }
        CategoryCache.getInstance(this).debug("SpellList: " + spellList + ", SpellCategory: " + spellCategory + ", valid: " + result);
        return result;
    }


    /**
     * Getter for the charskilllist.
     *
     * @return the CharSkillList associated with this character
     * @see #setCharSkillList
     */
    public CharSkillList getCharSkillList() {
        return m_charSkillList;
    }

    /**
     * Setter for the CharSkillList for this character.
     *
     * @param charSkillList The new skillList
     * @see #getCharSkillList
     */
    public void setCharSkillList(CharSkillList charSkillList) {
        CharSkillList old_charSkillList = getCharSkillList();
        m_charSkillList = charSkillList;
        m_charSkillList.setCharacter(this);
        changes.firePropertyChange(P_CHAR_SKILL_LIST, old_charSkillList, getCharSkillList());
    }

    /**
     * Getter for the CharLanguageList.
     *
     * @return The CharLanguageList
     * @see #setCharLanguageList
     */
    public CharLanguageList getCharLanguageList() {
        return this.charLanguageList;
    }

    /**
     * Setter for the CharLanguageList.
     *
     * @param charLanguageList The charLanguageList
     * @see #getCharLanguageList
     */
    public void setCharLanguageList(CharLanguageList charLanguageList) {
        this.charLanguageList = charLanguageList;
    }

    /**
     * Getter for the CharSpellLists.
     *
     * @return the charspelllists for this character
     * @see #setCharSpellLists
     */
    public CharSpellLists getCharSpellLists() {
        return this.charSpellLists;
    }

    /**
     * Setter for the CharSpellLists.
     *
     * @param charSpellLists The CharSpellLists
     * @see #getCharSpellLists
     */
    public void setCharSpellLists(CharSpellLists charSpellLists) {
        this.charSpellLists = charSpellLists;
    }

    /**
     * Getter for the magic realm.
     *
     * @return The realm of this character
     * @see #setRealm
     */
    public Realm getRealm() {
        return this.realm;
    }

    /**
     * Setter for the magic realm.
     *
     * @param realm The new realm for this character
     * @throws PropertyVetoException If one of the properties on this
     *         character restricts this realm
     * @see #getRealm
     */
    public void setRealm(Realm realm) throws PropertyVetoException {
        vetoes.fireVetoableChange(P_REALM, getRealm(), realm);
        Realm old_realm = getRealm();
        this.realm = realm;
        CategoryCache.getInstance(this).info("Setting realm: " + realm);
        changes.firePropertyChange(P_REALM, old_realm, getRealm());
    }

    /**
     * Getter for the special abilities of a character.
     *
     * @return the special abilities of the character
     */
    public Collection getSpecialAbilities() {
        return this.specialAbilities;
    }

    /**
     * Adds a special ability onto the character.
     *
     * @param specialAbility The ability to add
     */
    public void addSpecialAbility(SpecialAbility specialAbility) {
        addRestrictions(specialAbility);
        addSkillModifiers(specialAbility);
        specialAbilities.add(specialAbility);
        specialAbility.apply(this);
        changes.firePropertyChange(P_SPECIAL_ABILITIES, null, getSpecialAbilities());
    }


    /**
     * Adds all of the restrictions of this object to this character.
     *
     * Takes in a object and checks if it has a restriction list.
     * If it does it will add all of the restrictions in the list
     * onto the character.  These retrictions should register themselves
     * as VetoableChangeListeners and block the character from
     * getting settings that they restrict.
     *
     * @param resObj An object that may restrict the character
     *
     * @see com.chaosserver.merp.rules.restriction.IRestricted
     * @see #removeRestrictions
     */
    protected void addRestrictions(Object resObj) {
        if(resObj != null) {
            if(resObj instanceof IRestricted) {
                RestrictionList ResList = ((IRestricted)resObj).getRestrictionList();
                if(ResList != null) {
                    ResList.setChar(this);
                }
            }
        }
    }

    /**
     * Removes all of the restrictions of this object to this character.
     *
     * Takes in an object and checks if it has a restriction list.
     * If it does, it will remove all the restrictions in the list.
     *
     * @param resObj An object that may restrict the character
     *
     * @see com.chaosserver.merp.rules.restriction.IRestricted
     * @see #addRestrictions
     */
    protected void removeRestrictions(Object resObj) {
        if(resObj != null) {
            if(resObj instanceof IRestricted) {
                RestrictionList ResList = ((IRestricted)resObj).getRestrictionList();
                if(ResList != null) {
                    ResList.setChar(null);
                }
            }
        }
    }

    /**
     * Adds all skill modifiers of an object onto the character.
     *
     * @param skillModifierObj Object to get skill modifiers from for this character
     * @see #removeSkillModifiers
     */
    protected void addSkillModifiers(Object skillModifierObj) {
        if(skillModifierObj != null) {
            if(skillModifierObj instanceof ISkillModifiable) {
                Assertion.isNotNull(getCharSkillList(), "Cannot add skill "
                    + "modifiers before a character has a skill list.  This"
                    + "should never happen when using a factory to create these");

                CategoryCache.getInstance(this).debug("Got a skill modifier so applying: " + skillModifierObj);
                SkillModifierList skillModifierList = ((ISkillModifiable) skillModifierObj).getSkillModifierList();

                getCharSkillList().addSkillModifiers(skillModifierList);
            }
        }
    }

    /**
     * Removes all skill modifiers of an object from the character.
     *
     * @param skillModifierObj Object to remove skill modifiers from for this character
     * @see #addSkillModifiers
     */
     protected void removeSkillModifiers(Object skillModifierObj) {
        if(skillModifierObj != null) {
            if(skillModifierObj instanceof ISkillModifiable) {
                Assertion.isNotNull(getCharSkillList(), "Cannot add skill "
                    + "modifiers before a character has a skill list.  This"
                    + "should never happen when using a factory to create these");

                SkillModifierList skillModifierList = ((ISkillModifiable) skillModifierObj).getSkillModifierList();

                getCharSkillList().removeSkillModifiers(skillModifierList);
            }
        }
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

    /**
     * Add a VetoableChangeListener to the listener list. The listener is registered for all properties.
     *
     * @param v The VetoableChangeListener to be added
     * @see #removeVetoableChangeListener
     */
    public void addVetoableChangeListener(VetoableChangeListener v) {
      vetoes.addVetoableChangeListener(v);
    }

    /**
     * Remove a VetoableChangeListener from the listener list. This removes a
     * VetoableChangeListener that was registered for all properties.
     *
     * @param v The VetoableChangeListener to be removed
     * @see #addVetoableChangeListener
     */
    public void removeVetoableChangeListener(VetoableChangeListener v) {
      vetoes.removeVetoableChangeListener(v);
    }

    // Inherits from interface
    public void propertyChange(PropertyChangeEvent evt) {
        removeRestrictions(evt.getOldValue());
        removeSkillModifiers(evt.getOldValue());
        addRestrictions(evt.getNewValue());
        addSkillModifiers(evt.getNewValue());
    }
}