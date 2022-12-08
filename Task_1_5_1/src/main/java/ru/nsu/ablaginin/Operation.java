package ru.nsu.ablaginin;

import java.util.EnumSet;
import org.jetbrains.annotations.NotNull;

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
