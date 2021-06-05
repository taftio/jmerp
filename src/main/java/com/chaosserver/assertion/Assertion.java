package com.chaosserver.assertion;

/**
 * This is a basic assertion class.  Assertions are a programming
 * style that not everyone uses.  When an assertion fails it is
 * generally due to either a setup problem or a programming bug
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class Assertion {
    /**
     * Asserts that a value is true.
     *
     * @param b A boolean expression
     * @param s description of the assertion
     * @throws AssertionFailedRX If the assertion fails
     */
    public static void isTrue(boolean b, String s) {
        if(b == false) {
            throw new AssertionFailedRX(s);
        }
    }

    /**
     * Asserts that a value is false.
     *
     * @param b A boolean expression
     * @param s description of the assertion
     * @throws AssertionFailedRX If the assertion fails
     */
    public static void isFalse(boolean b, String s) {
        isTrue(!b, s);
    }

    /**
     * Asserts that an object is not null.
     *
     * @param obj An object reference
     * @param s description of the assertion
     * @throws AssertionFailedRX
     */
    public static void isNotNull(Object obj, String s) {
        if(obj == null) {
            throw new AssertionFailedRX(s);
        }
    }

    /**
     * There is an assertion used to test if an XML node is named what
     * is expected.  It standardizes the string output if the assertion
     * failes
     *
     * @param s1 The constant name of the node
     * @param s2 The name of the node loaded from file
     * @throws AssertionFailedRX Occurs if either node isn't equal or if either node is null
     */
    public static void isNodeNamed(String s1, String s2) {
        try {
            if( ! s1.equals(s2) ) {
                throw new AssertionFailedRX("This node only processes <"
                    + s1 + ">, but the node passed in is <"
                    + s2 + ">");
            }
        } catch (NullPointerException e) {
            throw new AssertionFailedRX("NullPointerException trying "
                + "to process node");
        }
    }

    /**
     * This assertion should only be placed where code should never be
     * reached.  If this assertion is reached, it always fails
     *
     * @param s description of the assertion
     * @throws AssertionFailedRX Always thrown
     */
    public static void shouldNotReach(String s) {
        throw new AssertionFailedRX(s);
    }

}