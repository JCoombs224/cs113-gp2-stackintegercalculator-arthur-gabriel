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



    @Override
    public String evaluate(String expression)
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
            //Put () at the front of the queue.
            else if(firstChar == '(')
            {
                String nextPriorityToken = calculation.nextToken();
                while ( !nextPriorityToken.equals(")") )
                {
                    nextPriorityToken = nextPriorityToken.trim();
                    firstChar = nextPriorityToken.charAt(0);

                    if(Character.isDigit(firstChar))
                    {
                        ((LinkedList<Integer>) queueOfIntegers).add(0,Integer.parseInt(nextPriorityToken) );
                    }
                    else if(isOperator(firstChar))
                    {
                        ((LinkedList<String>) queueOfOperations).add(0, nextPriorityToken);
                    }
                    nextPriorityToken = calculation.nextToken();
                }
            }
        }
        try
        {
            //while neither are empty.
            boolean firstNumberAdded = false;
            boolean secondNumberAdded = false;
            while ((!queueOfOperations.isEmpty()) )
            {
                String nextToken = "";
                char firstChar = '1';
                //if we have not added a first number.
                if(!firstNumberAdded)
                {
                    nextToken = queueOfIntegers.poll().toString();
                    nextToken = nextToken.trim();
                    firstChar = nextToken.charAt(0);
                    firstNumberAdded = true;
                }
                //if we have not added a first number.
                else if(!secondNumberAdded)
                {
                    //if a second number does not exist.
                    if(queueOfIntegers.isEmpty())
                    {
                        nextToken = queueOfOperations.poll();
                        nextToken = nextToken.trim();
                        firstChar = nextToken.charAt(0);
                        firstNumberAdded = false;
                        secondNumberAdded = false;
                    }
                    else
                     {
                        nextToken = queueOfIntegers.poll().toString();
                        nextToken = nextToken.trim();
                        firstChar = nextToken.charAt(0);
                        secondNumberAdded = true;
                     }
                }
                //lastly, poll the operations.
                else if(firstNumberAdded && secondNumberAdded)
                {
                    nextToken = queueOfOperations.poll();
                    nextToken = nextToken.trim();
                    firstChar = nextToken.charAt(0);
                    firstNumberAdded = false;
                    secondNumberAdded = false;
                }

                if(Character.isDigit(firstChar))
                {
                    int value = Integer.parseInt(nextToken);
                    list.push(value);
                }
                else if(isOperator(firstChar))
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
                return String.valueOf(answer);
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


        return String.valueOf(answer);
    }
    public boolean isOperator(char c)
    {
        return OPERATORS.indexOf(c) != -1;
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
