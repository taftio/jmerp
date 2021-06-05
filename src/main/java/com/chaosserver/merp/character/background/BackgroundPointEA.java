package com.chaosserver.merp.character.background;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.merp.character.attribute.ExtendedAttributeBase;
import com.chaosserver.merp.character.stat.ICharStat;
import com.chaosserver.merp.rules.specialabilities.SpecialAbility;

import java.util.Set;
import java.util.HashSet;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Extended attribute to hold information about background points
 * within a character.
 * <p>
 * This attribute holds all state information relating to a background
 * points in a character.  This includes the number of background points
 * left to spend and what state the spending is in.
 * </p>
 * <p>
 * Since it is possible to spend a single background point and increase
 * three different stats, state information about that expenditure is
 * handle in this attribute.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class BackgroundPointEA extends ExtendedAttributeBase {
    /** Reference to the property change supporter. */
    protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /** Maximum number of a points a character can spend on background points. */
    protected int maxPoints;

    /** Current number of points a character has left to spend. */
    protected int currentPoints;

    /** Defines if a character is adding one point to three different stats. */
    protected boolean addingToStats;

    /** Holds how many points left a character has to add to stats. */
    protected int pointsToAddToStats;

    /** Holds the collection of stats that have been added to. */
    protected Set statsAddedTo = new HashSet();

    /** Holds an incomplete special ability if the character has one. */
    protected SpecialAbility specialAbility;

    /**
     * Holds the number of stats a character can add a single point to when spending
     * a background point to add to multiple stats.
     */
    public int STATS_TO_ADD_ONE_POINT_TO = 3;

    /** Fired when the number of background points changes. */
    public static String P_CURRENT_POINTS = "EA.CurrentPoints";

    /** Fired when the last background point is spent. */
    public static String NO_POINTS_LEFT = "ExtendedAttributeBase.NoPointsLeft";

    /** Fired when a character starts adding single points to stats. */
    public static String ADDING_SINGLE_STAT = "EA.AddingSingleStat";

    /** Fired when a character finished adding single points to stats. */
    public static String DONE_ADDING_SINGLE_STAT = "EA.DoneAddingSingleStat";

    /**
     * Empty constructor for bean loader.
     *
     * @see com.chaosserver.data.JavaBeanLoader
     */
    public BackgroundPointEA() {
    }

    /**
     * Constructor to create a valid attribute.
     *
     * @param name The name the attribute will be stored as in the character
     * @param expiration The level at which the attribute will expire
     * @param maxPoints The maximum number of background points a character has
     */
    public BackgroundPointEA(String name, int expiration, int maxPoints) {
        this(name, expiration, maxPoints, maxPoints);
    }

    /**
     * Constructor to create a valid attribute.
     *
     * @param name The name the attribute will be stored as in the character
     * @param expiration The level at which the attribute will expire
     * @param maxPoints The maximum number of background points a character has
     * @param currentPoints The current points the character has left to spend
     */
    public BackgroundPointEA(String name, int expiration, int maxPoints, int currentPoints) {
        setName(name);
        setExpiration(expiration);
        setMaxPoints(maxPoints);
        setCurrentPoints(currentPoints);
    }

    /**
     * Setter for maxPoints property.
     *
     * @param maxPoints Value of maxPoints
     * @see #getMaxPoints
     */
    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    /**
     * Getter for maxPoints property.
     *
     * @return Value of maxPoints
     * @see #setMaxPoints
     */
    public int getMaxPoints() {
        return this.maxPoints;
    }

    /**
     * Setter for currentPoints property.
     *
     * @param currentPoints Value of currentPoints
     * @see #getCurrentPoints
     */
    public void setCurrentPoints(int currentPoints) {
        int old_current_points = getCurrentPoints();
        this.currentPoints = currentPoints;
        changes.firePropertyChange(P_CURRENT_POINTS, old_current_points, getCurrentPoints());
        if(getCurrentPoints() <= 0) {
            changes.firePropertyChange(NO_POINTS_LEFT, old_current_points, getCurrentPoints());
        }
    }

    /**
     * Getter for currentPoints property.
     *
     * @return Value of currentPoints
     * @see #setCurrentPoints
     */
    public int getCurrentPoints() {
        return this.currentPoints;
    }

    /**
     * Spends a background point on the character.
     */
    public void spendPoint() {
        Assertion.isTrue(getCurrentPoints() > 0, "Must have positive points to spend one");
        setCurrentPoints(getCurrentPoints()-1);
    }


    /**
     * Setter for addingToStats property.
     *
     * @param addingToStats Value of addingToStats
     * @see #isAddingToStats
     */
    public void setAddingToStats(boolean addingToStats) {
        this.addingToStats = addingToStats;
    }

    /**
     * Getter for addingToStats property.
     *
     * @return Value of addingToStats
     * @see #setAddingToStats
     */
    public boolean isAddingToStats() {
        return this.addingToStats;
    }

    /**
     * Setter for pointsToAddToStats property.
     *
     * @param pointsToAddToStats Value of pointsToAddToStats
     * @see #getPointsToAddToStats
     */
    public void setPointsToAddToStats(int pointsToAddToStats) {
        this.pointsToAddToStats = pointsToAddToStats;
    }

    /**
     * Getter for pointsToAddToStats property.
     *
     * @return Value of pointsToAddToStats
     * @see #setPointsToAddToStats
     */
    public int getPointsToAddToStats() {
        return this.pointsToAddToStats;
    }

    /**
     * Enters the EA in the state where a character is using a background point to
     * add a single point to three different stats.
     * <p>
     * This does not spend a background point, it mere flips into a certain state.
     * The process calling this method should spend the background point if
     * appropriate.
     * </p>
     */
    public void beginAddingToStats() {
        Assertion.isFalse(isAddingToStats(), "Cannot begin adding to stats while already adding");
        int old_pointsToAddToStats = getPointsToAddToStats();
        setAddingToStats(true);
        setPointsToAddToStats(STATS_TO_ADD_ONE_POINT_TO);
        statsAddedTo.clear();
        changes.firePropertyChange(ADDING_SINGLE_STAT, old_pointsToAddToStats, getPointsToAddToStats());
    }


    /**
     * Spends a stat point for adding one point to three different stats.
     *
     * @param charStat The stat to add a point to
     */
    public void spendStatPoint(ICharStat charStat) {
        Assertion.isFalse(statsAddedTo.contains(charStat), "Cannot spend a second point on the same stat");
        Assertion.isTrue(isAddingToStats(), "Character must be in the process of adding to stats");

        int old_pointsToAddToStats = getPointsToAddToStats();
        setPointsToAddToStats(pointsToAddToStats - 1);
        statsAddedTo.add(charStat);
        if(pointsToAddToStats <= 0) {
            setAddingToStats(false);
            statsAddedTo.clear();
            changes.firePropertyChange(DONE_ADDING_SINGLE_STAT, old_pointsToAddToStats, getPointsToAddToStats());
        }
    }

    /**
     * Checks if the character is in the middle of adding to three stats and this is one of them.
     *
     * @param charStat Stat to see if the character has already added to during this
     *                 transaction
     * @return If the character has already added to this stat in the middle of adding to three
     */
    public boolean isAddingTo(ICharStat charStat) {
        return statsAddedTo.contains(charStat);
    }

    /**
     * Returns the special ability currently held in the EA.
     * <p>
     * This will return a null if no special ability is currently found.
     * </p>
     *
     * @return Special ability value
     * @see #setSpecialAbility
     */
    public SpecialAbility getSpecialAbility() {
        return this.specialAbility;
    }

    /**
     * Sets the special ability on the EA.
     * <p>
     * This ability has not been applied to the character.  Generally
     * this is done when character gets a special ability that requires
     * more values to be set on it before it is ready to be added to
     * the character.
     * </p>
     *
     * @param specialAbility The ability to add to the EA
     * @see #getSpecialAbility
     */
    public void setSpecialAbility(SpecialAbility specialAbility) {
        this.specialAbility = specialAbility;
    }

    /**
     * Removes the special ability from the EA.
     */
    public void removeSpecialAbility() {
        this.specialAbility = null;
    }

    /**
     * Determines if the EA currently contains a special ability.
     *
     * @return if the EA has a special ability
     */
    public boolean hasSpecialAbility() {
        boolean result = false;
        if(getSpecialAbility() != null) {
            result = true;
        }
        else {
            result = false;
        }

        return result;
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