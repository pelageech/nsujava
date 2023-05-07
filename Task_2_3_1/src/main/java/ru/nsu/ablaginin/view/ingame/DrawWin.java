package ru.nsu.ablaginin.view.ingame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Setter;
import ru.nsu.ablaginin.view.Drawable;

/**
 * View module for drawing a win screen.
 */
@AllArgsConstructor
public class DrawWin implements Drawable {
  @Setter
  private Image winImage;
  private int width;
  private int height;

  @Override
  public void draw(GraphicsContext gc) {
    gc.drawImage(winImage, 0, 0, width, height);
  }
}
