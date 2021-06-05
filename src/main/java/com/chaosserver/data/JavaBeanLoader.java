package com.chaosserver.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collection;
import java.util.WeakHashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.SAXException;

import com.chaosserver.logging.CategoryCache;
import com.chaosserver.assertion.Assertion;

/**
 * Loads a generic Bean from an XML file
 * <p>
 * Java and XML go together like wine and cheese.  It seems that the two
 * technologies are good friends.  Therefore it made a lot of sense to
 * store JavaBeans inside of XML files.  After searching long and hard
 * for package already in existence that met my needs, I failed.
 * Therefore, the JavaBeanLoader came to life.
 * </p>
 * <p>
 * The javabean loader is a static class.  The end user should call
 * one of it's many getBean() methods passing in whatever input source
 * is most appropriate.  I like to give references to files, but
 * many people find that InputStreams are nicer things to work with.
 * </p>
 * <p>
 * The BeanLoader can load only certain types of Objects that certain
 * criteria.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.5
 * @since The Beginning 4/20/2001
 */
public class JavaBeanLoader implements IJavaBeanXmlConstants {
    /** Reference to the classloader used on the system. */
    protected static ClassLoader classLoader;

    static {
        classLoader = JavaBeanLoader.class.getClassLoader();
        if(classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }
    }

    static Map beanCache = new WeakHashMap();

    static EntityResolver entityResolver = new DtdEntityResolver();

    /**
     * Takes in a <code>File</code> representing an XML file based
     * on the JavaBean DTD that will be loaded into the appropriate
     * object and returned.
     *
     * @param xFile The file to be loaded
     * @return The object specified in the file
     * @throws JavaBeanLoaderExceptionX If there is any failure loading the bean
     */
    public static Object getBean(File xFile) throws JavaBeanLoaderExceptionX {
        if(beanCache.containsKey(xFile)) {
            return beanCache.get(xFile);
        }

        try {
            Object currObject = loadXML(xFile);
            beanCache.put(xFile, currObject);
            return currObject;
        }
        catch (ParserConfigurationException e) {
            throw new JavaBeanLoaderExceptionX("Failed to load "
                + "ParserConfigurationException: " + e.toString());
        }
        catch (SAXException e) {
            throw new JavaBeanLoaderExceptionX("Caught "
                + "SAXException: " + e.toString());
        }
        catch (IOException e) {
            throw new JavaBeanLoaderExceptionX("Caught "
                + "IOException: " + e.toString());
        }
    }

    /**
     * Wrapper for <code>getBean(File)</code>.
     *
     * @param fileName The name of a file to be loaded
     * @return The object specified in the file
     * @throws JavaBeanLoaderExceptionX If there is any failure loading the bean
     */
    public static Object getBean(String fileName) throws JavaBeanLoaderExceptionX {
        CategoryCache.getInstance(JavaBeanLoader.class).info("Loading: " + fileName);
        return getBean(new File(fileName));
    }

    /**
     * Takes in a <code>URL</code> for a resource XML file and loads.
     * it
     *
     * @param url The URL of a resource
     * @return The object specifid in the file
     * @throws JavaBeanLoaderExceptionX If there is any failure loading the bean
     */
    public static Object getBean(URL url) throws JavaBeanLoaderExceptionX {
        Object bean = null;
        if(beanCache.containsKey(url)) {
            bean = beanCache.get(url);
        }
        else {
            try {
                Object currObject = loadXML(url);
                beanCache.put(url, currObject);
                bean = currObject;
            }
            catch (Exception e) {
                handleException("Loading URL " + url, e);
            }
        }

        return bean;
    }

    /**
     * Takes in an XML <code>Document</code> for a and loads it.
     *
     * @param document The Document to load
     * @return The object specifid in the file
     * @throws JavaBeanLoaderExceptionX If there is any failure loading the bean
     */
    public static Object getBean(Document document) throws JavaBeanLoaderExceptionX {
        return loadNode(document.getDocumentElement());
    }

    /**
     * Takes in an <code>InputStream</code> for a resource XML file and loads.
     * it
     *
     * @param inputStream The input stream of a resource
     * @return The object specifid in the file
     * @throws JavaBeanLoaderExceptionX If there is any failure loading the bean
     */
    public static Object getBean(InputStream inputStream) throws JavaBeanLoaderExceptionX {
        Object bean = null;

        try {
            bean = loadXML(inputStream);
        }
        catch (Exception e) {
            handleException("Loading InputStream", e);
        }

        return bean;
    }

    /**
     * Loads the main object contained within the javabean file.
     *
     * @param is The input stream of the JavaBean.
     * @return The object specified in the file
     */
    private static Object loadXML(InputStream is) throws ParserConfigurationException,
                                              SAXException, IOException,
                                              JavaBeanLoaderExceptionX {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setEntityResolver(entityResolver);
        Document document = builder.parse( is );

        Element root = document.getDocumentElement();

        return loadNode(root);
    }

