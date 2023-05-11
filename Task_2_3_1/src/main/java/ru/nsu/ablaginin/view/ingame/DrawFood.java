package ru.nsu.ablaginin.view.ingame;

import ru.nsu.ablaginin.model.ingame.Food;
import javafx.scene.canvas.GraphicsContext;
import lombok.AllArgsConstructor;
import lombok.Setter;
import ru.nsu.ablaginin.view.Drawable;

/**
 * View module for drawing food.
 */
@AllArgsConstructor
public class DrawFood implements Drawable {
  @Setter private Food food;
  private int squareSize;

  @Override
  public void draw(GraphicsContext gc) {
    gc.drawImage(food.image(),
        food.x() * squareSize,
        food.y() * squareSize,
        squareSize,
        squareSize
    );
  }
}