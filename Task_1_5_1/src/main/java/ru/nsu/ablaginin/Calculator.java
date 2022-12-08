package ru.nsu.ablaginin;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class Calculator {
  private final List<Double> nums = new ArrayList<>();

  public Double readAndSolve(@NotNull String[] argv) {
    if (argv.length == 0) {
      throw new IllegalArgumentException("Missed expression!");
    }
    int numsEnd = 0;

    for (int i = argv.length - 1; i >= 0; i--) {

      try {
        nums.add(Double.parseDouble(argv[i]));
        numsEnd++;
      } catch (NumberFormatException e) {

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

  public Double solveFromString(@NotNull String input) {
    return readAndSolve(input.split(" "));
  }
}
