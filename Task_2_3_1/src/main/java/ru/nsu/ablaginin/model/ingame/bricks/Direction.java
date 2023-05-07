package ru.nsu.ablaginin.model.ingame.bricks;

/**
 * Just a direction (up, down, left, right).
 */
public enum Direction {
  UP,
  RIGHT,
  DOWN,
  LEFT;

  /**
   * Returns an opposite direction corresponding the current one.
   *
   * @return an opposite direction
   */
  public Direction opposite() {
    switch (this) {
      case UP -> {
        return DOWN;
      }
      case RIGHT -> {
        return LEFT;
      }
      case DOWN -> {
        return UP;
      }
      case LEFT -> {
        return RIGHT;
      }
      default -> throw new IllegalStateException("incorrect direction");
    }
  }

  /**
   * Parses an enum direction from string one.
   *
   * @param direction string direction
   * @return enum direction
   */
  public static Direction fromString(String direction) {
    return switch (direction.toLowerCase()) {
      case "up" -> UP;
      case "left" -> LEFT;
      case "right" -> RIGHT;
      case "down" -> DOWN;
      default -> throw new IllegalArgumentException();
    };
  }
}
