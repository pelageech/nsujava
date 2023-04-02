package ru.nsu.ablaginin.field;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Field {
  private final int width;
  private final int height;
  private final int columns;
  private final int rows;
  private final int squareSize;

  private Food food;

  public Field(int columns, int rows, int squareSize) {
    this.columns = columns;
    this.rows = rows;
    this.squareSize = squareSize;
    width = squareSize * columns;
    height = squareSize * rows;
  }

  public void drawField(GraphicsContext gc) {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        if ((i + j) % 2 == 0) {
          gc.setFill(Color.web("AAD751"));
        } else {
          gc.setFill(Color.web("A2D149"));
        }
        gc.fillRect(i * squareSize, j * squareSize, squareSize, squareSize);
      }
    }
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int getColumns() {
    return columns;
  }

  public int getRows() {
    return rows;
  }

  public int getSquareSize() {
    return squareSize;
  }
}
