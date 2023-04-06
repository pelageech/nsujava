package ru.nsu.ablaginin.field;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public record Barrier(int x, int y) {
  public void drawBarrier(GraphicsContext gc, int squareSize) {
    gc.setFill(Color.web("0A8E2D"));
    gc.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);
  }
}
