package com.chaosserver.merp.tester.rules;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class JUXMLTester extends TestCase {
    public JUXMLTester(String name) {
        super(name);
    }

    public static Test suite() {
        /**
        TestSuite suite= new TestSuite();
        suite.addTest(
            new JUXMLTester("XMLLoad") {
                protected void runTest() { testXML(); }
            }
        );
        **/
        TestSuite suite = new TestSuite(JUXMLTester.class);

        return suite;
        // return new TestSuite(JURaceListTester.class);
    }

    public void testXML() {
        DocumentBuilderFactory factory =
           DocumentBuilderFactory.newInstance();
        try {
           DocumentBuilder builder = factory.newDocumentBuilder();
           Document document = builder.parse( new File("\\javadev\\test.xml") );

            // Okay, start by getting root element, eh?
            Element root = document.getDocumentElement();
            // System.out.println("Name: " + root.getTagName() );

        } catch (SAXParseException e) {
            e.printStackTrace();
            fail(e.toString());

        } catch (SAXException e) {
           e.printStackTrace();
           fail(e.toString());

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            fail(e.toString());

        } catch (IOException e) {
           e.printStackTrace();
           fail(e.toString());
        }

    }

}