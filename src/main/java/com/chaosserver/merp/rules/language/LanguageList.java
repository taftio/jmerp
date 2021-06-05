package com.chaosserver.merp.rules.language;

import java.util.Collection;

import com.chaosserver.merp.data.FileNameGetter;
import com.chaosserver.data.JavaBeanLoader;
import com.chaosserver.data.JavaBeanLoaderExceptionX;
import com.chaosserver.exception.LoadErrorRX;

/**
 * Holds the list of languages.
 *
 * @version 1.0
 * @author Jordan Reed
 */
public class LanguageList {
    /** Singleton self reference. */
    private static LanguageList self;

    /** This is the collection of languages. */
    protected Collection languages;

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static LanguageList instance() {
        if(self == null) {
            try {
                self = (LanguageList) JavaBeanLoader.getBean(FileNameGetter.XML_LANGUAGELIST);
            } catch (JavaBeanLoaderExceptionX e) {
                throw new LoadErrorRX("Problem loading: '" + FileNameGetter.XML_LANGUAGELIST + "', " + e.toString());
            }
        }

        return self;
    }

    /**
     * Sets the collection of languages.
     * <p>
     * This should only be used by the bean loader
     * </p>
     *
     * @param languages The collection of languages
     */
    public void setLanguages(Collection languages) {
        this.languages = languages;
    }

    /**
     * Gets the collection of languages.
     * This should only be used by the iterator
     *
     * @return The collection
     */
    Collection getLanguages() {
        return this.languages;
    }

    /**
     * Gets the iterator over the list.
     *
     * @return The iterator over the list
     */
    public LanguageListIterator iterator() {
        return new LanguageListIterator(this);
    }

    /**
     * Returns the number of elements in this collection.
     *
     * @return Number of elements in the list
     */
    public int size() {
        return this.languages.size();
    }

}