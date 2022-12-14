package ru.nsu.ablaginin;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Class Calculator implements methods that solve mathematics
 * expressions. It supports all the real numbers including
 * + and - Infinity.
 * Supported operations must be described in enum Operations.
 * Currently, it is:
 *  - sum;
 *  - sub;
 *  - mul;
 *  - div;
 *  - pow;
 *  - log (with variadic base);
 *  - sin;
 *  - cos;
 *  - sqrt;
 */
public class Calculator {
  private final List<Double> nums = new ArrayList<>();

  /**
   * Takes an expression written in array form and solves an expression.
   * Each element of the array is an atom of an expression.
   * The method reads an array from the end, numbers are pushed to stack,
   * if an algorithm sees an operation, it takes numbers from the stack,
   * calculates an intermediate answer and pushes it back.
   * The answer is the last element in the stack.
   * Throws IllegalArgumentException on
   * - empty array;
   * - illegal operation;
   * - too low numbers.
   * The method puts warning in stdin if there are more numbers than it's needed.
   *
   * @param argv an array of atomic items of expression in prefix form
   * @return a real number that is an answer
   */
  public Double readAndSolve(@NotNull String[] argv) {
    if (argv.length == 0) {
      throw new IllegalArgumentException("Missed expression!");
    }
    int numsEnd = 0;

    for (int i = argv.length - 1; i >= 0; i--) {

      try {

        // if the current argv[i] is a number
        nums.add(Double.parseDouble(argv[i]));
        numsEnd++;
      } catch (NumberFormatException e) {

        // if it's an operation
        var op = Operation.parse(argv[i]);

        if (Operation.singleArg.contains(op)) { // one argument
          if (numsEnd == 0) {
            throw new IllegalArgumentException("Too low nums!");
          }

          var x = nums.get(numsEnd - 1);
          nums.set(numsEnd - 1, Operation.singleArgOperation(op, x));
        } else if (Operation.twoArg.contains(op)) { // two arguments
          if (numsEnd < 2) {
            throw new IllegalArgumentException("Too low nums!");
          }

          var x = nums.remove(--numsEnd);
          var y = nums.get(numsEnd - 1);
          nums.set(numsEnd - 1, Operation.twoArgOperation(op, x, y));
        } else { // invalid
          throw new IllegalArgumentException("Illegal operation!");
        }

      }
    }

    if (nums.size() > 1) {
      System.out.println("Warning: Last " + (nums.size() - 1) + " number(s) ain't used!");
    }

    return nums.get(numsEnd - 1);
  }

  /**
   * The method takes an expression in a convenient expression form
   * and solves it parsing string to string array and using the
   * `readAndSolve` method.
   *
   * @param input an expression in string form
   * @return a real number that is an answer
   */
  public Double solveFromString(@NotNull String input) {
    return readAndSolve(input.trim().replaceAll("\\s{2,}", " ").split(" "));
  }
}
