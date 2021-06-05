package com.chaosserver.data;

import java.lang.IllegalAccessException;
import java.lang.NoSuchMethodException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.data.find.Findable;
import com.chaosserver.data.find.FinderSupport;
import com.chaosserver.logging.CategoryCache;

/**
 * The JavaBean writer is used to write out an arbitrary JavaBean to XML.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class JavaBeanWriter implements IJavaBeanXmlConstants {
    /** Prefix to getter methods in a JavaBean. */
    public static String GETTER_PREFIX = "get";

    /** Prefix to setter methods in a JavaBean. */
    public static String SETTER_PREFIX = "set";

    /** Document object that is getting written to. */
    protected Document document = null;

    /**
     * Generates a XML Document for the given object.
     *
     * @param object The JavaBean to generate an XML document for
     * @return The document object representing the object passed in
     * @throws JavaBeanWriterExceptionX An error occurred attempting to create the document
     */
    public Document getDom(Object object) throws JavaBeanWriterExceptionX {
        CategoryCache.getInstance(this).info("Writing new object: " + object.getClass().getName());
        // First we must create the DOM that we will add everything to
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.newDocument();
        }
        catch (Exception e) {
            handleException("Failed to create a new document", e);
        }

        // document.setNodeValue("HTML");
        // document.appendChild(document.createElement("TEST"));

        document.appendChild(createNode(object));

        return document;
    }

    /**
     * Creates a node in the document representing the object passed in.
     *
     * @param object The object to create a node for
     * @return An XML node representing to object
     * @throws JavaBeanWriterExceptionX An error occurred attempting to create the document
     */
    protected Node createNode(Object object) throws JavaBeanWriterExceptionX {
        Assertion.isNotNull(object, "Cannot create a node for a null object");
        if(isObject(object)) {
            return createObjectNode(object);
        }
        else if(object instanceof Findable) {
            return createFindableNode((Findable) object);
        }
        else if(object instanceof Collection) {
            return createCollectionNode((Collection) object);
        }
        else if(object instanceof Map) {
            return createMapNode((Map) object);
        }
        else {
            // It must just be a good old bean
            return createBeanNode(object);
        }
    }

    /**
     * Creates a node in the document representing the primitive object passed in.
     *
     * @param object The object to create a node for
     * @return An XML node representing to object
     */
    protected Node createObjectNode(Object object) {
        CategoryCache.getInstance(this).debug("Creating Object Node: " + object.getClass().getName());
        Element objectElement = document.createElement(OBJECT_ELM);
        objectElement.setAttribute(CLASS_ATT, object.getClass().getName());
        objectElement.appendChild(document.createTextNode(object.toString()));

        return objectElement;
    }

    /**
     * Creates a node in the document representing the java bean object passed in.
     *
     * @param object The object to create a node for
     * @return An XML node representing to object
     * @throws JavaBeanWriterExceptionX An error occurred attempting to create the document
     */
    protected Node createBeanNode(Object object) throws JavaBeanWriterExceptionX {
        Element beanElement = document.createElement(BEAN_ELM);
        beanElement.setAttribute(CLASS_ATT, object.getClass().getName());

        Class beanClass = object.getClass();
        Method[] methods = beanClass.getDeclaredMethods();

        for(int i=0; i<methods.length; i++) {
            if(methods[i].getName().startsWith(SETTER_PREFIX)) {
                String propertyName = null;
                try {
                    propertyName = methods[i].getName().substring(SETTER_PREFIX.length());
                    Method getterMethod = beanClass.getDeclaredMethod(GETTER_PREFIX + propertyName, null);

                    Class[] classes = getterMethod.getParameterTypes();
                    if(classes.length == 0) {
                        try {
                            beanElement.appendChild(createPropertyNode(object, getterMethod));
                        }
                        catch (IllegalAccessException e) {
                            CategoryCache.getInstance(this).warn("IllegalAccessException on getter: " + getterMethod.getName());
                        }
                    }
                }
                catch (NoSuchMethodException e) {
                    // Eat it.  Yum.  This means that there is no such getter for a setter.
                    // Bad!
                    CategoryCache.getInstance(this).warn("Setter without Getter for property '" + propertyName
                        + "' missing on class '" + object.getClass().getName());
                }
            }

        }

        return beanElement;
    }

    /**
     * Creates a node in the document representing a property on a bean.
     *
     * @param implementor The object to get the property from
     * @param getterMethod The method to get the value of the property
     * @return An XML node representing to property
     * @throws JavaBeanWriterExceptionX An error occurred attempting to create the document
     * @throws IllegalAccessException If the loader is not able to access the getter method
     */
    protected Node createPropertyNode(Object implementor, Method getterMethod)
            throws JavaBeanWriterExceptionX, IllegalAccessException {

        String propertyName = getterMethod.getName().substring(GETTER_PREFIX.length());
        CategoryCache.getInstance(this).debug("Creating Property Node: " + propertyName);

        Element propertyElement = document.createElement(PROPERTY_ELM);
        propertyElement.setAttribute(NAME_ATT, propertyName);
        Object returnValue = null;
        try {
            CategoryCache.getInstance(this).debug("Invoking '" + getterMethod.getName() + "' on '" + implementor.getClass().getName() + "'");
            returnValue = getterMethod.invoke(implementor, null);

        }
        catch (InvocationTargetException e) {
            handleException("Failed to invoke method: " + getterMethod.getName(), e);
        }

        if(returnValue != null) {
            CategoryCache.getInstance(this).debug("Got Back: " + returnValue.getClass().getName());
            propertyElement.appendChild(createNode(returnValue));
        }


        return propertyElement;
    }

    /**
     * Creates a node in the document representing the collection object passed in.
     *
     * @param collection The object to create a node for
     * @return An XML node representing to object
     * @throws JavaBeanWriterExceptionX An error occurred attempting to create the document
     */
    protected Node createCollectionNode(Collection collection) throws JavaBeanWriterExceptionX {
        Element collectionElement = document.createElement(COLLECTION_ELM);
        collectionElement.setAttribute(CLASS_ATT, collection.getClass().getName());

        Iterator iterator = collection.iterator();
        while(iterator.hasNext()) {
            Object object = iterator.next();
            collectionElement.appendChild(createNode(object));
        }

        return collectionElement;
    }

    /**
     * Creates a node in the document representing a findable object passed in.
     *
     * @param findable The object to create a node for
     * @return An XML node representing to object
     * @throws JavaBeanWriterExceptionX An error occurred attempting to create the document
     */
    protected Node createFindableNode(Findable findable) throws JavaBeanWriterExceptionX {
        Element findableElement = document.createElement(FINDABLE_ELM);
        findableElement.setAttribute(CLASS_ATT, findable.getClass().getName());
        findableElement.setAttribute(FINDER_CLASS_ATT, findable.getFinder().getClass().getName());
        findableElement.setAttribute(FINDER_KEY_ATT, findable.getPkName());
        Object primaryKey = null;
        try {
            primaryKey = FinderSupport.getPk(findable);
        }
        catch (Exception e) {
            handleException("Failed to find the primary key value of bean: " + findable.getClass().getName(), e);
        }
        Assertion.isTrue(primaryKey instanceof String, "Can only find by a String value");
        findableElement.appendChild(document.createTextNode((String) primaryKey));

        return findableElement;
    }

    /**
     * Creates a node in the document representing a map object passed in.
     *
     * @param map The map to create a node for
     * @return An XML node representing to object
     * @throws JavaBeanWriterExceptionX An error occurred attempting to create the document
     */
    protected Node createMapNode(Map map) throws JavaBeanWriterExceptionX {
        Element mapElement = document.createElement(MAP_ELM);
        mapElement.setAttribute(CLASS_ATT, map.getClass().getName());

        Collection entryCollection = map.entrySet();
        Iterator iterator = entryCollection.iterator();

        while(iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            mapElement.appendChild(createNode(new MapEntry(entry.getKey(), entry.getValue())));
            // collectionElement.appendChild(createNode(object));
        }

        return mapElement;
    }

    /**
     * Tests if an object is a primitive.
     *
     * @param object Object to test
     * @return If the object is a primitive
     */
    protected boolean isObject(Object object) {
        if(   object instanceof Integer
           || object instanceof String) {

            return true;
        }
        else {
            return false;
        }
    }


    /**
     * Wraps an exception in a JavaBeanWriterExceptionX.
     *
     * @param s String describing what caused this exception
     * @param t Exception bean wrapped
     * @throws JavaBeanWriterExceptionX Throw wrapping the given exception
     */
    protected void handleException(String s, Throwable t)
            throws JavaBeanWriterExceptionX {

        if(t instanceof InvocationTargetException) {
            throw new JavaBeanWriterExceptionX(s,
                ((InvocationTargetException) t).getTargetException());
        }
        else if(!(t instanceof JavaBeanWriterExceptionX)) {
            throw new JavaBeanWriterExceptionX(s, t);
        }
    }
}
