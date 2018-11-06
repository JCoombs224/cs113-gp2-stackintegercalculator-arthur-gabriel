package models;


import java.util.*;

/**
 * CalculatorModel.java : Concrete class using the stack data structure to evaluate infix math expressions.
 *
 * TODO: This file given just to get code to compile (method stubbed). Make sure to implement appropriately (and remove this).
 *
 * @author Nery Chapeton-Lamas
 * @version 1.0
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


    public int recursiveMethodToCalculateParenthesis(String expression)
    {
        String[] expressionToSplit = expression.split("");

        int firstOccurrence = -1;
        int lastOccurrence = -1;

        //The index of the first parenthesis.
        for(int i = 0; i < expressionToSplit.length; i++)
        {
            if(expressionToSplit[i].equals("(" ))
            {
                firstOccurrence = i;
                break;
            }
        }

        //The index of the last parenthesis.
        for(int c = expressionToSplit.length-1; c > 0; c--)
        {
            if(expressionToSplit[c].equals(")"))
            {
                lastOccurrence = c;
                break;
            }
        }

        String operationBeforeParenthesis = "";
        String operationAfterParenthesis = "";
        //check if numbers are outside of the parenthesis.
        //in this case look for numbers before.
        if(firstOccurrence > 0)
        {
            for(int i = 0; i < firstOccurrence; i++)
            {
                operationBeforeParenthesis += expressionToSplit[i];
            }

        }

        //check if numbers are outside of the parenthesis.
        //in this case look for numbers after.
        if(lastOccurrence < expressionToSplit.length-1)
        {

            for(int i = lastOccurrence+1; i < expressionToSplit.length; i++)
            {
                operationAfterParenthesis  += expressionToSplit[i];
            }
        }

        //add these operations to the bottom of the stack.
        listToProcess.add(operationBeforeParenthesis);
        listToProcess.add(operationAfterParenthesis);

    }


    @Override
    public String evaluate(String expression)
    {
        String[] expressionToSplit = expression.split("");





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
}
