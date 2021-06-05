package com.chaosserver.merp.rules.specialabilities;

import com.chaosserver.merp.character.MerpCharacter;

/**
 * Holds a special ability.
 * <p>
 * Special abilities are very generic items that can be placed onto a
 * character.  When placed against a character, the apply method is
 * called allowing the special ability to make whatever affect it
 * needs to do on the character.
 * </p>
 *
 * @author Jordan Reed
 */
public interface SpecialAbility {
    /**
     * Returns a long text description of the special ability.
     *
     * @return Description of the ability
     */
    public String getDescription();

    /**
     * Returns the name of the special ability.
     *
     * @return The name of the special ability
     */
    public String getName();

    /**
     * Returns if this special ability can be applied to a character
     * multiple times.
     * <p>
     * Some special abilities, such as infravision, should not be
     * applied to a character more than once.
     * </p>
     *
     * @return if the special ability can be applied multiple times.
     */
    public boolean allowMultiple();

    /**
     * Clones the object into a new copy that is a different
     * instance than the system default one.
     */
    public Object clone();

    /**
     * Returns if the special ability is valid to apply to a character.
     *
     * @return if the skill can be applied to a character
     */
    public boolean isValid();

    /**
     * Applies the special ability to the character.
     *
     * @param character The character to apply the skill to
     */
    public void apply(MerpCharacter character);
}