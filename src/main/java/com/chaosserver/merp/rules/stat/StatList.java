package com.chaosserver.merp.rules.stat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * The main stat list is a singleton object.  It holds a list of all the stats with information on them.
 * It loads the information for the stat list out of an xml configuration file.
 * <p>
 * This list was created before the BeanLoader, and loads itself instead of using the more
 * generic method.
 * </p>
 *
 * @author Jordan Reed
 * @version 1.1
 * @since The beginning
 */

public class StatList implements IStatConstants {
    /** XML Tag for the stat ID. */
    private static String ID_ATT = "ID";

    /** XML Tag for min value. */
    private static String MIN_ATT = "min";

    /** XML Tag for maximum adjustment. */
    private static String MAX_ADJUST_ATT = "adjust";

    /** XML Tag for the stat name. */
    private static String NAME_TAG = "Name";

    /** XML Tag for the stat abbreviation. */
    private static String ABBR_TAG = "Abbr";

    /** XML Tag for the swappable property. */
    private static String SWAPPABLE_TAG = "Swappable";

    /** XML Tag for totaled property. */
    private static String TOTALED_TAG = "Totaled";

    /** Value of the default minimum total. */
    private static int s_defaultTotal = 0;

    /** Singleton self reference. */
    private static StatList s_self;

    /** The actual collection of stats. */
    private ArrayList m_statList;

    /** This is the minumum that a the totalled list is allowed to be. */
    private int m_minTotal = 0;

    /**
     * The construct can only be called by it's own method.  If the expected
     * XML file is present it will load the statlist out of it, else it will
     * load a default stat list.
     */
    private StatList() {
        m_statList = new ArrayList(7);
        if(isDataFilePresent()) {
            // Load information from this datafile
            try {
                loadXML( new File(System.getProperty("MERP_HOME")+"\\data\\game\\stat", "StatList.xml") );
            } catch (ParserConfigurationException e) {
                System.out.println("Eating: " + e);
            } catch (SAXException e) {
                System.out.println("Eating: " + e);
            } catch (IOException e) {
                System.out.println("Eating: " + e);
            }


        } else {
            m_statList.add(STRENGTH, new Stat(STRENGTH, "Strength", "ST"));
            m_statList.add(AGILITY, new Stat(AGILITY, "Agility", "AG"));
            m_statList.add(CONSTITUTION, new Stat(CONSTITUTION, "Constitution", "CO"));
            m_statList.add(INTELLIGENCE, new Stat(INTELLIGENCE, "Intelligence", "IG"));
            m_statList.add(INTUITION, new Stat(INTUITION, "Intuition", "IT"));
            m_statList.add(PRESENCE, new Stat(PRESENCE, "Presence", "PR"));
            m_statList.add(APPEARANCE, new Stat(APPEARANCE, "Appearance", "ST"));
        }
    }

    /**
     * Checks for the existence of the xml configuration file.
     *
     * @return Does the file exist
     */
    private boolean isDataFilePresent() {
        File f = new File(System.getProperty("MERP_HOME")+"\\data\\game\\stat", "StatList.xml");
        if (f.exists()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * The method to load the statlist from the XML file.
     *
     * @param xFile The File object containing the xml
     */
    private void loadXML(File xFile) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse( xFile );

        Element root = document.getDocumentElement();

        try {
            setMinTotal(root.getAttributes().getNamedItem(MIN_ATT).getNodeValue());
        } catch (NullPointerException e) {
            // If they've left out the min total and no DTD is present
            setMinTotal(s_defaultTotal);
        }

        NodeList stats = root.getElementsByTagName("Stat");
        for(int i=0; i < stats.getLength(); i++) {
            Node currStat = stats.item(i);
            int statID = -1;
            int minVal = -1;
            int maxAdjust = -1;
            String statName = null;
            String statAbbr = null;
            boolean isSwappable = false;
            boolean isTotaled = false;

            // Get desired stat attributes
            statID = Integer.parseInt(currStat.getAttributes().getNamedItem(ID_ATT).getNodeValue());

            try {
                minVal = Integer.parseInt(currStat.getAttributes().getNamedItem(MIN_ATT).getNodeValue());
            } catch (NullPointerException e) {
                minVal = -1;
            }

            try {
                maxAdjust = Integer.parseInt(currStat.getAttributes().getNamedItem(MAX_ADJUST_ATT).getNodeValue());
            } catch (NullPointerException e) {
                maxAdjust = -1;
            }

            // Cycle through this stats nodes
            NodeList currStatElems = currStat.getChildNodes();
            for(int j=0; j < currStatElems.getLength(); j++) {
                if(NAME_TAG.equals(currStatElems.item(j).getNodeName())) {
                    statName = currStatElems.item(j).getChildNodes().item(0).getNodeValue();
                } else if (ABBR_TAG.equals(currStatElems.item(j).getNodeName())) {
                    statAbbr = currStatElems.item(j).getChildNodes().item(0).getNodeValue();
                } else if (SWAPPABLE_TAG.equals(currStatElems.item(j).getNodeName())) {
                    isSwappable = true;
                } else if (TOTALED_TAG.equals(currStatElems.item(j).getNodeName())) {
                    isTotaled = true;
                } else if ("#text".equals(currStatElems.item(j).getNodeName())) {
                } else {
                    System.out.println("StatList.loadXML - Unknown Tag: "+ currStatElems.item(j).getNodeName());
                    //System.out.println(currStatElems.item(j).getChildNodes().item(0).getNodeValue());
                }
            }

            m_statList.add(statID, new Stat(statID, statName, statAbbr, isSwappable, isTotaled, minVal, maxAdjust));
        }


    }

    /**
     * Singleton method that is used to get the one instance of this object in the system.
     *
     * @return The one instance of this object
     */
    synchronized public static StatList instance() {
        if(s_self==null) {
            s_self = new StatList();
        }

        return s_self;
    }

    /**
     * Gets an iterator over the collection.
     *
     * @return The iterator
     */
    public StatListIterator iterator() {
      return new StatListIterator(m_statList.listIterator());
    }

    /**
     * Getter for the minimum total.
     *
     * @return The min total
     * @see #setMinTotal
     */
    public int getMinTotal() {
        return m_minTotal;
    }

    /**
     * Setter for the min total.
     *
     * @param minTotal The minimum total
     * @see #getMinTotal
     */
    public void setMinTotal(int minTotal) {
        m_minTotal = minTotal;
    }

    /**
     * Setter for the minumum total.  If null or a non-string is passed
     * in, then minTotal is set to the default.
     *
     * @param minTotal A string containing the min total
     * @see #getMinTotal
     */
    public void setMinTotal(String minTotal) {
        setMinTotal(Integer.parseInt(minTotal));
    }

    /**
     * Returns the number of stats in the list.
     *
     * @return the number of elements in the list
     */
    public int size() {
        return m_statList.size();
    }
}