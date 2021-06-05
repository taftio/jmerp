package com.chaosserver.data;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.chaosserver.merp.dice.ClosedPercentileDice;
import com.chaosserver.assertion.Assertion;
import com.chaosserver.exception.LoadErrorRX;

/**
 * This class is used to load up generic tables that one would roll
 * against.  It will use the meta-data in the table to determine
 * what type of object to instantiate and return back to the user.
 *
 * The table allows for two types of ways to describe objects.  For
 * objects that have a finder you can define a row in the table to
 * be an object which can be found.  Otherwise you can define
 * a JavaBean with properties as a row in the table and it will
 * instantiate a new instance of that object
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class RollTable {
    /** Holds the last roll. */
    private int m_roll;

    /**
     * Getter for the roll.
     *
     * @return The roll
     */
    public int getRoll() { return m_roll; }

    /**
     * Setter for the roll.
     * <p>
     * This can be used if the table is more of a lookup than a roller
     * </p>
     */
    private void setRoll(int roll) { m_roll = roll; }

    /** Holds the name of the finder if the table has one */
    private String m_finderObject = null;

    /**
     * Getter for the finder object.
     *
     * @return The finder
     */
    public String getFinderObject() { return m_finderObject; }

    /**
     * Setter for the finder object.
     *
     * @param finderObject The finder
     */
    private void setFinderObject(String finderObject) { m_finderObject = finderObject; }

    /** Holds the class of object the finder returns. */
    private String m_finderClass = null;

    /** Holds the key to find by in the finder. */
    private String m_finderKey = null;

    /** Holds the minimum this table can roll. */
    private int m_min = 0;

    /** Holds the maximum this table can roll. */
    private int m_max = 0;

    /** Holds whether or not this is open. */
    private boolean m_open = false;

    /** Holds the ArrayList of nodes that can be rolled. */
    private ArrayList m_nodeList = null;

    /**
     * The main constructor takes in the name of an XML file that
     * will be loaded.
     *
     * @param xFile The name of an XML file
     */
    public RollTable(File xFile) {
        Assertion.isTrue(xFile.exists(), "Need an XML file to roll against");

        try {
            loadXML(xFile);
        } catch (ParserConfigurationException e) {
            System.out.println("Eating: " + e.toString());
        } catch (SAXException e) {
            System.out.println("Eating: " + e.toString());
        } catch (IOException e) {
            System.out.println("Eating: " + e.toString());
        }

        /*
        System.out.println("Loaded from File");
        System.out.println("Min: " + m_min);
        System.out.println("Max: " + m_max);
        System.out.println("Open: " + m_open);
        System.out.println("Object: " + m_finderObject);
        System.out.println("Finder: " + m_finderClass);
        System.out.println("Key:    " + m_finderKey);
        */
    }

    /**
     * This constructor is used to load a sub-table.
     *
     * @param rootRollTableNode The top of the new table
     */
    private RollTable(Node rootRollTableNode) {
        loadRollTable(rootRollTableNode);

        /*
        System.out.println("Loaded from Node");
        System.out.println("Min: " + m_min);
        System.out.println("Max: " + m_max);
        System.out.println("Open: " + m_open);
        System.out.println("Object: " + m_finderObject);
        System.out.println("Finder: " + m_finderClass);
        System.out.println("Key:    " + m_finderKey);
        */
    }

    /**
     * This is the main method to make a roll on the table and then
     * return the object being identified.
     *
     * @return The object result of the table
     */
    public Object getNewResult() {
        ClosedPercentileDice cpd = new ClosedPercentileDice();
        cpd.roll();

        return processValue(cpd.getValue());
    }

    /** Tag for XML value of PrimaryKey. */
    private static String PRIMARY_KEY_TAG = "PrimaryKey";

    /** Tag for XML value of RollTable. */
    private static String ROLL_TABLE_TAG = "RollTable";

    /**
     * This call takes in a number inside the possibilities and then
     * pulls out the result.  If there is a sub-table stored, then it
     * will generate a random roll against the sub table.
     *
     * @param roll The roll value
     * @return The result object
     */
    public Object processValue(int roll) {
        // The list could be nodes, or it could be sub-tables
        if(m_nodeList.get(roll-1) instanceof Node) {
            Node rowNode = (Node)m_nodeList.get(roll-1);
            NodeList childRowNodes = rowNode.getChildNodes();

            for(int nodeCount=0; nodeCount < childRowNodes.getLength(); nodeCount++) {
                Node subNode = childRowNodes.item(nodeCount);

                if(PRIMARY_KEY_TAG.equals(subNode.getNodeName())) {
                    String primeKeySearch = subNode.getChildNodes().item(0).getNodeValue();
                    return findBy(primeKeySearch);
                } else if (ROLL_TABLE_TAG.equals(subNode.getNodeName())) {
                    RollTable subRollTable = new RollTable(subNode);
                    m_nodeList.set(roll-1, subRollTable);
                    return subRollTable.getNewResult();
                } else if ("#text".equals(subNode.getNodeName())) {
                }
                else {
                    System.out.println("RollTable.processValue - Got unknown tag: " + subNode.getNodeName());
                }
            }
        } else if (m_nodeList.get(roll-1) instanceof RollTable) {
            return ((RollTable)m_nodeList.get(roll-1)).getNewResult();
        }

        return null;
    }

    /**
     * This is the findBy method.  It should only be called on a table
     * that uses a finder object.  This will search the finder object
     * for the value.
     *
     * @param key The key to search for
     * @return The result object of the finder
     */
    private Object findBy(String key) {
        // Okay, let's try instantiating.
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        Assertion.isNotNull(classLoader, "Class loader can't be null");
        Assertion.isNotNull(m_finderClass, "Cannot load a null class");
        try {
            Class finder = classLoader.loadClass(m_finderClass);
            Class params[] = new Class[1];
            params[0] = String.class;

            // Grabs the methods to get reference and find
            Method instanceMethod = finder.getMethod("instance", null);
            Method findMethod = finder.getMethod("findBy" + m_finderKey, params );

            // Grabs the finder object
            Object finderObject = instanceMethod.invoke(null, null);

            // Finds the fun
            Object findName[] = new Object[1];
            findName[0] = key;
            Object returnObject = findMethod.invoke(finderObject, findName);

            return returnObject;


        } catch (ClassNotFoundException e) {
            throw new LoadErrorRX(e.toString());
        } catch (NoSuchMethodException e) {
            throw new LoadErrorRX(e.toString());
        } catch (IllegalAccessException e) {
            throw new LoadErrorRX(e.toString());
        } catch (InvocationTargetException e) {
            throw new LoadErrorRX("Couldn't find: '"+key+"'", e.getTargetException());
        }
    }

    private static String FINDER_OBJECT_TAG = "FinderObject";
    private static String ROW_TAG = "Row";
    private static String MIN_ATT = "min";
    private static String MAX_ATT = "max";
    private static String OPEN_ATT = "open";

    /**
     * This builds the roll table object out of an XML file.
     *
     * @param xFile the XML file to build from
     */
    private void loadXML(File xFile) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse( xFile );

        Element root = document.getDocumentElement();

        NodeList rollTableNodes = root.getChildNodes();

        for(int i = 0; i < rollTableNodes.getLength(); i++) {
            Node rootRollTableNode = rollTableNodes.item(i);
            if(ROLL_TABLE_TAG.equals(rootRollTableNode.getNodeName())) {
                loadRollTable(rootRollTableNode);
            }
        }
    }

    /**
     * This builds the roll table object out of <RollTable> node in
     * an XML file.
     *
     * @param rootRollTableNode The root node
     */
    private void loadRollTable(Node rootRollTableNode) {
        Assertion.isTrue(ROLL_TABLE_TAG.equals(rootRollTableNode.getNodeName()), "Only processes a node table");

        // The first node MUST be a roll table
        NodeList rollTableSubNodes = rootRollTableNode.getChildNodes();

        NamedNodeMap attributes = rootRollTableNode.getAttributes();
        m_min = Integer.parseInt(attributes.getNamedItem(MIN_ATT).getNodeValue());
        m_max = Integer.parseInt(attributes.getNamedItem(MAX_ATT).getNodeValue());
        m_open = ("TRUE" == attributes.getNamedItem(OPEN_ATT).getNodeValue());

        m_nodeList = new ArrayList( (m_max - m_min) + 1);

        // Cycle through each race element and create a race from it
        for(int nodeCount=0; nodeCount < rollTableSubNodes.getLength(); nodeCount++) {
            Node subNode = rollTableSubNodes.item(nodeCount);
            if(FINDER_OBJECT_TAG.equals(subNode.getNodeName())) {
                setFinder(subNode);
            } else if (ROW_TAG.equals(subNode.getNodeName())) {
                storeRow(subNode);
            } else if ("#text".equals(subNode.getNodeName())) {

            }
            else {
                System.out.println("Found unknown tag: " + subNode.getNodeName());
            }
        }
    }

    private static String OBJECT_CLASS_ATT = "objectClass";
    private static String FINDER_CLASS_ATT = "finderClass";
    private static String FINDER_KEY_ATT = "finderKey";

    /**
     * This is a private setter for the finder objects.
     * Pass a finder node in from an XML document.
     *
     * @param finderNode a finder node
     */
    private void setFinder(Node finderNode) {
        Assertion.isTrue(FINDER_OBJECT_TAG.equals(finderNode.getNodeName()), "setFinder must process a finder node");

        NamedNodeMap attributes = finderNode.getAttributes();

        m_finderObject  = attributes.getNamedItem(OBJECT_CLASS_ATT).getNodeValue();
        m_finderClass   = attributes.getNamedItem(FINDER_CLASS_ATT).getNodeValue();
        m_finderKey     = attributes.getNamedItem(FINDER_KEY_ATT).getNodeValue();
    }


    /**
     * This is a private method to take a row node and stick in into
     * the line.
     *
     * @param rowNode the row node
     */
    private void storeRow(Node rowNode) {
        NamedNodeMap attributes = rowNode.getAttributes();

        int min = Integer.parseInt(attributes.getNamedItem(MIN_ATT).getNodeValue());
        int max = Integer.parseInt(attributes.getNamedItem(MAX_ATT).getNodeValue());

        for(int currVal = min-1; currVal < max; currVal++) {
            // System.out.println("Adding: " + (currVal - m_min + 1));
            m_nodeList.add( currVal - m_min + 1, rowNode);
        }
    }
}