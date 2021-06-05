package com.chaosserver.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.chaosserver.exception.MethodNotImplementedRX;

/**
 * Lookup a name value pair
 * <p>
 * This object existed as a Adapter for a Map into a Collection.
 * Now that the JavaBeanLoader fully supports collections, this
 * object is not needed.
 * </p>
 *
 * @ deprecated Use a Map instead
 */
public class Lookup implements Collection {
    private Map m_table;

    public Lookup() {
        m_table = new HashMap();
    }

    public boolean add(Object lookupEntry) {
        m_table.put( ((LookupEntry)lookupEntry).getKey(), ((LookupEntry)lookupEntry).getValue());
        return true;
    }

    /**
     * Gets the value from table for this key.
     *
     * @param key The key to retreive an object for
     * @return The object mapping to this key.  This will return NULL
     *         if there is no object associated with the key.
     */
    public Object get(Object key) {
        return m_table.get(key);
    }

    public int size() {
        return m_table.size();
    }

    public boolean isEmpty() {
        return m_table.isEmpty();
    }

    public boolean contains(Object obj) {
        throw new MethodNotImplementedRX("blah");
    }

    public Iterator iterator() {
        throw new MethodNotImplementedRX("blah");
    }

    public Object[] toArray() {
        throw new MethodNotImplementedRX("blah");
    }

    public Object[] toArray(Object[] obj) {
        throw new MethodNotImplementedRX("blah");
    }

    public boolean remove(Object obj) {
        throw new MethodNotImplementedRX("blah");
    }

    public boolean containsAll(Collection c) {
        throw new MethodNotImplementedRX("blah");
    }

    public boolean addAll(Collection c) {
        throw new MethodNotImplementedRX("blah");
    }

    public boolean removeAll(Collection c) {
        throw new MethodNotImplementedRX("blah");
    }

    public boolean retainAll(Collection c) {
        throw new MethodNotImplementedRX("blah");
    }

    public void clear() {
        throw new MethodNotImplementedRX("blah");
    }
}