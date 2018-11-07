package models;


import edu.miracosta.cs113.Term;

import java.io.BufferedReader;
import java.util.*;

/**
 * CalculatorModel.java : Concrete class using the stack data structure to evaluate infix math expressions.
 *
 * TODO: This file given just to get code to compile (method stubbed). Make sure to implement appropriately (and remove this).
 *
 * @author Nery Chapeton-Lamas
 * @version 1.1
 *
 * @modified by Gabriel Bactol and Arthur Utnehmer (CS113)
 */
public class CalculatorModel implements CalculatorInterface
{
    //Initialize variables
    private Stack<Integer> list;
    private static final String OPERATORS = "+-*/";

    public CalculatorModel()
    {
        list = new Stack<Integer>();
    }
    public CalculatorModel(Stack<Integer> list)
    {
        this.list = list;
    }
    /**
     * An exception class that would handle thrown exceptions when the syntax of the expression is incorrect
     */
    public static class SyntaxErrorException extends Exception
    {
        SyntaxErrorException(String message)
        {
            super(message);
        }
    }


    /**
     * Takes a string formatted with parenthesis and then returns a number relative to the operations within the operations in the string.
     *
     * @param expression an arithmetic equation.
     * @return answer The solution to the equation.
     */
    public int recursiveMethodToCalculateParenthesis(String expression)
    {
        String[] expressionToSplit = expression.split("");

        int firstOccurrence = -1;
        int lastOccurrence = -1;
        String answer = "";
        String operationBeforeParenthesis = "";
        String operationAfterParenthesis = "";
        String operationInsideParenthesis = "";
        Boolean containsAParenthesis = false;

        if(expression.equals(""))
        {
            return 0;
        }
        //The index of the first parenthesis.
        for(int i = 0; i < expressionToSplit.length; i++)
        {
            if(expressionToSplit[i].equals("(" ))
            {
                containsAParenthesis = true;
                firstOccurrence = i;
                break;
            }
        }

        //if the item has a parenthesis find the location of the parenthesis and remove the info surrounding the parenthesis.
        if(containsAParenthesis)
        {
            //The index of the last parenthesis.
            for (int c = expressionToSplit.length - 1; c > 0; c--) {
                if (expressionToSplit[c].equals(")")) {
                    lastOccurrence = c;
                    break;
                }
            }

            //check if numbers are outside of the parenthesis.
            //in this case look for numbers before.
            if (firstOccurrence > 0) {
                for (int i = 0; i < firstOccurrence; i++) {
                    operationBeforeParenthesis += expressionToSplit[i];
                }

            }

            //check if numbers are outside of the parenthesis.
            //in this case look for numbers after.
            if (lastOccurrence < expressionToSplit.length - 1)
            {
                for (int i = lastOccurrence + 1; i < expressionToSplit.length; i++)
                {
                    operationAfterParenthesis += expressionToSplit[i];
                }
            }
            //save what is inside the parenthesis.
            for(int i = firstOccurrence+1; i< lastOccurrence; i++)
            {
                operationInsideParenthesis += expressionToSplit[i];
            }

        }
        //if out of the parenthatis, then return the expression.
        else if(!containsAParenthesis)
        {
            return helperArithmetic(expression);
        }

        return helperArithmetic(operationBeforeParenthesis + recursiveMethodToCalculateParenthesis(operationInsideParenthesis) + operationAfterParenthesis);

    }

    /**
     * Evaluates any values with parentheses within the expression
     *
     * @param expression unevaluated mathematical expression containing +, -, *, / and paren (all integer based)
     * @return a String representation of the equation, with the parentheses removed
     */
    @Override
    public String evaluate(String expression)
    {
        //evaluate using a recursive method.
        int answer = recursiveMethodToCalculateParenthesis(expression);
        return  Integer.toString(answer);
    }
    public boolean isOperator(char c)
    {
        return OPERATORS.indexOf(c) != -1;
    }

