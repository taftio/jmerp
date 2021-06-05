package com.chaosserver.merp.character.attribute;

import com.chaosserver.merp.tester.MerpTesterBase;
import junit.framework.Test;
import junit.framework.TestSuite;

public class ExtendedAttributeTester extends MerpTesterBase {
	public ExtendedAttributeTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(ExtendedAttributeTester.class);

        return suite;
    }

	public void testConstructor1() {
		ExtendedAttributeGeneric extendedAttribute = new ExtendedAttributeGeneric();

		if(extendedAttribute.getName() != null) {
			fail("Empty constructor produced a non-null name");
		}

		if(extendedAttribute.getAttribute() != null) {
			fail("Empty constructor produced a non-null attribute");
		}

		if(extendedAttribute.getExpiration() != 0) {
			fail("Empty constructor produced a non-null expiration");
		}
	}

	public void testConstructor2() {
		String name = "NAME";
		Object attribute = new Integer(5);

		ExtendedAttributeGeneric extendedAttribute = new ExtendedAttributeGeneric(name, attribute);

		if(!name.equals(extendedAttribute.getName())) {
			fail("Constructor failed to set name");
		}

		if(!attribute.equals(extendedAttribute.getAttribute())) {
			fail("Constructor failed to set attribute");
		}

		if(extendedAttribute.getExpiration() != 0) {
			fail("Empty constructor produced a non-null expiration");
		}
	}

	public void testConstructor3() {
		String name = "NAME";
		Object attribute = new Integer(5);
		int expiration = 5;

		ExtendedAttributeGeneric extendedAttribute = new ExtendedAttributeGeneric(name, attribute, expiration);

		if(!name.equals(extendedAttribute.getName())) {
			fail("Constructor failed to set name");
		}

		if(!attribute.equals(extendedAttribute.getAttribute())) {
			fail("Constructor failed to set attribute");
		}

		if(extendedAttribute.getExpiration() != expiration) {
			fail("Constructor failed to set expiration");
		}
	}

	public void testSetGetName() {
		String name = "NAME";

		ExtendedAttributeGeneric extendedAttribute = new ExtendedAttributeGeneric();

		extendedAttribute.setName(name);
		if(!name.equals(extendedAttribute.getName())) {
			fail("Setter/Getter failed on name");
		}
	}

	public void testSetGetAttribute() {
		String attribute = "NAME";

		ExtendedAttributeGeneric extendedAttribute = new ExtendedAttributeGeneric();

		extendedAttribute.setAttribute(attribute);
		if(!attribute.equals(extendedAttribute.getAttribute())) {
			fail("Setter/Getter failed on attribute");
		}
	}

	public void testSetGetExpiration() {
		int expiration = 5;

		ExtendedAttributeGeneric extendedAttribute = new ExtendedAttributeGeneric();

		extendedAttribute.setExpiration(expiration);
		if(expiration != extendedAttribute.getExpiration()) {
			fail("Setter/Getter failed on expiration");
		}
	}

}