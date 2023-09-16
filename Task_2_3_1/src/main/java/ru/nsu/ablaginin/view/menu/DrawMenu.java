package ru.nsu.ablaginin.view.menu;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.ablaginin.model.menu.Menu;
import ru.nsu.ablaginin.view.Drawable;

/**
 * View module for drawing menu.
 */
@Getter
@AllArgsConstructor
public class DrawMenu implements Drawable {
  private final Menu menu;
  @Setter private Color bgColor;
  @Setter private Color textColor;

  @Override
  public void draw(GraphicsContext gc) {
    gc.setFill(bgColor);
    gc.fillRect(0, 0, menu.getWidth(), menu.getHeight());
    gc.setFill(textColor);
    gc.setFont(new Font("Comic Sans MS", 65));
    gc.fillText("SnakeFX v1.1", menu.getWidth() / 3.6, menu.getHeight() / 4.);

    var exitButtonPoint = menu.getExitButton().getPoint();
    menu.getExitButton().setLayoutX(exitButtonPoint.x);
    menu.getExitButton().setLayoutY(exitButtonPoint.y);

    var levelButtons = menu.getLevelButtons();
    var width = 85.5;
    var height = 20;

    for (int i = 0; i < levelButtons.size(); i++) {
      levelButtons.get(i).setMinSize(width, height);
      levelButtons.get(i).setLayoutX(i * (width + 5));
      levelButtons.get(i).setLayoutY(400);
    }
  }
}
