import java.util.Arrays;

public class Driver {

    public static void main(String[] args) {
        MathLogic math = new MathLogic();

        // ??? Test 1: getCoef ????????????????????????????????
        String poly1 = "2x^3 + 3x^2 - 5x + 7";
        double[] coef1 = math.getCoef(poly1);
        System.out.println("Original polynomial: " + poly1);
        System.out.println("Coefficients [x^3, x^2, x, constant]: " + Arrays.toString(coef1));

        // ??? Test 2: formatEquation ?????????????????????????
        String equation = "2x^3 + 3x^2 - 5x + 7 = x^3 + x^2 + x + 1";
        double[] formatted = math.formatEquation(equation);
        System.out.println("\nOriginal equation: " + equation);
        System.out.println("Formatted (moved to LHS): " + Arrays.toString(formatted));

        // ??? Test 3: solveLinEq ?????????????????????????????
        double[] linEq = {0, 0, 4, -8}; // 4x - 8 = 0 ? x = 2
        String linResult = math.solveLinEq(linEq);
        System.out.println("\nEquation: 4x - 8 = 0");
        System.out.println("solveLinEq Output: " + linResult);

        // ??? Test 4: solveQuadEq ????????????????????????????
        double[] quadEq = {0, 1, -3, 2}; // x² - 3x + 2 = 0 ? roots: 2 and 1
        String quadResult = math.solveQuadEq(quadEq);
        System.out.println("\nEquation: x² - 3x + 2 = 0");
        System.out.println("solveQuadEq Output: " + quadResult);
    }
}
