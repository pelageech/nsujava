package ru.nsu.ablaginin.view.ingame;

import ru.nsu.ablaginin.model.ingame.Field;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Setter;
import ru.nsu.ablaginin.view.Drawable;

/**
 * View module for drawing field.
 */
@AllArgsConstructor
public class DrawField implements Drawable {
  private Field field;
  @Setter private Color colorOne;
  @Setter private Color colorTwo;
  @Setter private Color colorBarrier;

  @Override
  public void draw(GraphicsContext gc) {
    for (int i = 0; i < field.getColumns(); i++) {
      for (int j = 0; j < field.getRows(); j++) {
        if ((i + j) % 2 == 0) {
          gc.setFill(colorOne); // AAD751
        } else {
          gc.setFill(colorTwo); // A2D149
        }
        gc.fillRect(i * field.getSquareSize(),
            j * field.getSquareSize(),
            field.getSquareSize(),
            field.getSquareSize()
        );
      }
    }
    for (var b : field.getBarriers()) {
      gc.setFill(colorBarrier); // 0A8E2D
      gc.fillRect(
          b.x * field.getSquareSize(),
          b.y * field.getSquareSize(),
          field.getSquareSize(),
          field.getSquareSize()
      );
    }
  }
}
