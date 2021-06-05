package com.chaosserver.merp.rules.race.modifier;

import com.chaosserver.merp.rules.stat.modifier.BaseStatModifier;

/**
 * Modifies a statistic with a racial modifier.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class RaceStatModifier extends BaseStatModifier {
    /**
     * Gets the bonus type.
     *
     * @return the bonus type
     */
    public int getBonusType() {
        return RACE_BONUS;
    }
}