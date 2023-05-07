package ru.nsu.ablaginin.view.ingame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Setter;
import ru.nsu.ablaginin.view.Drawable;

/**
 * View module for drawing a loose screen.
 */
@AllArgsConstructor
public class DrawLose implements Drawable {
  @Setter private Image loseImage;
  private int width;
  private int height;
  @Override
  public void draw(GraphicsContext gc) {
    gc.drawImage(loseImage, 0, 0, width, height);
  }
}
