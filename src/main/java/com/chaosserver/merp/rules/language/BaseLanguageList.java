package com.chaosserver.merp.rules.language;

import com.chaosserver.logging.CategoryCache;
import com.chaosserver.merp.data.FileNameGetter;
import com.chaosserver.data.JavaBeanLoader;
import com.chaosserver.data.JavaBeanLoaderExceptionX;
import com.chaosserver.exception.LoadErrorRX;

// import java.util.ArrayList;
import java.util.Collection;

/**
 * Holds the base list of languages.
 * <p>
 * Each race has an associated base list of languages.  This list
 * contains all of the starting values for a language as well as
 * the maximum the race can develop in the language during
 * adolesence.
 * </p>
 *
 * @version 1.0
 * @author Jordan Reed
 * @since The Beginning
 */
public class BaseLanguageList {
    /** This is the collection of languages. */
    protected Collection languages;

    /**
     * Basic constructor.
     */
    public BaseLanguageList() {
        CategoryCache.getInstance(this).debug("Constructor Called");
    }

    /**
     * Setter for the language list.
     *
     * @param languages The language collection
     * @see #getLanguages
     */
    public void setLanguages(Collection languages) {
        CategoryCache.getInstance(this).debug("Setting new language list: " + languages.size());
        this.languages = languages;
    }

    /**
     * Getter for the language list.
     *
     * @return the languages
     * @see #setLanguages
     */
    public Collection getLanguages() {
        return this.languages;
    }

    /**
     * Returns an iterator over the elements in this collection.
     *
     * @return an iterator over the elements in this collection
     */
    public BaseLanguageListIterator iterator() {
        return new BaseLanguageListIterator(this);
    }
}