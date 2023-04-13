package ru.nsu.ablaginin.model.ingame.bricks;

public enum Direction {
  UP,
  RIGHT,
  DOWN,
  LEFT;

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
    }
    throw new IllegalStateException();
  }

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