    /**
     * Takes a string (with and equation)and then returns an equation according to PEMDAS.
     *
     * @param expression an arithmetic equation.
     * @return answer The solution to the equation in arithmetic order.
     */
    public String sortOrderOfOperations(String expression)
    {
        Stack<Character> precedence = new Stack<Character>();
        String postfixString = "";

        expression = expression+"s";
        expression= expression.replace("+", "s+");
        expression= expression.replace("-", "s-");
        expression= expression.replace("/", "s/");
        expression= expression.replace("*", "s*");



        for (int index = 0; index < expression.length(); ++index)
        {
            char value = expression.charAt(index);

            //Add values to the precedence depending on the precedence + and - first
            if (value == '+' || value == '-')
            {
                if (precedence.isEmpty())
                {
                    precedence.push(value);
                } else
                {
                    Character operator = precedence.peek();
                    while (!(precedence.isEmpty() || operator.equals(('(')) || operator.equals((')'))))
                    {
                        operator = precedence.pop(); // Code Updated
                        postfixString += operator.charValue();
                    }
                    precedence.push(value);
                }
            }
            //theb do * and /
            else if (value == '*' || value == '/')
            {
                if (precedence.isEmpty())
                {
                    precedence.push(value);
                } else
                {
                    Character operator = precedence.peek();
                    // while condition updated
                    while (!operator.equals(('+')) && !operator.equals(('-')) && !precedence.isEmpty())
                    {
                        operator = precedence.pop(); // Code Updated
                        postfixString += operator.charValue();
                    }
                    precedence.push(value);
                }
            } else
            {
                postfixString += value;
            }
        }

        while (!precedence.isEmpty())
        {
            Character oper = precedence.peek();
            if (!oper.equals(('(')))
            {
                precedence.pop();
                postfixString += oper.charValue();
            }
        }

        postfixString = postfixString.replace("s", " ");
        postfixString = postfixString.replace("+", "+ ");
        postfixString = postfixString.replace("*", "* ");
        postfixString = postfixString.replace("-", "- ");
        postfixString = postfixString.replace("/", "/ ");
        postfixString = postfixString.trim();

        return postfixString;
    }

    /**
     * Takes a string and then returns a number relative to the operations within the operations in the string.
     *
     * @param expression an arithmetic equation.
     * @return answer The solution to the equation.
     */
    private int helperArithmetic(String expression)
    {

        int answer = 0;
        //remove spaces and add to the infix to postfix algorithm.

        expression = expression.replace(" ", "").trim();
        expression = this.sortOrderOfOperations(expression);
        System.out.println(expression);


        //stack for operands
        Stack<Integer>operandStack = new Stack<Integer>();

        //process each token.
        String[] tokens = expression.split("\\s");

        try
        {
            for(String nextToken: tokens)
            {
                char firstChar = nextToken.charAt(0);
                //does it start with a digit?
                if((Character.isDigit(firstChar)))
                {
                    System.out.println(nextToken);
                    //get int value.
                    int value = Integer.parseInt(nextToken);
                    //push value into operand stack.
                    list.push(value);
                }
                //is it an operator.
                else if(isOperator(firstChar))
                {
                    //evaluate.
                    int result = evalOP(firstChar);
                    //push to stack.
                    list.push(result);
                }
                else
                {
                    throw new SyntaxErrorException("Invalid syntax " + firstChar);
                }
            }

            //no more tokens.
            answer = list.pop();

            if(operandStack.isEmpty())
            {
                return answer;
            }
            else
            {
                throw new SyntaxErrorException("stack is not empty");
            }
        }

        catch(EmptyStackException e)
        {
            return answer;
        }
        catch (SyntaxErrorException e)
        {
            System.out.println("Syntax error");
        }

        return answer;
    }

    /**
     * Takes a string and then returns a number after solving the equation within the operations in the string.
     *
     * @param op the operator that would be used for the two numbers.
     * @return result, The solution to the between the two numbers.
     */
    private int evalOP(char op)
    {
        int rhs = list.pop();
        int lhs = list.pop();
        int result = 0;

        switch(op)
        {
            case '+': result = lhs + rhs;
                break;
            case '-': result = lhs - rhs;
                break;
            case '*': result = lhs * rhs;
                break;
            case '/': result = lhs / rhs;
                break;
        }
        return result;
    }

