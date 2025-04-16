 /*************************************************************************
 @ title The MathLogic Class
             
 @ author Chukwunonso Daniel Ekweaga
 @ date 16/04/25
 *************************************************************************/

public class MathLogic
{
    
    //Step 1: Create a method that extracts coefficient from a number and puts it in an array
    /*********************************************************************************
     * Method to extract coefficient from the string
     * @description Extracts coefficients from a 3rd-degree polynomial expression
              e.g. "2x^3 + 3x^2 - 5x + 7" ? [2, 3, -5, 7]
     
     * @param polynomial     The entire term string (e.g. "-3x^2")
     * @return  arr[]       All the coefficients in an array
     *********************************************************************************/
    public double[] getCoef(String polynomial)
    {
            double[] arr = new double[4]; 
    
            // Remove spaces
            polynomial = polynomial.replaceAll(" ", "");
    
            // Split on + or - while keeping the signs
            String[] terms = polynomial.split("(?=[+-])");
    
            for (String term : terms)
            {    
                if (term.contains("x^3"))
                {
                    arr[0] = extractCoefficient(term, "x^3");
                }
                else if (term.contains("x^2"))
                {
                    arr[1] = extractCoefficient(term, "x^2");
                }
                else if (term.contains("x"))
                {
                    arr[2] = extractCoefficient(term, "x");
                }
                else
                {
                    arr[3] = Integer.parseInt(term);
                }
            }
    
            return arr;
    }
    
    //Step 2 : Create a method the extracts the coefficient from the variables 
    /*********************************************************************
     * Method to extract coefficient from the variable
     
     * @param term     The entire term string (e.g. "-3x^2")
     * @param variable The variable part (e.g. "x^2")
     * @return         The integer coefficient
     *********************************************************************/
    private double extractCoefficient(String term, String variable)
    {
        String coef = term.replace(variable, "");
        if (coef.equals("") || coef.equals("+")) return 1.0;
        if (coef.equals("-")) return -1.0;
        return Integer.parseInt(coef);
    }

    //Step 3: Create a method that makes a proper equation so no x on the other side or anything like that
    /*********************************************************************************
     * Method to create a proper equation equating to 0 on right hand side
     
     * @param polynomial    The entire term string (e.g. "-3x^2")
     * @return equationArr  Properly formatted equation
     *********************************************************************************/
     public double[] formatEquation(String polynomial)
     {
         //Declaration Section
         String LHS,RHS;
         int equalsIndex;
         double[] lhsArr = new double[4];
         double[] rhsArr = new double[4];
         double[] equationArr = new double[4];
     
         //Run the get coefficient method on both sides of equal sign
         equalsIndex = polynomial.indexOf("=");
         LHS = polynomial.substring(0,equalsIndex);
         RHS = polynomial.substring(equalsIndex+1,polynomial.length());
         
         lhsArr = getCoef(LHS);
         rhsArr =getCoef(RHS);
         
         //Now Subtract them from each other place them into a final array
         for(int i = 0; i < 4 ; i++)
         {
             equationArr[i] = lhsArr[i] - rhsArr[i];
         }
         //Return the formatted equation
         return equationArr;
     }
     
     //Step 4: Create a method to print roots from a linear equation
     /********************************************************************************************************
      * Method to print roots from a linear equation
      * 
      * @param linEquation An array contain all the coefficients of the variable [x, constant]
      * @return output A string containing the root of the linear equation
      ********************************************************************************************************/
      public String solveLinEq(double[] linEquation)
      {
          // Get value of a and the constant (c) in the linear equation < ax + c >
          double a  = linEquation[2];
          double constant = linEquation[3];
          double root = 0;
          String output = "";
          //Handle a based on it's sign - or +
          if(a>0){ root = -constant/(a);}
          if(a<0){ root = constant/a;}
          
          output = String.format("Root: %.2f ",root);
          return output;
      }
     
      //Step 5: Create  a method to get the square root part of the discriminants
      /********************************************************************************************
      * Method to compute the square root part (discriminant) of the quadratic formula.
      * 
      * @param a Coefficient of x²
      * @param b Coefficient of x
      * @param c Constant term
      * @return The square root portion: ?(b² - 4ac), or NaN if discriminant is negative
      ********************************************************************************************/
     public double getRoots(double a, double b, double c)
     {
         double discriminant = Math.pow(b, 2) - (4 * a * c);
         if (discriminant < 0) 
         {
             return Double.NaN; 
         }
         return Math.sqrt(discriminant); 
     }       

      //Step 6: Create a method to print roots from the Quadratic Equation
      /********************************************************************************************************
       * Method to solve a quadratic equation ax² + bx + c
       * 
       * @param quadEquation An array contain all the coefficients of the variable[x², x, constant]
       * @return output A string containing the roots of the quadratic equation
       ********************************************************************************************************/
      public String solveQuadEq(double[] quadEquation)
      { 
        // Get value of a, b and the constant (c) in the linear equation 
        double a,b,constant,sqrtSide,root1,root2;
        a = quadEquation[1];
        b  = quadEquation[2];
        constant = quadEquation[3];
        String output = "";
        
        //Plug variables into quadratic equation
        sqrtSide = getRoots(a,b,constant);
        root1 = (-b + sqrtSide) / (2 * a);
        root2 = (-b - sqrtSide) / (2 * a);
        
        //Return the roots
        output = String.format("Root: %.2f , %.2f ",root1,root2);
        return output;  
      }
}
