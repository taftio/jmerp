package com.chaosserver.data;

import java.io.InputStream;

import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;

import com.chaosserver.merp.data.FileNameGetter;
import com.chaosserver.logging.CategoryCache;

/**
 * This is used to attempt to load a DTD for a document from the classpath
 * and if this fails, it will call the super.
 * @since The Beginning 4/20/2001
 */
public class DtdEntityResolver extends DefaultHandler {
    /** Extension of a JAR file. */
    protected static String JAR_EXT = ".jar!";

    /** System id for a file. */
    protected String systemId = null;

    /**
     * Resolves an entity requested by a XML file.
     *
     * @param publicId The public identifer, or null if none is available.
     * @param systemId The system identifier provided in the XML document.
     * @return The new input source, or null to require the default behaviour.
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     */
    public InputSource resolveEntity(String publicId, String systemId)
            throws SAXException {

        InputSource result = null;
        CategoryCache.getInstance(this).debug("publicId: " + publicId);
        CategoryCache.getInstance(this).debug("systemId: " + systemId);
        this.systemId = systemId;

        int lastIndex = systemId.lastIndexOf(JAR_EXT);
        if(lastIndex >= 0) {
            // The entity is in a jar file.
            CategoryCache.getInstance(this).debug("Last index of JAR is: " + lastIndex);
            String dtdName = systemId.substring(lastIndex+JAR_EXT.length());
            CategoryCache.getInstance(this).debug("I got: " + dtdName);
            InputStream inputStream = FileNameGetter.getInputStream(dtdName);
            result = new InputSource(inputStream);
        }

        if(result == null) {
            // System.out.println("DtdEntityResolver.resolveEntity - Got nulls, so call the parent");
            result = super.resolveEntity(publicId, "merp/data/game/race/JavaBean.dtd");
        }

        return result;
    }

    /**
     * Returns the last system id from a call to load.
     *
     * @return The last system id.
     */
    public String getLastSystemId() {
        return systemId.toString();
    }
}