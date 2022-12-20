package ru.nsu.ablaginin;

import java.util.EnumSet;
import org.jetbrains.annotations.NotNull;

/**
 * Operation contains atomic mathematics operations.
 * They're divided into two groups: single- and two-argument
 * operations.
 */
public enum Operation {
  INVALID,

  // 2-argument
  SUM,
  SUB,
  MUL,
  DIV,
  POW,
  LOG,

  // single-argument
  SIN,
  COS,
  SQRT;

  public static final EnumSet<Operation> singleArg = EnumSet.of(SIN, COS, SQRT);
  public static final EnumSet<Operation> twoArg = EnumSet.of(SUM, SUB, MUL, DIV, POW, LOG);

  /**
   * Parses string to enum.
   *
   * @param op an operation in a string form
   * @return appropriate enum
   */
  public static Operation parse(String op) {
    return switch (op.trim()) {
      case "sin" ->
        SIN;
      case "cos" ->
        COS;
      case "sqrt" ->
        SQRT;
      case "+" ->
        SUM;
      case "-" ->
        SUB;
      case "/" ->
        DIV;
      case "*" ->
        MUL;
      case "log" ->
        LOG;
      case "^" ->
        POW;
      default ->
        INVALID;
    };
  }

  /**
   * Applies the operation to one input number.
   *
   * @param op a single-argument operation
   * @param num an argument
   * @return an answer
   */
  public static double singleArgOperation(@NotNull Operation op, double num) {
    return switch (op) {
      case SIN ->
          Math.sin(num);
      case COS ->
          Math.cos(num);
      case SQRT ->
          Math.sqrt(num);
      default ->
          throw new IllegalArgumentException();
    };
  }

  /**
   * Applies the operation to two input numbers.
   *
   * @param op an operation
   * @param a the first argument
   * @param b the second argument
   * @return an answer
   */
  public static double twoArgOperation(@NotNull Operation op, double a, double b) {
    return switch (op) {
      case SUM ->
          a + b;
      case SUB ->
          a - b;
      case DIV ->
          a / b;
      case MUL ->
          a * b;
      case LOG ->
          Math.log(b) / Math.log(a);
      case POW ->
          Math.pow(a, b);
      default ->
          throw new IllegalArgumentException();
    };
  }
}
