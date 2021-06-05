package com.chaosserver.merp.rules.language.restriction;

import com.chaosserver.merp.rules.restriction.BaseCharRestriction;

/**
 * Restricts that maximum ranks that a character may start a language with.
 * <p>
 * This restriction only affects characters that are still 0 level.  When
 * a character progresses to 1st level, this restriction becomes dead and
 * can be safely removed from the character.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.0
 */
public abstract class MaxLanguageRankRestriction extends BaseCharRestriction {
	/** The max this language is allowed to reach */
}