    /**
     * Loads the main object contained within the javabean file.
     *
     * @param url The resource URL of the JavaBean.
     * @return The object specified in the file
     */
    private static Object loadXML(URL url) throws ParserConfigurationException,
                                              SAXException, IOException,
                                              JavaBeanLoaderExceptionX {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setEntityResolver(entityResolver);
        Document document = builder.parse( url.toString() );

        Element root = document.getDocumentElement();

        return loadNode(root);
    }

    /**
     * Loads the main object contained within the javabean file.
     *
     * @param xFile The XML file to load from
     * @return The oject specified in the file
     * @throws JavaBeanLoaderExceptionX If there is any failure loading the bean
     */
    private static Object loadXML(File xFile) throws ParserConfigurationException,
                                              SAXException, IOException,
                                              JavaBeanLoaderExceptionX {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setEntityResolver(entityResolver);
        Document document = builder.parse( xFile );

        Element root = document.getDocumentElement();

        return loadNode(root);
    }

    /**
     * Wraps an exception in a JavaBeanLoaderExceptionX.
     *
     * @param s A string describing the condition of the error.
     * @param t The object to wrap in the new exception.
     * @throws JavaBeanLoaderExceptionX A new exception wrapping the old one.
     */
    protected static void handleException(String s, Throwable t)
            throws JavaBeanLoaderExceptionX {

        if(t instanceof InvocationTargetException) {
            throw new JavaBeanLoaderExceptionX(s,
                ((InvocationTargetException) t).getTargetException());
        }
        else if(!(t instanceof JavaBeanLoaderExceptionX)) {
            throw new JavaBeanLoaderExceptionX(s, t);
        }
    }

    /**
     * This method dispatches based on the type of Node to the proper
     * method which handles that specific node type to get the object
     * out of the node.
     *
     * @param node The node to load into an object
     * @return The object contained in the node
     * @throws JavaBeanLoaderExceptionX If there is any failure loading the bean
     */
    private static Object loadNode(Node node) throws JavaBeanLoaderExceptionX {
        String nodeName = node.getNodeName();
        CategoryCache.getInstance(JavaBeanLoader.class).debug("JavaBeanLoader.loadNode - Entering: " + nodeName);
        if(BEAN_ELM.equals(nodeName)) {
            return loadBeanNode(node);
        }
        else if(COLLECTION_ELM.equals(nodeName)) {
            return loadCollectionNode(node);
        }
        else if(OBJECT_ELM.equals(nodeName)) {
            return loadObjectNode(node);
        }
        else if (FINDABLE_ELM.equals(nodeName)) {
            return loadFindableNode(node);
        }
        else if (MAP_ELM.equals(nodeName)) {
            return loadMapNode(node);
        }
        else {
            printOut("JavaBeanLoader.loadNode - Unknown Node: " + nodeName);
        }

        return null;
    }

    /**
     * Loads a object contained within a Bean node.
     *
     * @param node the node to load the bean object
     * @return The object represented in the node
     */
    private static Object loadBeanNode(Node node) throws JavaBeanLoaderExceptionX {
        Assertion.isNodeNamed(BEAN_ELM, node.getNodeName());
        printOut("JavaBeanLoader.loadBeanNode - Entering");

        // All beans are required to have a public no value constructor.
        //   The first step is to get the class of the bean as an attribute
        //   and instantiate it using the basic constructor.
        Object bean = getObjectByClass(node);
        CategoryCache.getInstance(JavaBeanLoader.class).debug("Loading: " + bean.getClass().getName());

        // Beans are nothing more than a list of properties, so iterate
        // through the subnodes and load the properties
        NodeList properties = node.getChildNodes();
        for(int currPropertyCount = 0; currPropertyCount < properties.getLength(); currPropertyCount++) {
            Node currPropNode = properties.item(currPropertyCount);
            if(PROPERTY_ELM.equals(currPropNode.getNodeName())) {
                Property currProperty = loadPropertyNode(currPropNode);
                if(currProperty != null) {
                    setProperty(bean, currProperty);
                }
                else {
                    printOut("JaveBeanLoader.loadBeanNode - Got null property");
                }
            }
            else if(TEXT_ELM.equals(currPropNode.getNodeName())) {
                // Not Sure why I'm getting random text nodes
            }
            else {
                printOut("JavaBeanLoader.loadBeanNode - Unknown Node: "
                    + currPropNode.getNodeName() + ": '"
                    + currPropNode.getNodeValue() + "'");
            }
        }

        return bean;
    }

