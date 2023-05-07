package ru.nsu.ablaginin.model.ingame.bricks;

/**
 * An aim for win.
 *
 * @param foodCollect how many foods snake should collect to win
 */
public record Aim(int foodCollect) {
  /**
   * Checks if the food is not positive.
   *
   * @param foodCollect food collect
   */
  public Aim {
    if (foodCollect <= 0) {
      throw new IllegalArgumentException("foodCollect <= 0");
    }
  }
}
