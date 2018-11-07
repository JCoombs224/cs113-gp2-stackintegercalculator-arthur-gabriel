/**
 * TermTest.java : JUnit test for Term class.  Tests are organized in the strategic
 * development process for the class only, running the entire JUnit Tester does not
 * guarantee order!
 *
 * DO NOT CHANGE ANY CODE HERE! THIS WILL BE USED FOR GRADING! Definitely use this
 * as a guide to help you program Term and account for all situations. You can
 * comment code out (otherwise your code may not compile as you build the class!)
 *
 * @author  Nery Chapeton-Lamas <nchapetonLamas@miracosta.edu>
 * @version 1.0
 */

package edu.miracosta.cs113;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 * TermTest.java: Tests whether the term class functions properly with the methods and information
 * stored within the class.
 *
 * @author Nery Chapeton-Lamas
 * @version 1.1
 *
 * @Modified: Gabriel Bactol and Arthur Utnehmer (CS113)
 * Note: Nothing was changed, it was modified for more documentation
 */
public class TermTest {

    //if TERMS array changed, make sure to update COEFFICIENTS and/or EXPONENTS array
    private static final String[] TERMS = {"", "+5", "-3", "+x", "-x",
            "+6x", "-9x", "+x^3", "-x^5", "+x^-2",
            "-x^-7", "+7x^4", "-2x^13", "+25x^-8", "-54x^-17"};
    private static final int[] COEFFICIENTS =   {0, 5, -3, 1, -1, 6, -9, 1, -1, 1, -1, 7, -2, 25, -54};
    private static final int[] EXPONENTS =      {3, 0,  0, 1,  1, 1,  1, 3, 5, -2, -7, 4, 13, -8, -17};

    private static int COMPARE_TO_VALUE = 3; //if this is changed, make sure to change array COMPARE_TO_VALUES
    private static final int[] COMPARE_TO_VALUES = {0, 1, 1, 1, 1, 1, 1, 0, -1, 1, 1, -1, -1, 1, 1};
    private static final int ORIGINAL_EQUALS_TEST = 0;
    private static final int ORIGINAL_BEFORE_TEST = -1;
    //private static final int ORIGINAL_AFTER_TEST = 1; //not used


    /**
     * Tests whether the coefficients are set correctly using the mutator and accessor
     */
    @Test
    public void testGetSetCoefficient() {
        Term test = new Term();

        for(int c : COEFFICIENTS) {
            test.setCoefficient(c);
            assertEquals("Expected and actual coefficients DON'T match", c, test.getCoefficient());
        }
    }

    /**
     * Tests whether the exponents are set correctly using the mutator and accessor
     */
    @Test
    public void testGetSetExponent() {
        Term test = new Term();

       for(int e : EXPONENTS) {
            test.setExponent(e);
            assertEquals("Expected and actual exponents DON'T match", e, test.getExponent());
        }
    }

    /**
     * Tests whether both the coefficients and exponents are set correctly using the setAll method
     */
    @Test
    public void testSetAll() {
        Term test = new Term();
        int c, e;

        for(int i = 0; i < EXPONENTS.length; i++) {
            c = COEFFICIENTS[i];
            e = EXPONENTS[i];

            test.setAll(c, e);
            assertTrue(test.getCoefficient() == c && test.getExponent() == e);
            assertEquals("Expected and actual coefficients DON'T match", c, test.getCoefficient());
            assertEquals("Expected and actual exponents DON'T match", e, test.getExponent());
        }
    }

    /**
     * Tests whether the toString method prints the polynomial given to the method properly
     */
    @Test
    public void testToString() {
        Term test = new Term();
        String expected, actual;
        int c, e;

        for(int i = 0; i < TERMS.length; i++) {
            c = COEFFICIENTS[i];
            e = EXPONENTS[i];
            expected = TERMS[i];

            test.setAll(c, e);
            actual = test.toString();
            actual = actual.replaceAll(" ", ""); //more flexible test doesnt depend on white space

            assertEquals("Expected and actual toStrings DON'T match", expected, actual);
        }
    }

    /**
     * Tests whether the full constructor sets the coefficients and exponents properly through the full constructor
     */
    @Test
    public void testFullConstructor() {
        Term test;
        int c, e;
        String actual, expected;

        for(int i = 0; i < EXPONENTS.length; i++) {
            c = COEFFICIENTS[i];
            e = EXPONENTS[i];

            test = new Term(c, e);

            assertEquals("Expected and actual coefficients DON'T match", c, test.getCoefficient());
            assertEquals("Expected and actual exponents DON'T match", e, test.getExponent());
        }
    }

