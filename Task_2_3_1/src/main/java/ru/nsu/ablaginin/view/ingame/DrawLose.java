package ru.nsu.ablaginin.view.ingame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Setter;
import ru.nsu.ablaginin.model.ingame.Snake;
import ru.nsu.ablaginin.view.Drawable;

import java.util.HashMap;
import java.util.Map;

/**
 * View module for drawing a loose screen.
 */
@AllArgsConstructor
public class DrawLose implements Drawable {
  @Setter private Snake.DeathType deathType = Snake.DeathType.NONE;
  private Map<Snake.DeathType, Image> loseImages;
  private int width;
  private int height;
  @Override
  public void draw(GraphicsContext gc) {
    if (deathType == Snake.DeathType.NONE) {
      return;
    }

    gc.drawImage(loseImages.get(deathType), 0, 0, width, height);
  }
}
