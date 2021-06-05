package com.chaosserver.merp.character.language;

import java.util.ArrayList;
import java.util.Collection;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.merp.rules.language.Language;

/**
 * A list of all the <code>CharLanguage</code>s a character knows.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 *
 * @see com.chaosserver.merp.character.language.CharLanguage
 */
public class CharLanguageList {
    /** Holds the list of languages. **/
    protected Collection charLanguageList;

    /**
     * Default constructor for bean loading.
     */
    public CharLanguageList() {
        // Build a new list with a prime above 10
        this.charLanguageList = new ArrayList(17);
    }

    /**
     * Returns a reference to the internal list.
     * It is package local and should only be called by the iterator.
     *
     * @return Internal representation of the languages
     */
    Collection getCharLanguageList() {
        return this.charLanguageList;
    }

    /**
     * Sets the collection.
     * <p>
     * This should only be used by the bean loader.  Otherwise use
     * the add method.
     * </p>
     *
     * @param charLanguageList the list that holds the languages
     */
    public void setCharLanguageList(Collection charLanguageList) {
        this.charLanguageList = charLanguageList;
    }

    /**
     * Returns the number of elements in this collection.
     *
     * @return Number of elements in the list
     */
    public int size() {
        return this.charLanguageList.size();
    }

    /**
     * Returns true if this collection contains the specified char language.
     *
     * @param charLanguage The language to check for
     * @return If the list contains the language
     */
    public boolean contains(CharLanguage charLanguage) {
        return this.charLanguageList.contains(charLanguage);
    }

    /**
     * Returns true if this collection contains a skill for the specified language.
     *
     * @param language The language to check for
     * @return If the list contains the language
     */
    public boolean contains(Language language) {
        Assertion.isNotNull(language, "Cannot check for containing a null language");
        // return this.charLanguageList.contains(charLanguage);
        boolean result = false;
        CharLanguageListIterator charLanguageListIterator = this.iterator();
        while(charLanguageListIterator.hasNext()) {
            CharLanguage charLanguage = charLanguageListIterator.next();
            if(language.equals(charLanguage.getLanguage())) {
                result = true;
                break;
            }
        }

        return result;
    }


    /**
     * Gets an iterator for the collection.
     *
     * @return The iterator
     */
    public CharLanguageListIterator iterator() {
        return new CharLanguageListIterator(this);
    }

    /**
     * Adds a CharLanguage to the list.
     *
     * @param charLanguage Language to add to the list
     */
    public void add(CharLanguage charLanguage) {
        charLanguageList.add(charLanguage);
    }
}