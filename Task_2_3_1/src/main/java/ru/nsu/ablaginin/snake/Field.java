package ru.nsu.ablaginin.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Field {
  private int width;
  private int height;
  private int columns;
  private int rows;
  private int squareSize;

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
}
