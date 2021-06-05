package com.chaosserver.merp.rules.skill.modifier;

import java.util.ArrayList;

/**
 * Holds a list of skill modifiers.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class SkillModifierList extends ArrayList {
    /**
     * Adds skill modifier to the list.
     *
     * @param skillModifier The new modifier to add
     * @return if the skill was added
     */
    public boolean add(ISkillModifier skillModifier) {
        return super.add(skillModifier);
    }

    /**
     * Adds skill modifier to the list.
     *
     * @param obj The new modifier to add
     * @return if the skill was added
     */
    public boolean add(Object obj) {
        return add((ISkillModifier) obj);
    }
}