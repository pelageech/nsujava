package ru.nsu.ablaginin.model;

import ru.nsu.ablaginin.model.bricks.Barrier;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Field {
  @Getter private int width;
  @Getter private int height;
  @Getter private int squareSize;
  @Getter private int columns;
  @Getter private int rows;
  @Getter @Setter private List<Barrier> barriers = new ArrayList<>();

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
