package com.chaosserver.data.find;

import java.lang.reflect.Method;
import java.lang.NoSuchMethodException;
import java.lang.IllegalAccessException;
import java.lang.reflect.InvocationTargetException;

/**
 * Provides support for Finder objects, Findable objects and methods interacting with them.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class FinderSupport {
    /**
     * Gets the value of the primary key from a findable object.
     *
     * @param findable The object to get the primary key from
     * @return the primary key of the object queried
     * @throws NoSuchMethodException If there is no correct getter method for the primary key
     * @throws IllegalAccessException If there is no access to the getter method for the primary key
     * @throws InvocationTargetException If there is an exception throw when getting the key
     */
    public static Object getPk(Findable findable) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String propertyName = findable.getPkName();
        Class findableClass = findable.getClass();
        Method primaryKeyMethod = findableClass.getMethod("get" + propertyName, null);
        Object result = primaryKeyMethod.invoke(findable, null);

        return result;
    }
}