package tester.com.chaosserver.data;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.chaosserver.data.JavaBeanLoader;
import com.chaosserver.data.JavaBeanWriter;
import com.chaosserver.merp.rules.magic.SpellCategory;
import com.chaosserver.merp.rules.magic.SpellCategoryFinder;
import com.chaosserver.merp.rules.race.Race;
import com.chaosserver.merp.rules.race.RaceFinder;
import com.chaosserver.merp.tester.MerpTesterBase;

import org.w3c.dom.Document;
import org.apache.crimson.tree.ElementNode;
import org.apache.crimson.tree.XmlWriteContext;

public class JavaBeanWriterTester extends MerpTesterBase {
	protected JavaBeanWriter javaBeanWriter;

	public JavaBeanWriterTester(String name) {
		super(name);
	}

    public static Test suite() {
        TestSuite suite = new TestSuite(JavaBeanWriterTester.class);

        return suite;
    }

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


	public void writeXml(Document document) throws Exception {
		PrintWriter pw = new PrintWriter(System.out);
		XmlWriteContext context = new XmlWriteContext(pw, 0);
		ElementNode root = (ElementNode) document.getDocumentElement();
		if(root != null) {
			root.writeXml(context);
		}
		else {
			System.out.println("Root is null!");
		}
		System.out.flush();
		pw.flush();
	}
}