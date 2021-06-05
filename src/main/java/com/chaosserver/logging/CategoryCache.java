package com.chaosserver.logging;

import com.chaosserver.assertion.Assertion;
import java.util.HashMap;
import java.util.Map;
import com.chaosserver.data.cache.MruCache;

import org.apache.log4j.Category;

/**
 * CategoryCache is used to hold a cache of log4j categories.
 * <p>
 * This cache will hold the most recently used log4j categories and after
 * the max size of the cache is reached, it will dispose of the least recently used
 * categories.
 * </p>
 *
 * @author Jordan Reed
 */
public class CategoryCache {
    /** Logger. */
    protected static Category logger = Category.getInstance(CategoryCache.class.getName());

    /** Holds the Map of all the categories. */
    protected static Map categoryMap;

    /** Holds if the class has been initilized */
    protected static boolean isInitialized = false;

    static {
        logger.info("Initializtion Start");
        categoryMap = new MruCache();
        ((MruCache)categoryMap).setMaxSize(50);
        isInitialized = true;
        logger.info("Initializtion complete");
    };

    /**
     * Gets an instance based on the object.
     * <p>
     * Shortcut to getInstance(obj.getClass().getName())
     * </p>
     *
     * @param obj The object to get the category for
     * @return The category
     */
    public static Category getInstance(Object obj) {
        Assertion.isNotNull(obj, "obj requires to get logging category");
        return getInstance(obj.getClass().getName());
    }

    /**
     * Gets an instance based on the class.
     * <p>
     * Shortcut to getInstance(classname.getName())
     * </p>
     *
     * @param classname The class to get the category for
     * @return The category
     */
    public static Category getInstance(Class classname) {
        Assertion.isNotNull(classname, "classname requires to get logging category");
        return getInstance(classname.getName());
    }

    /**
     * Gets a category for this name.
     * <p>
     * Will pull from the cache if it exists, otherwise it will create
     * a new instance and place it into the cache.
     * </p>
     *
     * @name The name to create the category for
     * @return The category
     */
    public static Category getInstance(String name) {
        Assertion.isNotNull(name, "className requires to get logging category");
        Category resultCategory;

        if(!isInitialized) {
            logger.debug("Cache not initialized.  Returning new logger for: " + name);
            resultCategory = Category.getInstance(name);
        }
        else if(categoryMap.containsKey(name)) {
            resultCategory = (Category) categoryMap.get(name);
        }
        else {
            logger.info("Creating new: " + name);
            if(logger.isDebugEnabled()) {
                logger.debug("Cache Contains: " + categoryMap.size());
            }
            resultCategory = Category.getInstance(name);
            categoryMap.put(name, resultCategory);
        }


        return resultCategory;
    }
}