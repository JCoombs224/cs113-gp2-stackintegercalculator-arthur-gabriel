package models;


import edu.miracosta.cs113.Term;

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
public class CalculatorModel implements CalculatorInterface {

    public Stack<Integer> list = new Stack<Integer>();
    private static final String OPERATORS = "+-*/";

    public static class SyntaxErrorException extends Exception
    {
        SyntaxErrorException(String message)
        {
            super(message);
        }
    }



    @Override
    public String evaluate(String expression)
    {
        StringTokenizer calculation = new StringTokenizer(expression, " ");

        /*
        String number1,number2 = "";
        String operator = "";
        String withParent = "";
        int totalNumber = 0;
        while(g.charAt(i) != ')')
        {
            withParent += g.charAt(i);
        }
        withParent.replaceAll(" ", "");
        if(withParent.length() > 2)
        {
            for(int i = 0; i < withParent.length();i++)
            {
                if(Character.isDigit(withParent.charAt(i)) && operator.equals(""))
                {
                    number1 += withParent.charAt(i);
                }
                if(Character.isDigit(withParent.charAt(i)) && !operator.equals(""))
                {
                    number2 += withParent.charAt(i);
                }
                else if(!Character.isDigit(withParent.charAt(i)))
                {
                    if(!operator.equals(""))
                    {
                        if(operator.equals("+"))
                        {
                            totalNumber = Integer.parseInt(number1) + Integer.parseInt(number2);
                        }
                        else if(operator.equals("-"))
                        {
                            totalNumber = Integer.parseInt(number1) - Integer.parseInt(number2);
                        }
                        else if(operator.equals("*"))
                        {
                            totalNumber = Integer.parseInt(number1) - Integer.parseInt(number2);
                        }
                        else if(operator.equals("/"))
                        {
                            totalNumber = Integer.parseInt(number1) / Integer.parseInt(number2);
                        }

                        number1 = String.valueOf(totalNumber);
                        number2 = "";
                    }

                    operator = "";
                    operator += withParent.charAt(i);
                }
                if(i+1 == withParent.length() && !number2.equals("") && !operator.equals(""))
                {
                    if(operator.equals("+"))
                    {
                        totalNumber = Integer.parseInt(number1) + Integer.parseInt(number2);
                    }
                    else if(operator.equals("-"))
                    {
                        totalNumber = Integer.parseInt(number1) - Integer.parseInt(number2);
                    }
                    else if(operator.equals("*"))
                    {
                        totalNumber = Integer.parseInt(number1) - Integer.parseInt(number2);
                    }
                    else if(operator.equals("/"))
                    {
                        totalNumber = Integer.parseInt(number1) / Integer.parseInt(number2);
                    }
                }

            }
        }*/





        //Proof of concept.
        int answer = helperArithmetic(expression);

        return String.valueOf(answer);
    }
    public boolean isOperator(char c)
    {
        return OPERATORS.indexOf(c) != -1;
    }

    private int helperArithmetic(String expression)
    {
        //stack of operations and stack of integers.
        Queue<String> queueOfOperations = new LinkedList<>();
        Queue<Integer> queueOfIntegers = new LinkedList<>();

        int answer = 0;
        StringTokenizer calculation = new StringTokenizer(expression, " ");

        //separate operations and numbers into queues.
        while (calculation.hasMoreTokens())
        {
            String nextToken = calculation.nextToken();
            nextToken = nextToken.trim();
            char firstChar = nextToken.charAt(0);

            if(Character.isDigit(firstChar))
            {
                queueOfIntegers.add(Integer.parseInt(nextToken));
            }
            else if(isOperator(firstChar))
            {
                queueOfOperations.add(nextToken);
            }
        }
        try
        {
            //while neither are empty.
            boolean firstNumberAdded = false;
            boolean secondNumberAdded = false;

            //add first number to the stack.
            String nextTokenTmp = queueOfIntegers.poll().toString();
            nextTokenTmp = nextTokenTmp.trim();
            list.push(Integer.parseInt(nextTokenTmp));

            while ((!queueOfOperations.isEmpty()) )
            {
                String nextToken = "";
                char firstChar = '1';
                //if we have not added a first number.
                if(!queueOfIntegers.isEmpty())
                {
                    nextToken = queueOfIntegers.poll().toString();
                    nextToken = nextToken.trim();
                    firstChar = nextToken.charAt(0);
                    //add digit to the stack.
                    int value = Integer.parseInt(nextToken);
                    list.push(value);
                }
                //lastly, poll the operations.
                nextToken = queueOfOperations.poll();
                nextToken = nextToken.trim();
                firstChar = nextToken.charAt(0);

                //if a command, calculate.
                if(isOperator(firstChar))
                {
                    int result = evalOP(firstChar);
                    list.push(result);
                }
                else
                {
                    throw new SyntaxErrorException("Please enter an allowable character");
                }
            }

            answer = list.pop();
            if(list.empty())
            {
                return answer;
            }
            else
            {
                throw new SyntaxErrorException("Stack is not empty, did not process all characters");
            }
        }
        catch(EmptyStackException e)
        {
            System.out.println("Stack is empty");
        }
        catch (SyntaxErrorException e)
        {
            System.out.println("Syntax error");
        }

        return answer;
    }
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
}
