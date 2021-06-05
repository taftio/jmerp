package com.chaosserver.merp.rules.skill.modifier;

/**
 * This object has the ability to place modifiers on skills
 * that a character has.
 * <p>
 * This interface grants the ability to get the modifiers
 * out of the object
 * </p>
 *
 * @author Jordan Reed
 * @since The Beginning
 */
public interface ISkillModifiable {
    /**
     * Gets the list of skill modifiers associated
     * with the object.
     *
     * @return The list of skill modifiers
     */
    public SkillModifierList getSkillModifierList();
}