package edu.miracosta.cs113;

import models.CalculatorModel;
import org.junit.Test;
import views.CalculatorView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * CalculatorTest.java: This class checks whether the program outputs the right number after specific inputs are
 * given to the calculator.
 * @author Gabriel Bactol & Arthur Utnehmur
 * @version 1.0
 */
public class CalculatorTest
{
    //this is used to test a calculator's basic operations
    private static final String SMALL_ADDITION = "5 + 10";
    private static final String SMALL_SUBTRACTION = "13 - 8";
    private static final String SMALL_MULTIPLICATION = "4 * 4";
    private static final String SMALL_DIVISION = "6 / 3";

    //similar to the small tests, just with bigger numbers
    private static final String BIG_ADDITION = "30 + 75";
    private static final String BIG_SUBTRACTION = "40 - 27";
    private static final String BIG_MULTIPLICATION = "80 * 6";
    private static final String BIG_DIVISION = "81 / 9";

    //used to test the evaluate method in the CalculatorModel class, having more than one operations
    private static final String MIXED_EQUATION1 = "3 * 4(6 + 2) - 8 / 2";
    private static final String MIXED_EQUATION2 = "4 / 2 + 6 * 10 / (2 + 3)";
    private static final String MIXED_EQUATION3 = "5 + 3( 4( 5 - 3 ) ) - 8 + 2";


    //tests the additional feature of the program, being the derivative
    private static final String DERIVATIVE_POSITIVE = "3x^10+4x^7+5x^4+3";
    private static final String DERIVATIVE_NEGATIVE = "6x^-12+5x^-3";
    private static final String DERIVATIVE_POSITIVE_NEGATIVE = "2x^12+5x^3-3x^-2-4x^-4";
    private static final String DERIVATIVE_ZERO = "6x^4+5";


    /**
     * Tests basic addition within the calculator
     */
    @Test
    public void testSmallAddition()
    {
        CalculatorModel test = new CalculatorModel();

        assertEquals("15", test.evaluate(SMALL_ADDITION));
    }
    /**
     * Tests basic subtraction within the calculator
     */
    @Test
    public void testSmallSubtraction()
    {
        CalculatorModel test = new CalculatorModel();

        assertEquals("5", test.evaluate(SMALL_SUBTRACTION));
    }
    /**
     * Tests basic multiplication within the calculator
     */
    @Test
    public void testSmallMultiplication()
    {
        CalculatorModel test = new CalculatorModel();

        assertEquals("16", test.evaluate(SMALL_MULTIPLICATION));
    }
    /**
     * Tests basic division within the calculator
     */
    @Test
    public void testSmallDivision()
    {
        CalculatorModel test = new CalculatorModel();

        assertEquals("2", test.evaluate(SMALL_DIVISION));
    }
    /**
     * Tests basic addition within the calculator
     */
    @Test
    public void testBigAddition()
    {
        CalculatorModel test = new CalculatorModel();

        assertEquals("105", test.evaluate(BIG_ADDITION));
    }
    /**
     * Tests basic subtraction within the calculator
     */
    @Test
    public void testBigSubtraction()
    {
        CalculatorModel test = new CalculatorModel();

        assertEquals("13", test.evaluate(BIG_SUBTRACTION));
    }
    /**
     * Tests basic multiplication within the calculator
     */
    @Test
    public void testBigMultiplication()
    {
        CalculatorModel test = new CalculatorModel();

        assertEquals("480", test.evaluate(BIG_MULTIPLICATION));
    }
    /**
     * Tests basic division within the calculator
     */
    @Test
    public void testBigDivision()
    {
        CalculatorModel test = new CalculatorModel();

        assertEquals("9", test.evaluate(BIG_DIVISION));
    }
    /**
     * Test calculating an equation with various operations within it
     */
    @Test
    public void testMixedOperations1()
    {
        CalculatorModel test = new CalculatorModel();

        assertEquals("92", test.evaluate(MIXED_EQUATION1));
    }
    /**
     * Test calculating another equation with various operations within it
     */
    @Test
    public void testMixedOperations2()
    {
        CalculatorModel test = new CalculatorModel();

        assertEquals("14", test.evaluate(MIXED_EQUATION2));
    }
    /**
     * Test calculating an equation with various operations within it, this time parentheses within parentheses
     */
    @Test
    public void testMixedOperations3()
    {
        CalculatorModel test = new CalculatorModel();

        assertEquals("23", test.evaluate(MIXED_EQUATION3));
    }

    /**
     * Tests derivative of an equation that has positive numbers
     */
    @Test
    public void testDerivativePositive()
    {
        CalculatorModel test = new CalculatorModel();

        assertEquals("+30x^9+28x^6+20x^3", test.calcDerivative(DERIVATIVE_POSITIVE));
    }
    /**
     * Tests derivative of an equation that has negative numbers
     */
    @Test
    public void testDerivativeNegative()
    {
        CalculatorModel test = new CalculatorModel();

        assertEquals("-72x^-13-15x^-4", test.calcDerivative(DERIVATIVE_NEGATIVE));
    }
    /**
     * Tests derivative of an equation that has positive and negative numbers
     */
    @Test
    public void testDerivativePositiveNegative()
    {

        CalculatorModel test = new CalculatorModel();

        assertEquals("+24x^11+15x^2+6x^-3+16x^-5", test.calcDerivative(DERIVATIVE_POSITIVE_NEGATIVE));
    }

    /**
     * Tests the derivative of an equation, having a number with no exponent, which should remove it from the
     * evaluated equation.
     */
    @Test
    public void testDerivativeZero()
    {
        CalculatorModel test = new CalculatorModel();

        assertEquals("+24x^3", test.calcDerivative(DERIVATIVE_ZERO));
    }

}