    /**
     * Gets the Class represented by the javabean from a node.
     *
     * @param node The node containing the javabean
     * @return The class
     */
    private static Object getObjectByClass(Node node) throws JavaBeanLoaderExceptionX {
        NamedNodeMap nodeAttributes = node.getAttributes();
        Node classAtt = nodeAttributes.getNamedItem(CLASS_ATT);
        String className = classAtt.getNodeValue();
        Object newObject = null;

        CategoryCache.getInstance(JavaBeanLoader.class).debug("Loading: " + className);

        try {
            Class beanClass = classLoader.loadClass(className);
            newObject = beanClass.newInstance();
        }
        catch (Exception e) {
            handleException("Error instantiating class: " + className, e);
        }

        return newObject;
    }

    /**
     * Gets the Property object represented inside a property node.
     *
     * @param node The node holding the property object
     * @return The property object in the node
     * @throws JavaBeanLoaderExceptionX If there is any failure loading the bean
     */
    public static Property loadPropertyNode(Node node) throws JavaBeanLoaderExceptionX {
        Assertion.isNodeNamed(PROPERTY_ELM, node.getNodeName());
        printOut("JavaBeanLoader.loadPropertyNode - Entering");

        String propertyName = null;
        Object propertyObj = null;

        NamedNodeMap nodeAttributes = node.getAttributes();
        Node nameAtt = nodeAttributes.getNamedItem(NAME_ATT);
        propertyName = nameAtt.getNodeValue();

        printOut("JavaBeanLoader.loadPropertyNode - Property Named: " + propertyName);


        NodeList properties = node.getChildNodes();
        for(int currPropertyCount = 0; currPropertyCount < properties.getLength(); currPropertyCount++) {
            Node currPropNode = properties.item(currPropertyCount);

            if(TEXT_ELM.equals(currPropNode.getNodeName())) {
                // Not Sure why I'm getting random text nodes
            }
            else {
                propertyObj = loadNode(currPropNode);
                if(propertyObj == null) {
                    printOut("JavaBeanLoader.loadPropertyNode - Got Back NULL fro loadNode: "
                        + currPropNode.getNodeName());
                }
                else {
                    printOut("JavaBeanLoader.loadPropertyNode - Got Back value, returning property");
                    return new Property(propertyName, propertyObj);
                }
            }
        }
        return null;
    }

    /**
     * Gets the Collection object represtned inside a collection node.
     *
     * @param node The node representing the collection
     * @return A collection
     * @throws JavaBeanLoaderExceptionX If there is any failure loading the bean
     */
    public static Collection loadCollectionNode(Node node) throws JavaBeanLoaderExceptionX {
        printOut("JavaBeanLoader.loadCollectionNode - Entering");
        Collection collection = (Collection) getObjectByClass(node);

        NodeList elements = node.getChildNodes();
        for(int currElementCount = 0; currElementCount < elements.getLength(); currElementCount++) {
            Node currElementNode = elements.item(currElementCount);
            Object elementObj;

            if(TEXT_ELM.equals(currElementNode.getNodeName())) {
                // Not Sure why I'm getting random text nodes
            }
            else {
                elementObj = loadNode(currElementNode);
                if(elementObj == null) {
                    printOut("JavaBeanLoader.loadElementNode - Got Back NULL fro loadNode: "
                        + currElementNode.getNodeName());
                }
                else {
                    collection.add(elementObj);
                }
            }
        }


        return collection;
    }

    /**
     * Gets the Map object represented inside a map node.
     *
     * @param node The node representing the map
     * @return A map
     * @throws JavaBeanLoaderExceptionX If there is any failure loading the bean
     */
    public static Map loadMapNode(Node node) throws JavaBeanLoaderExceptionX {
        printOut("JavaBeanLoader.loadMapNode - Entering");
        Map map = (Map) getObjectByClass(node);
        printOut("Got: " + map.getClass().getName());

        NodeList elements = node.getChildNodes();
        for(int currElementCount = 0; currElementCount < elements.getLength(); currElementCount++) {
            Node currElementNode = elements.item(currElementCount);
            Object mapEntry = loadNode(currElementNode);
            if(mapEntry instanceof MapEntry) {
                printOut("Got Map Entry");
                map.put( ((MapEntry)mapEntry).getKey(), ((MapEntry)mapEntry).getValue());
            }
        }

        return map;
    }

