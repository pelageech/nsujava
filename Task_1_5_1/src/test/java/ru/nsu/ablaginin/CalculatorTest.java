package ru.nsu.ablaginin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CalculatorTest {

  @Test
  public void ordinaryTest() {
    var res = Math.sin(1 - 2 + 1);
    String[] input = new String[]{"sin", "+", "-", "1", "2", "1"};
    Calculator calc = new Calculator();
    assertEquals(res, calc.readAndSolve(input));
  }

  @Test
  public void ordinaryFromStringTest() {
    var res = Math.sin(1 - 2 + 1);
    Calculator calc = new Calculator();
    assertEquals(res, calc.solveFromString("sin + - 1 2 1"));
  }

  @Test
  public void singleOpTest() {
    var res = Math.sqrt(Math.cos(Math.sin(0)));
    String[] input = new String[]{"sqrt", "cos", "sin", "0"};
    Calculator calc = new Calculator();
    assertEquals(res, calc.readAndSolve(input));
  }

  @Test
  public void twoOpTest() {
    double result = Operation.twoArgOperation (
        Operation.LOG, 2, (Math.pow(2, 16) / (2 * 2)) + 9 - 9
    );
    String[] input = new String[]{
        "log", "2", "-", "+", "/", "^", "2", "16", "*", "2", "2", "9", "9"
    };
    Calculator calc = new Calculator();
    assertEquals(result, calc.readAndSolve(input));
  }

  @Test
  public void complicatedTest() {
    double ans = Math.pow(Math.sin(3), 5) * 9 + Math.cos(3. / 7.);
    String input = "+ * ^ sin 3 5 9 cos / 3 7";
    Calculator calc = new Calculator();
    assertEquals(ans, calc.solveFromString(input));
  }

  @Test
  public void complicatedOneTest() {
    String[] input = new String[]{
        "+", "-", "*", "13", "-", "sin", "*", "3", "4", "3",
        "*", "sin", "0", "cos", "+", "5", "-", "0", "1", "-",
        "^", "9", "2", "^", "6", "+", "cos", "0", "sin", "1.9411"
    };
    Calculator calc = new Calculator();
    assertEquals(3.1416785965751544, calc.readAndSolve(input));
  }

  @Test
  public void complicatedTwoTest() {
    double result = Math.sqrt(
        Math.pow(
            9,
            Math.pow(
                Math.sin(3 + Math.pow(2, 5)), 2
            ) + Math.pow(Math.cos(35), 2))) - (66 / (2 + 2 * 2. / 4.)
    );
    String input = "- sqrt ^ 9    +    ^ sin     + 3 ^ 2 5 2 ^      cos 35 2 / 66 + 2 * 2 / 2 4";
    Calculator calc = new Calculator();
    assertEquals(result, calc.solveFromString(input));
  }

  @Test
  public void exceptionTest() {
    Calculator calc = new Calculator();
    assertThrows(IllegalArgumentException.class, () -> calc.readAndSolve(new String[0]));
    assertThrows(
        IllegalArgumentException.class,
        () -> calc.readAndSolve(new String[]{"sinc", "0"})
    );
    assertThrows(
        IllegalArgumentException.class,
        () -> calc.readAndSolve(new String[]{"sin"})
    );
    assertThrows(
        IllegalArgumentException.class,
        () -> calc.readAndSolve(new String[]{"+", "2"})
    );
  }
}