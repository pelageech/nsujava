package ru.nsu.ablaginin.model.ingame.bricks;

import java.awt.Point;

/**
 * A barrier that the snake can die due to.
 */
public class Barrier extends Point {
  /**
   * Creates a single barrier exemplar.
   *
   * @param x x-coordinate
   * @param y y-coordinate
   */
  public Barrier(int x, int y) {
    super(x, y);
  }
}
