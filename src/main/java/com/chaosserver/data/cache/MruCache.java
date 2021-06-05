package com.chaosserver.data.cache;

import java.util.Date;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.SortedMap;

import org.apache.log4j.Category;

import com.chaosserver.logging.CategoryCache;

public class MruCache extends HashMap {
    /** Logger. */
    protected static Category logger = Category.getInstance(MruCache.class.getName());

    protected int maxSize = 10;
    protected SortedMap timeMap = new TreeMap();

    protected class MruEntry implements Comparable {

        protected Object key;
        protected Object value;
        protected Date lastAccess;

        public MruEntry(Object key, Object value) {
            setKey(key);
            setValue(value);
            touch();
        }

        public void setKey(Object key) {
            this.key = key;
        }

        public Object getKey() {
            return this.key;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Object getValue() {
            return this.value;
        }

        public void touch() {
            if(null == lastAccess) {
                lastAccess = new Date();
            }
            else {
                lastAccess.setTime(System.currentTimeMillis());
            }
        }

        public Date getLastAccess() {
            return this.lastAccess;
        }

        public int compareTo(Object o) {
            MruEntry mruEntry = (MruEntry) o;
            int result = this.getLastAccess().compareTo(mruEntry.getLastAccess());

            return result;
        }


        /**
        public boolean equals(Object object) {
            boolean result = false;

            if(object instanceof MruEntry) {
                if(this.getKey().equals(((MruEntry)object).getKey())
                        && this.getValue().equals(((MruEntry)object).getValue())
                        && this.getLastAccess().equals(((MruEntry)object).getLastAccess()) ) {


                    result = true;
                }
            }
            return result;
        }
        */

        public String toString() {
            StringBuffer buffer = new StringBuffer(256);
            buffer.append(this.getClass().getName());
            buffer.append("[");
            buffer.append("key=");
            buffer.append(getKey());
            buffer.append(", value=");
            buffer.append(getValue());
            buffer.append(", lastAccess=");
            buffer.append(getLastAccess().getTime());
            buffer.append("]");

            return buffer.toString();
        }
    }

    public Object put(Object key, Object value) {
        Object oldEntry = null;

        if(containsKey(key)) {
            oldEntry = get(key);
        }

        MruEntry newEntry = new MruEntry(key, value);
        logger.debug("Adding: " + newEntry);

        timeMap.put(newEntry.getLastAccess(), newEntry);
        super.put(newEntry.getKey(), newEntry);
        clean();

        return oldEntry;
    }

    protected void clean() {
        while(size() > maxSize) {
            logger.info("Max size reached: " + maxSize);
            logger.debug("timeMap.firstKey() = " + timeMap.firstKey());
            logger.debug("Entry is: " + timeMap.get(timeMap.firstKey()));
            MruEntry entry = (MruEntry) timeMap.get(timeMap.firstKey());
            if(entry != null) {
                logger.debug("Dropping: " + entry.getKey());
                remove(entry.getKey());
                timeMap.remove(entry.getLastAccess());
            }
        }
    }

    public MruEntry getMruEntry(Object key) {
        MruEntry entry = null;

        if(containsKey(key)) {
            Object objEntry = null;
            objEntry = super.get(key);

            if(objEntry instanceof MruEntry) {
                entry = (MruEntry) objEntry;
                timeMap.remove(entry.getLastAccess());
                entry.touch();
                timeMap.put(entry.getLastAccess(), entry);
            }
        }

        return entry;
    }

    public Object get(Object key) {
        MruEntry entry = getMruEntry(key);

        if(entry != null) {
            return entry.getValue();
        }
        else {
            return null;
        }
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        clean();
    }

    public int getMaxSize() {
        return this.maxSize;
    }

}