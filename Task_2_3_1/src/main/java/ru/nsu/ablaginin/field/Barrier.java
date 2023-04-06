package ru.nsu.ablaginin.field;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;

public class Barrier extends Point {
  public Barrier(int x, int y) {
    super(x, y);
  }

  public void drawBarrier(GraphicsContext gc, int squareSize) {
    gc.setFill(Color.web("0A8E2D"));
    gc.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);
  }
}