    /**
     * Takes a polynomial equation, finds the derivative for each polynomial within the equation.
     *
     * @param expression a Polynomial equation that will be used to find the derivative for
     * @return newEquation, a String representation of the expression, evaluated after finding the derivative
     */
    public String calcDerivative(String expression) {

        //removing any whitespace
        expression = expression.replaceAll(" ", "");

        //where the information of the polynomial equation will be stored
        Queue<Term> derivativeList = new LinkedList<>();

        //what holds a single polynomial within the expression
        String polynomial = "";

        //what will store the new polynomial
        String newEquation = "";

        //going through the whole expression
        for (int i = 0; i < expression.length(); i++) {

            //if it finds an operator, then it knows we have reached the next polynomial in the equation.
            //the only exception to this is if the first number is negative, so it would require the negative symbol
            //keep in mind that the exponent could be negative as well, so we do need to check whether
            //it is after a '^' symbol
            if ((expression.charAt(i) == '+' ||
                    expression.charAt(i) == '-')  && i != 0 && expression.charAt(i-1) != '^')
            {
                //a new term is created, and stored into the queue
                Term addTerm = new Term(polynomial);
                derivativeList.offer(addTerm);

                //resetting the String to insert the next polynomial
                polynomial = "";

                //adding the operator for the polynomial, positive or negative
                polynomial += expression.charAt(i);

            }
            //if it reaches the end of the String, which is the last of the last polynomial in the string
            else if (i + 1 == expression.length())
            {
                //add the operator, and add the term into the queue
                polynomial += expression.charAt(i);
                Term addTerm = new Term(polynomial);
                derivativeList.offer(addTerm);
            }
            //if it hasn't reached the next operator or end of the string, continue to add the piece of the string
            //since it hasn't finished adding the polynomial
            else
            {
                polynomial += expression.charAt(i);
            }
        }

        //while there is still objects in the queue
        while (derivativeList.peek() != null) {

            //remove the head of the queue, which was the first polynomial in the equation
            Term removeObject = derivativeList.poll();

            //if the exponent is 0, the number doesn't exist anymore
            if (removeObject.getExponent() != 0) {

                //the coefficient is multiplied by the exponent value
                removeObject.setCoefficient(removeObject.getCoefficient() * removeObject.getExponent());

                //the exponent is subtracted by 1
                removeObject.setExponent(removeObject.getExponent() - 1);

                //the toString of the term is added into the expression
                newEquation += removeObject.toString();
            }
        }

        //return the new expression, containing the polynomials with their derivatives calculated
        return newEquation;
    }

    /**
     * Mutator for list stack variable
     *
     * @param list
     *            Stack variable representing the list stack
     */
    public void setList(Stack<Integer> list)
    {
        this.list = list;
    }

    /**
     * Accessor to get the stack from the list variable
     *
     * @return the stack assigned to the variable list
     */
    public Stack<Integer> getList()
    {
        return list;
    }

    /**
     * Accessor to get the OPERATORS string
     *
     * @return the string within the OPERATORS variable
     */
    public String getOperators()
    {
        return OPERATORS;
    }

    /**
     * Equals method checks ALL instance variables are equal between two CalculatorModel objects
     */
    public boolean equals(Object obj)
    {
        //checking whether the object compared to are even CalculatorModels
        if(obj instanceof CalculatorModel)
        {
            //checking the instance variables
            if(this.getList().equals(((CalculatorModel)obj).getList()) &&
                this.getOperators().equals(((CalculatorModel)obj).getOperators()))
            {
                return true;
            }
        }
        //by default, it is false
        return false;
    }

    /**
     * toString representing this CalculatorModels values
     *
     * @return formatted string of what was within the stack
     */
    public String toString()
    {
        ArrayList<Integer> temp = new ArrayList<>();
        String info = "From the inside of the stack: \r\n";
        while(!list.empty())
        {
            temp.add(list.pop());
        }
        for(int i = 0; i <temp.size();i++)
        {
            info += temp.get(temp.size() -1 - i) + " ";
            list.push(temp.get(temp.size() - 1 - i));
        }
        return info;
    }

}
