package ru.nsu.ablaginin.field;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;

public record Food(Image image, Point point) {
  public void drawFood(GraphicsContext gc, int squareSize) {
    gc.drawImage(image,
        point.x * squareSize,
        point.y * squareSize,
        squareSize,
        squareSize
    );
  }
}
