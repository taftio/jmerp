package com.chaosserver.merp.rules.language;

import com.chaosserver.exception.NotFoundX;

/**
 * Use this package to find a language.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class LanguageFinder {
    /** Singleton self reference */
    private static LanguageFinder s_self;

    /**
     * The main constructor is private.  It's a singleton.
     */
    private LanguageFinder() {

    }

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static LanguageFinder instance() {
        if(s_self == null) {
            s_self = new LanguageFinder();
        }

        return s_self;
    }

    /**
     * Finds the language by name from the system language list.
     *
     * @param name The name of the stat to find
     * @return The language object being searched for
     * @throws NotFoundX The language could not be found
     */
    public static Language findByName(String name) throws NotFoundX {
        return findByName(name, LanguageList.instance());

    }

    /**
     * Finds the language by name from the given language list.
     *
     * @param name The name of the stat to find
     * @param languageList The list to search through
     * @return The language object being searched for
     * @throws NotFoundX The language could not be found
     */
    public static Language findByName(String name, LanguageList languageList) throws NotFoundX {
        LanguageListIterator languageListIterator = languageList.iterator();

        while(languageListIterator.hasNext()) {
            Language currLanguage = (Language) languageListIterator.next();
            if(currLanguage.getName().equals(name)) {
                return currLanguage;
            }
        }
        throw new NotFoundX("Couldn't find language named: " + name);
    }

}