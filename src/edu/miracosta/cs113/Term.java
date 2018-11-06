package edu.miracosta.cs113;

import java.lang.Comparable;
/**
 * Term.java : stores values for coefficient and exponent with
 * a compareTo method that would compare the values of two
 * Term object's exponents.
 *
 * @author Gabriel Bactol (CS113)
 * @version 1.0
 *
 */
public class Term implements Comparable<Term>
{
    private int coefficient;
    private int exponent;

    /**
     * The default constructor, sets up values when there
     * are no values given in the parameters
     */
    public Term()
    {
        coefficient = 1;
        exponent = 1;
    }
    /**
     * The string constructor, that would read each individual character
     * of the String passed in, and organize it into the coefficient and
     * exponent values.
     *
     * @param term
     *            Term is an string that contains a term for polynomial,
     *            including a coefficient and exponent.
     */
    public Term(String term)
    {
        String tempCoeff;
        String tempExpo;
        tempExpo = "";
        tempCoeff = "";

        //cycles through all characters in the string
        for(int i = 0;i < term.length();i++)
        {
            //if it is currently checking a ^ character, then everything afterwards is an exponent
            if(term.charAt(i) == '^')
            {
                tempExpo = term.substring(i+1);
                i = term.length()-1;
            }
            //if the character checked is x, and there is no digit before the x character
            else if(term.charAt(i) == 'x' && !Character.isDigit(term.charAt(i-1)))
            {
                //coefficient is 1
                tempCoeff += "1";

                //if there is no ^ afterwards, then the exponent is 1
                if(i+1 == term.length())
                {
                    tempExpo ="1";
                }
            }

            //if it finds the x character, and there is nothing beyond it, the exponent is 1
            else if (term.charAt(i) == 'x' && i+1 == term.length())
            {
                    tempExpo = "1";
            }

            //if it finds there is a value other than x, it would be the coefficient
            else if(term.charAt(i) != 'x')
            {
                tempCoeff += term.charAt(i);

                //if there is no other value beyond the coefficient, the exponent would be 0
                if(i+1 == term.length())
                {
                    tempExpo +="0";
                }
            }
        }

        //set the coefficient and exponent based on what was inserted into the strings
        this.coefficient = Integer.parseInt(tempCoeff);
        this.exponent = Integer.parseInt(tempExpo);

    }

    /**
     * Full constructor, specifying each part of Term
     *
     * @param coefficient
     *            integer representing coefficient
     * @param exponent
     *            integer representing exponent
     */
    public Term(int coefficient, int exponent)
    {
        this.coefficient = coefficient;
        this.exponent = exponent;
    }

    /**
     * compareTo method, included through the use of implementing the Comparable interface
     * compares a term's exponent with the term's exponent that was just passed into the method
     *
     * @param other
     *            An object containing an exponent and coefficient, and would use
     *            their exponent value to compare this object's exponent value
     */
    public int compareTo(Term other)
    {
        //if the exponents of both term objects are equal value
        if(other.getExponent() == this.getExponent())
        {
            return 0;
        }

        //if the value of the Term object passed in has a greater exponent value than this
        //Term object's exponent
        else if(other.getExponent() > this.getExponent())
        {
            return -1;
        }

        //if the exponent of this Term object has a greater value than the Term object passed in
        else if(other.getExponent() < this.getExponent())
        {
            return 1;
        }
        return 0;
    }

    /**
     * Mutator for Coefficient value
     *
     * @param coefficient
     *            integer value representing coefficient
     */
    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }
    /**
     * Accessor to get the value for the coefficient variable in the Term class
     *
     * @return the value assigned to the variable coefficient
     */
    public int getCoefficient()
    {
        return coefficient;
    }

    /**
     * Mutator for Exponent value
     *
     * @param exponent
     *            integer value representing exponent
     */
    public void setExponent(int exponent)
    {
        this.exponent = exponent;
    }

    /**
     * Accessor to get the value for the coefficient variable in the Term class
     *
     * @return the value assigned to the variable coefficient
     */
    public int getExponent()
    {
        return exponent;
    }

    /**
     * Mutator for all values of the Term class
     *
     * @param coeff
     *            integer value representing coefficient
     * @param expo
     *            integer value representing exponent
     */
    public void setAll(int coeff, int expo)
    {
        this.setCoefficient(coeff);
        this.setExponent(expo);
    }

    /**
     * toString representing objects values
     *
     * @return formatted string of exponent and coefficient
     */
    public String toString()
    {
        //contains the Term object's information
        String listTerm;
        listTerm = "";


        if(coefficient != 0)
        {
            //if the coefficient is positive, then add a plus beside it
            if(coefficient > 0)
            {
                listTerm = "+";
                //if the coefficient is something other than 1, then add the whole number to the string
                if(coefficient != 1)
                {
                    listTerm += coefficient;
                }
            }

            //if the coefficient is negative
            else
            {
                //if the value is negative 1, add just a negative to the string
                if(coefficient == -1)
                {
                    listTerm += "-";
                }
                //if the coefficient is any other value other than negative one, then add the whole number
                else
                {
                    listTerm += coefficient;
                }

            }
        }

        //checks whether either value aren't 0 since we don't want to display any values of 0 for exponents
        //or coefficients
        if(exponent != 0 && coefficient != 0)
        {
            listTerm += "x";
            //if the exponent is greater than one, then add the whole exponent to the string
            if(exponent != 1)
            {
                listTerm += "^" + exponent;
            }
        }
        return listTerm;
    }

    /**
     * Equals method checks ALL instance variables are equal
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj)
    {
        //checking whether the object passed in is a Term object
        if(obj instanceof Term)
        {
            //Checks individual values of this Term object and the one passed in, and
            //determines whether the values are the same
            if(((Term) obj).getCoefficient() == this.getCoefficient() &&
            ((Term) obj).getExponent() == this.getExponent())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        return false;
    }

    /**
     * Copy constructor, deep copies Term object
     *
     * @param original
     *            used to get all Term parts and deep copy
     */
    public Term(Term original)
    {
        if(original == null)
        {
            System.out.println("Error");
            System.exit(0);
        }
        this.exponent = original.exponent;
        this.coefficient = original.coefficient;
    }
    /**
     * Clone method, copies the information of this Term object to another Term object
     *
     * @return this term object and all of the variable information within it
     */
    public Term clone()
    {
        return new Term(this);
    }


}
