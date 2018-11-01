package models;


import java.util.EmptyStackException;
import java.util.Stack;

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
    public String evaluate(String expression){
        String[] tokens = expression.split("\\s+");
        try
        {
            for(String nextToken: tokens)
            {
                char firstChar = nextToken.charAt(0);

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
                    throw new SyntaxErrorException("hello");
                }
            }
            int answer = list.pop();
            if(list.empty())
            {
                return String.valueOf(answer);
            }
            else
            {
                throw new SyntaxErrorException("hello");
            }
        }
        catch(EmptyStackException e)
        {
            throw new SyntaxErrorException("hello");
        }


        return "NaN";
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
