package ru.nsu.ablaginin.model.bricks;

public record Aim(int foodCollect) {
  public Aim {
    if (foodCollect <= 0) {
      throw new IllegalArgumentException("foodCollect <= 0");
    }
  }
}
