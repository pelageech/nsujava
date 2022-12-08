package ru.nsu.ablaginin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class OperationTest {
  @Test
  public void parseTest() {
    assertSame(Operation.SIN, Operation.parse("sin"));
    assertSame(Operation.COS, Operation.parse("cos"));
    assertSame(Operation.SQRT, Operation.parse("sqrt"));
    assertSame(Operation.SUM, Operation.parse("+"));
    assertSame(Operation.SUB, Operation.parse("-"));
    assertSame(Operation.MUL, Operation.parse("*"));
    assertSame(Operation.DIV, Operation.parse("/"));
    assertSame(Operation.LOG, Operation.parse("log"));
    assertSame(Operation.INVALID, Operation.parse("foobar"));
  }

  @Test
  public void singleArgOperationTest() {
    assertTrue(Operation.singleArgOperation(Operation.SIN, Math.PI) < Math.ulp(1.0));
    assertEquals(1.0, Operation.singleArgOperation(Operation.COS, 0.0));
    assertEquals(2.0, Operation.singleArgOperation(Operation.SQRT, 4.0));
    assertThrows(
        IllegalArgumentException.class,
        () -> Operation.singleArgOperation(Operation.DIV, 4)
    );
  }

  @Test
  public void twoArgOperationTest() {
    assertEquals(81, Operation.twoArgOperation(Operation.SUM, 54, 27));
    assertEquals(180, Operation.twoArgOperation(Operation.SUB, 90, -90));
    assertEquals(10, Operation.twoArgOperation(Operation.MUL, 2, 5));
    assertEquals(2, Operation.twoArgOperation(Operation.DIV, 4, 2));
    assertEquals(3, Operation.twoArgOperation(Operation.LOG, 2, 8));
    assertThrows(
        IllegalArgumentException.class,
        () -> Operation.twoArgOperation(Operation.INVALID, 4, 4)
    );
  }
}