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
}
