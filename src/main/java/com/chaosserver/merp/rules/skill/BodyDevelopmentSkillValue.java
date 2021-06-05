package com.chaosserver.merp.rules.skill;

import com.chaosserver.merp.character.skill.CharSkill;
import com.chaosserver.merp.character.skill.BodyDevelopmentCharSkill;
import com.chaosserver.merp.character.skill.ICharSkill;

/**
 * BaseSkillValue for body development.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class BodyDevelopmentSkillValue extends BaseSkillValue {
    // Inherits from baseclass
    public ICharSkill getCharSkill() {
        return new BodyDevelopmentCharSkill(getSkill(), getValue());
    }
}