    /**
     * Gets the base object represneted inside an object element
     * A base object must contain a constructor that takes in a single string represening it.
     *
     * @param node The node containing the object
     * @return The basic object
     * @throws JavaBeanLoaderExceptionX If there is any failure loading the bean
     */
    public static Object loadObjectNode(Node node) throws JavaBeanLoaderExceptionX {
        printOut("JavaBeanLoader.loadObjectNode - Entering");
        Object newObject = null;

        NamedNodeMap nodeAttributes = node.getAttributes();
        Node classAtt = nodeAttributes.getNamedItem(CLASS_ATT);
        String className = classAtt.getNodeValue();
        String classValue = node.getChildNodes().item(0).getNodeValue();
        printOut("JavaBeanLoader.loadObjectNode - Value: " + classValue);

        try {
            Class beanClass = classLoader.loadClass(className);
            Class classes[] = new Class[1];
            classes[0] = String.class;
            Constructor beanConstructor = beanClass.getConstructor(classes);
            Object objects[] = new Object[1];
            objects[0] = classValue;
            newObject = beanConstructor.newInstance(objects);
        }
        catch (Exception e) {
            handleException("Error loading Object node: " + className, e);
        }

        return newObject;
    }

    /**
     * Sets a single property on the bean object passed in.
     *
     * @param bean The bean object to set the property on
     * @param property The property to be set
     */
    private static void setProperty(Object bean, Property property) throws
                                            JavaBeanLoaderExceptionX {

        CategoryCache.getInstance(JavaBeanLoader.class).debug("Setting " + property.getName() + " on " + bean.getClass().getName());
        String methodName = "set"+property.getName();
        printOut("JavaBeanLoader.setProperty - " + methodName + "("+property.getObject().getClass()+")");

        Class classes[] = { property.getObject().getClass() };
        Object objects[] = { property.getObject() };

        try {
            Method method = bean.getClass().getMethod(methodName, classes);
            method.invoke(bean, objects);
        }
        catch (NoSuchMethodException e) {
            // This is a cheap hack due to primitive types.  Trying to
            // find a method with (int) as a parameter is basically
            // impossible since int cannot have it's own class.  So
            // instead we must iterate through all of them to find the
            // right one, and then call it.  The <code>invoke</code> function,
            // unlike the <code>getMethod</code> method is smart enough to
            // unwrap primitives.
            boolean success = false;
            Method methods[] = bean.getClass().getMethods();
            for(int i = 0; i < methods.length; i++) {
                if(methods[i].getName().equals(methodName)) {
                    try {
                        methods[i].invoke(bean, objects);
                        success = true;
                    }
                    catch (IllegalAccessException e2) {
                        throw new JavaBeanLoaderExceptionX("IllegalAccessException: "
                            + e2.toString());
                    }
                    catch (InvocationTargetException e2) {
                        throw new JavaBeanLoaderExceptionX("InvocationTargetException: "
                            + e2.toString());
                    }
                }
            }

            if(!success) {
                throw new JavaBeanLoaderExceptionX("NoSuchMethodException: "
                    + e.toString());
            }
        }
        catch (IllegalAccessException e) {
            throw new JavaBeanLoaderExceptionX("IllegalAccessException: "
                + e.toString());
        }
        catch (InvocationTargetException e) {
            throw new JavaBeanLoaderExceptionX("InvocationTargetException: "
                + e.toString());
        }
    }

    /**
     * Finds an object in the system that's already been loaded.
     *
     * @param node The node representing the finable object
     * @return the object to be found
     */
    private static Object loadFindableNode(Node node) throws JavaBeanLoaderExceptionX {
        NamedNodeMap nodeAttributes = node.getAttributes();

        String className = nodeAttributes.getNamedItem(CLASS_ATT).getNodeValue();
        String finderClassName = nodeAttributes.getNamedItem(FINDER_CLASS_ATT).getNodeValue();
        String finderKeyName = nodeAttributes.getNamedItem(FINDER_KEY_ATT).getNodeValue();
        String finderKey = node.getChildNodes().item(0).getNodeValue();
        Object returnObject = null;

        printOut("JavaBeanLoader.loadFindableNode - Class: " + className);
        printOut("JavaBeanLoader.loadFindableNode - FinderClass: " + finderClassName);
        printOut("JavaBeanLoader.loadFindableNode - KeyName: " + finderKeyName);
        printOut("JavaBeanLoader.loadFindableNode - Key: " + finderKey);


        try {
            Class finder = classLoader.loadClass(finderClassName);
            Class params[] = new Class[1];
            params[0] = String.class;

            // Grabs the methods to get reference and find
            Method instanceMethod = finder.getMethod("instance", null);
            Method findMethod = finder.getMethod("findBy" + finderKeyName, params );

            // Grabs the finder object
            Object finderObject = instanceMethod.invoke(null, null);

            // Finds the fun
            Object findName[] = new Object[1];
            findName[0] = finderKey;
            returnObject = findMethod.invoke(finderObject, findName);
        }
        catch (Exception e) {
            handleException("Failed to load Findable node", e);
        }

        return returnObject;
    }

    /**
     * Prints the output to a log.
     *
     * @param s The string to log
     */
    private static void printOut(String s) {
        if(false) {
            System.out.println(s);
        }
    }

}