    /**
     * Tests whether the coefficient and exponent are initialize properly through the default constructor
     */
    @Test
    public void testDefaultConstructor() {
        Term test = new Term();

        assertEquals("Expected and actual coefficients DON'T match", 1, test.getCoefficient());
        assertEquals("Expected and actual exponents DON'T match", 1, test.getExponent());
    }

    /**
     * Tests whether the copy constructor successfully copies information from one term object to another, and checks
     * whether the information stored within it is correct
     */
    @Test
    public void testCopyConstructor() {
        Term original = new Term(-5, 10);
        Term copy = new Term(original);

        assertEquals("Coefficients in 'copy' does not match 'original'",
                original.getCoefficient(), copy.getCoefficient());
        assertEquals("Exponents in 'copy' does not match 'original'",
                original.getExponent(), copy.getExponent());
        assertTrue("Reference is not a unique memory address (shallow copy)",
                original != copy);
    }

    /**
     * Tests whether the coefficient and exponent variables are initialized correctly through the use of the
     * String constructor
     */
    @Test
    public void testStringConstructor() {
        Term test;
        int c, e;
        String t;

        //omitting first test in array with "" since that will throw error!
        for(int i = 1; i < TERMS.length; i++) {
            c = COEFFICIENTS[i];
            e = EXPONENTS[i];
            t = TERMS[i];

            test = new Term(t);

            assertEquals("Expected and actual coefficients DON'T match", c, test.getCoefficient());
            assertEquals("Expected and actual exponents DON'T match", e, test.getExponent());
        }
    }

    /**
     * Tests whether the equals constructor works properly by comparing objects with the same values
     */
    @Test
    public void testEqualsTrue() {
        Term test1, test2;
        int c, e;

        for(int i = 0; i < COEFFICIENTS.length; i++) {
            c = COEFFICIENTS[i];
            e = EXPONENTS[i];

            test1 = new Term(c, e);
            test2 = new Term(c, e);

            assertEquals("Expected and actual should be TRUE for equals", test1, test2);
        }
    }

    /**
     * Tests whether the equals constructor works properly by comparing objects with the different values
     */
    @Test
    public void testEqualsFalse() {
        Term test1, test2;
        int c, e;

        for(int i = 1; i < COEFFICIENTS.length; i++) {
            c = COEFFICIENTS[i];
            e = EXPONENTS[i];

            test1 = new Term(c, e);
            test2 = new Term(COEFFICIENTS[i-1], EXPONENTS[i-1]);

            assertNotEquals("Expected and actual should be FALSE for equals", test1, test2);
        }
    }

    /**
     * Tests whether the clone constructor successfully clones a Term object with the same information
     */
    @Test
    public void testClone() {
        Term original = new Term(10, -5);
        Term copy = (Term)original.clone();

        assertEquals("Coefficients in 'copy' does not match 'original'",
                original.getCoefficient(), copy.getCoefficient());
        assertEquals("Exponents in 'copy' does not match 'original'",
                original.getExponent(), copy.getExponent());
        assertTrue("Reference is not a unique memory address (shallow copy)",
                original != copy);
    }

    /**
     * Tests whether the compareTo method works properly by comparing two Term objects and their exponents
     */
    @Test
    public void testCompareTo() {
        Term original = new Term(1, COMPARE_TO_VALUE), test;
        int e, compareResult;

        for(int i = 0; i < EXPONENTS.length; i++) {
            e = EXPONENTS[i];

            test = new Term(1, e);
            compareResult = original.compareTo(test);

            if( COMPARE_TO_VALUES[i] == ORIGINAL_EQUALS_TEST) {
                assertEquals("Expected and actual compareTo result (original exponent EQUALS test exponent) DON'T match",
                        0, compareResult);
            }
            else if ( COMPARE_TO_VALUES[i] == ORIGINAL_BEFORE_TEST) {
                assertTrue("Expected and actual compareTo result (original exponent COMES BEFORE test exponent) DON'T match",
                        compareResult < 0);
            } else {
                assertTrue("Expected and actual compareTo result (original exponent COMES AFTER test exponent) DON'T match",
                        compareResult > 0);
            }

        }
    }

}