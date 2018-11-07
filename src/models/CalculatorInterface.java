package models;

/**
 * CalculatorInterface.java : Interface for calculator model (back-end)
 *
 * @author Nery Chapeton-Lamas
 * @version 1.1
 *
 * @Modified: Gabriel Bactol and Arthur Utnehmer (CS113)
 */
public interface CalculatorInterface{

    /**
     * Takes an infix expression and, enforcing operator precedence, evaluates it using the stack data structure.
     *
     * @param expression unevaluated mathematical expression containing +, -, *, / and paren (all integer based)
     * @return a String representation of the expression evaluated, using operator precedence and enforcing parens
     */
    public String evaluate(String expression);

    /**
     * Takes a polynomial equation, and makes finds the derivative for each polynomial.
     *
     * @param expression a Polynomial equation that the method calcDerivative() will find the derivative for
     * @return a String representation of the expression, evaluated after finding the derivative
     */
    public String calcDerivative(String expression);
}
