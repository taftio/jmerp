package com.chaosserver.merp.rules.race;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.merp.rules.language.BaseLanguageList;
import com.chaosserver.merp.rules.restriction.IRestricted;
import com.chaosserver.merp.rules.restriction.RestrictionList;
import com.chaosserver.merp.rules.stat.Stat;

/**
 * This class contains information on a single race in the system.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class Race implements IRestricted {
    /** This is the name of the race. */
    private String m_name;

    /** This is the map of stat bonuses for the race. */
    protected Map m_statBonusMap;

    /** This is the list of stat restriction. */
    private RestrictionList m_RestrictionList;

    /** This is the list of base skills. */
    private ArrayList m_baseSkillList;

    /** The list of the base languages a character knows and can learn. */
    protected BaseLanguageList baseLanguageList;

    /** This is the percentage change of learning a spell list. */
    private int m_chanceOfSpellList = 0;

    /** This is the number of additional language ranks starting. */
    private int m_languageRanks = 0;

    /** This is the number of backgroun options. */
    private int m_backgroundOptions;

    /**
     * Empty Constructor.
     */
    public Race() {
    }

    /**
     * This is a friendly constructor for race.  This class should
     * only be created by the RaceList singleton object.
     *
     * @author Jordan Reed
     * @version 1.0
     */
    Race(String name, RestrictionList RestrictionList) {
        Assertion.isTrue(name != null, "Race requires a non null name");
        setName(name);
        setRestrictionList(RestrictionList);
    }

    /**
     * Getter method of the stat name.
     *
     * @return the name of the stat
     * @see #setName
     */
    public String getName() {
        return m_name;
    }


    /**
     * Setter method of the stat name.
     *
     * @param name The new stat name
     * @see #getName
     */
    public void setName(String name) {
        m_name = name;
    }

    /**
     * Setter for the Chance of learning a spell list.
     *
     * @param chance The change of learning a spell list
     * @see #getChangeOfSpellList
     */
    public void setChanceOfSpellList(int chance) {
        m_chanceOfSpellList = chance;
    }

    /**
     * Getter for the change of learning a spell list.
     *
     * @return The chance of learning a spell list
     * @see #setChanceOfSpellList
     */
    public int getChangeOfSpellList() {
        return m_chanceOfSpellList;
    }

    /**
     * Setter for the language ranks.
     *
     * @param languageRanks The language ranks
     * @see #getLanguageRanks
     */
    public void setLanguageRanks(int languageRanks) {
        m_languageRanks = languageRanks;
    }

    /**
     * Getter for the language ranks.
     *
     * @return the langauge ranks
     * @see #setLanguageRanks
     */
    public int getLanguageRanks() {
        return m_languageRanks;
    }

    /**
     * Getter method for the stat res list.
     *
     * @return The stat restriction list
     * @see #setRestrictionList
     */
    public RestrictionList getRestrictionList() {
        return m_RestrictionList;
    }

    /**
     * Setter method for the stat res list.
     *
     * @param restrictionList the new stat res list
     * @see #getRestrictionList
     */
    public void setRestrictionList(RestrictionList restrictionList) {
        m_RestrictionList = restrictionList;
    }

    /**
     * Getter for then number of background options.
     *
     * @return The number of background options
     * @see #setBackgroundOptions
     */
    public int getBackgroundOptions() {
        return m_backgroundOptions;
    }

    /**
     * Setter for the number of background options.
     *
     * @param backgroundOptions The number of background options
     * @see #getBackgroundOptions
     */
    public void setBackgroundOptions(int backgroundOptions) {
        m_backgroundOptions = backgroundOptions;
    }

    /**
     * Setter method for the base skill list.
     *
     * @param baseSkillList The new base list
     * @see #getBaseSkillList
     */
    public void setBaseSkillList(ArrayList baseSkillList) {
        m_baseSkillList = baseSkillList;
    }

    /**
     * Getter for the base skill list.
     *
     * @return the base skill list for this race
     * @see #setBaseSkillList
     */
    public List getBaseSkillList() {
        return m_baseSkillList;
    }

    /**
     * Setter method for the base language list.
     *
     * @param baseLanguageList the base language list
     * @see #getBaseLanguageList
     */
    public void setBaseLanguageList(BaseLanguageList baseLanguageList) {
        this.baseLanguageList = baseLanguageList;
    }

    /**
     * Getter method for the base language list.
     *
     * @return The base language list
     * @see #setBaseLanguageList
     */
    public BaseLanguageList getBaseLanguageList() {
        return this.baseLanguageList;
    }

    /**
     * Gets the racial bonus for a given stat.
     *
     * @param stat The stat to get the racial bonus for
     * @return stat bonus for the race
     */
    public int getRaceStatBonus(Stat stat) {
        Map map = getStatBonusMap();
        if(map != null) {
            if(map.get(stat) != null) {
                Integer bonus = (Integer) map.get(stat);
                return bonus.intValue();
            }
        }
        return 0;
    }

    /**
     * Getter for the stat bonus based on race.
     *
     * @return the stat bonus map
     * @see #setStatBonusMap
     */
     public Map getStatBonusMap() {
         return m_statBonusMap;
     }

     /**
      * Setter for the stat bonus based on race.
      *
      * @param statBonusMap The stat bonus map
      * @see #getStatBonusMap
      */
     public void setStatBonusMap(Map statBonusMap) {
         m_statBonusMap = statBonusMap;
     }

    /**
     * Converts the object to string representation.
     *
     * @return a string representation of the object
     */
     public String toString() {
        return getName();
    }

}