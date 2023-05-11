package ru.nsu.ablaginin.model.ingame;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import ru.nsu.ablaginin.model.ingame.bricks.Barrier;

/**
 * The current field with barriers. Starts from (0, 0).
 */
public class Field {
  @Getter private final int width;
  @Getter private final int height;
  @Getter private final int squareSize;
  @Getter private final int columns;
  @Getter private final int rows;
  @Getter @Setter private List<Barrier> barriers = new ArrayList<>();

  /**
   * Create a single model of field. Covers the screen with field from
   * (0, 0) to (width, height). A scale is set by squareSize.
   *
   * @param width width
   * @param height height
   * @param squareSize squareSize
   */
  public Field(int width, int height, int squareSize) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("width or height <= 0");
    }
    this.width = width;
    this.height = height;
    this.squareSize = squareSize;
    columns = width % squareSize == 0
        ? width / squareSize
        : width / squareSize + 1;

    rows = height % squareSize == 0
        ? height / squareSize
        : height / squareSize + 1;
  }
}