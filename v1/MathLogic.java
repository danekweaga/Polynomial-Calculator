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
     public double[] formatEquation(String LHS, String RHS)
    {
        double[] lhsArr = new double[4];
        double[] rhsArr = new double[4];
        double[] equationArr = new double[4];
    
        lhsArr = getCoef(LHS);
        rhsArr = getCoef(RHS);
    
        for (int i = 0; i < 4; i++) {
            equationArr[i] = lhsArr[i] - rhsArr[i];
        }
    
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

      //Step 5: Create a method to print roots from the Quadratic Equation 
      /********************************************************************************************************
       * Method to solve a quadratic equation ax² + bx + c
       * 
       * @param quadEquation An array contain all the coefficients of the variable[x², x, constant]
       * @return output A string containing the roots of the quadratic equation
       ********************************************************************************************************/
      public String solveQuadEq(double[] quadEquation)
      { 
        //Declare all variables
        double singleRoot,root1,root2,a,b,c,sqrtSide,discriminant,realPart,imaginaryPart;
        String output = "";
        
        // Get value of a, b and the constant (c) in the linear equation 
        a = quadEquation[1];
        b = quadEquation[2];
        c = quadEquation[3];
        
        //Plug variables into quadratic equation
        discriminant = (b * b) - (4 * a * c);
        sqrtSide = Math.sqrt(discriminant);
        
        //If the discriminant is positive the equation has two distinct real solutions
        if(discriminant>0)
        {
            sqrtSide = Math.sqrt(discriminant);
            root1 = (-b + sqrtSide) / (2 * a);
            root2 = (-b - sqrtSide) / (2 * a);
            output = String.format("Two Distinct Roots: %.2f , %.2f ",root1,root2);
        }
        
        //If the discriminant is equal to zero the equation has one real repeated solution
        else if(discriminant == 0)
        {
            singleRoot = (-b) / (2*a);
            output = String.format("One Real Repeated Root: %.2f ",singleRoot);
        }
        
        //If the discriminant is negative, the equation has two imaginary (complex) solutions
        else 
        {
            sqrtSide = Math.sqrt(-discriminant);
            realPart = (-b) / (2*a);
            imaginaryPart = (sqrtSide) / (2*a);
            root1 = realPart + imaginaryPart;
            root2 = realPart - imaginaryPart;
            if(Math.abs(realPart) < 1e-9)
            {
                output = String.format("Two complex roots: %.2fi, -%.2fi",
                                    imaginaryPart,imaginaryPart);
            }
            else
            {
                output = String.format("Two complex roots: %.2f + %.2fi, %.2f - %.2fi",
                                       realPart,imaginaryPart,realPart,imaginaryPart);
            }
        }
        return output;  
      }
      
    
      //Step 7: Create a method to print roots from the Cubic Equation
      /********************************************************************************************************
      * Method to solve a cubic equation ax³ + bx² + cx + d
      * 
      * @param cubEquation An array contain all the coefficients of the variable[x³,x², x, constant]
      * @return output A string containing the roots of the cubic equation
      ********************************************************************************************************/
       public String solveCubEq(double[] cubEquation) 
       {
        // Declare all variables at the top
        double a, b, c, d;
        double p, q, discriminant, shift;
        double u, v, t, t1, t2, t3;
        double realRoot, realPart, imaginaryPart;
        double root1, root2, root3, r, phi;
        String result;
    
        // Extract coefficients
        a = cubEquation[0];
        b = cubEquation[1];
        c = cubEquation[2];
        d = cubEquation[3];
    
        // Handle degenerate case (not cubic)
        if (a == 0) 
        {
            return solveQuadEq(new double[]{0, b, c, d});
        }
    
        // Normalize coefficients (make a = 1)
        b /= a;
        c /= a;
        d /= a;
        a = 1;
    
        // Depressed cubic transformation: x = t - b/3
        shift = b / 3;
        p = c - (b * b) / 3;
        q = (2 * Math.pow(b, 3)) / 27 - (b * c) / 3 + d;
    
        // Calculate discriminant
        discriminant = Math.pow(q / 2, 2) + Math.pow(p / 3, 3);
    
        if (discriminant > 0) 
        {
            // One real root, two complex roots
            u = Math.cbrt(-q / 2 + Math.sqrt(discriminant));
            v = Math.cbrt(-q / 2 - Math.sqrt(discriminant));
            t = u + v;
            realRoot = t - shift;
    
            realPart = -t / 2 - shift;
            imaginaryPart = Math.abs(Math.sqrt(3) * (u - v) / 2);
    
            result = String.format("One real root: %.4f, Two complex roots: %.4f + %.4fi, %.4f - %.4fi",
                    realRoot, realPart, imaginaryPart, realPart, imaginaryPart);
        }
        
        else if (discriminant == 0) 
        {
            // All roots real, at least two are equal
            u = Math.cbrt(-q / 2);
            t1 = 2 * u;
            t2 = -u;
    
            root1 = t1 - shift;
            root2 = t2 - shift;
    
            if (Math.abs(root1 - root2) < 1e-6) 
            {
                result = String.format("Triple real root: %.4f", root1);
            } 
            
            else 
            {
                result = String.format("Double root: %.4f and single root: %.4f", root2, root1);
            }
        }
        
        else 
         {
            // Three distinct real roots (using trigonometric method)
            r = Math.sqrt(-Math.pow(p, 3) / 27);
            phi = Math.acos(-q / (2 * r));
    
            t1 = 2 * Math.cbrt(r) * Math.cos(phi / 3);
            t2 = 2 * Math.cbrt(r) * Math.cos((phi + 2 * Math.PI) / 3);
            t3 = 2 * Math.cbrt(r) * Math.cos((phi + 4 * Math.PI) / 3);
    
            root1 = t1 - shift;
            root2 = t2 - shift;
            root3 = t3 - shift;
    
            result = String.format("Three real roots: %.4f, %.4f, %.4f", root1, root2, root3);
        }
    
        return result;
    }
    
    //Step 8 - Create a method that chooses which method to use and solves it using the method
     /********************************************************************************************************
     * Step 8: Method that chooses the correct solver based on the type of equation
     * 
     * @param equation A String containing the equation 
     * @return A string containing the solution(s)
     ********************************************************************************************************/
    public String solveAnyEquation(String LHS, String RHS)
    {
         double[] newEq = formatEquation(LHS , RHS);  
         String output = "";
         if(newEq[0] != 0 )
         {
            output = solveCubEq(newEq);
         }
         
         else if(newEq[1] != 0)
         {
            output = solveQuadEq(newEq);
         }
         else if(newEq[2] != 0)
         {
            output = solveLinEq(newEq);
         }
         else
         {
             if(newEq[3] == 0)
             {
                output = "All real numbers are solutions.";
             }
             
             else 
             {
                 output = "No solution.";
             }
         }
         return output;
    }

}
