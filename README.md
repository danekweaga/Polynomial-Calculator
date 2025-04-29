# Polynomial Calculator

A modern JavaFX desktop application that solves and graphs polynomials of degree 1 (linear), degree 2 (quadratic), and degree 3 (cubic).

It is designed with:
- A dark-themed intuitive UI.
- A simple number pad and operator buttons.
- Graphing capabilities (for visualizing polynomials).
- Clear and human-readable solution display.

## How It Works
- Enter the left-hand side (LHS) and right-hand side (RHS) of the equation.
- Press **SOLVE** to see the roots.
- Press **GRAPH** to view the graph of the polynomial.
- Press **CLEAR** to reset the fields.

> **Note:** Use `^` to indicate exponents. Example: Write `2x^3+3x^2-5x+7` for \(2x^3 + 3x^2 - 5x + 7\).

> The symbol `^` is used because it is the standard way to represent powers in plain text, where superscripts are not available.

## Technologies Used
- Java 21
- JavaFX

## File Structure
- `MathLogic.java`: Handles parsing, formatting, and solving polynomials.
- `PolynomialGrapher.java`: Handles graphing polynomials using JavaFX's LineChart.
- `PolynomialCalculatorGUI.java`: The user interface layout and button actions.
- `PolynomialCalculatorApp.java`: Launches the application.
- `README.md`: Project description.
- `HOW_TO_USE.txt`: Usage instructions.
- `LICENSE`: Open-source license (MIT).

## License
This project is licensed under the MIT License.

## Author
Built by Chukwunonso Daniel Ekweaga.
