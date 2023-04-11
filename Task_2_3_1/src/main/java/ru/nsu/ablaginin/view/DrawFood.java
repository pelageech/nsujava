package ru.nsu.ablaginin.view;

import ru.nsu.ablaginin.model.Food;
import javafx.scene.canvas.GraphicsContext;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class DrawFood implements Drawable {
  @Setter private Food food;
  private int squareSize;

  @Override
  public void draw(GraphicsContext gc) {
    gc.drawImage(food.getImage(),
        food.getX() * squareSize,
        food.getY() * squareSize,
        squareSize,
        squareSize
    );
  }
}
