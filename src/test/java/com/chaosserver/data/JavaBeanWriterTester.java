package com.chaosserver.data;

import com.chaosserver.merp.tester.MerpTesterBase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.w3c.dom.Document;

public class JavaBeanWriterTester extends MerpTesterBase {
	protected JavaBeanWriter javaBeanWriter;

	public JavaBeanWriterTester(String name) {
		super(name);
	}

    public static Test suite() {
        TestSuite suite = new TestSuite(JavaBeanWriterTester.class);

        return suite;
    }

    @Override
    public void setUp() {
		javaBeanWriter = new JavaBeanWriter();
	}

    public void testInteger() throws Exception {
		Object origObject = new Integer(5);
		Document document = javaBeanWriter.getDom(origObject);
		Object finalObject = JavaBeanLoader.getBean(document);

		if(!origObject.equals(finalObject)) {
			fail("Objects do not match!");
		}
	}

    public void testString() throws Exception {
		Object origObject = "Big Test!";
		Document document = javaBeanWriter.getDom(origObject);
		Object finalObject = JavaBeanLoader.getBean(document);

		if(!origObject.equals(finalObject)) {
			fail("Objects do not match!");
		}
	}

	public void testMap() throws Exception {
		Map origObject = new HashMap();
		origObject.put("test", "blah");
		origObject.put("test2", "blah");

     	Document document = javaBeanWriter.getDom(origObject);
		Object finalObject = JavaBeanLoader.getBean(document);

		if(!origObject.equals(finalObject)) {
			fail("Objects do not match!");
		}
	}

	public void testCollection() throws Exception {
		Collection origObject = new ArrayList();
		origObject.add("test");
		origObject.add("test2");

     	Document document = javaBeanWriter.getDom(origObject);
		Object finalObject = JavaBeanLoader.getBean(document);

		if(!origObject.equals(finalObject)) {
			fail("Objects do not match!");
		}
	}


    // This method uses specific classes from Crimson, which is
    // deprecated and doesn't play nice with modern Java.
    // Commented out for now, will look into the possibility
    // of making this work later.

//	public void writeXml(Document document) throws Exception {
//		PrintWriter pw = new PrintWriter(System.out);
//		XmlWriteContext context = new XmlWriteContext(pw, 0);
//		ElementNode root = (ElementNode) document.getDocumentElement();
//		if(root != null) {
//			root.writeXml(context);
//		}
//		else {
//			System.out.println("Root is null!");
//		}
//		System.out.flush();
//		pw.flush();
//	}
}