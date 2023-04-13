package ru.nsu.ablaginin.view.menu;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.ablaginin.Main;
import ru.nsu.ablaginin.model.menu.Menu;
import ru.nsu.ablaginin.view.Drawable;

@AllArgsConstructor
public class DrawMenu implements Drawable {
  @Getter private final Menu menu;
  @Getter @Setter private Color bgColor;
  @Getter @Setter private Color textColor;

  @Override
  public void draw(GraphicsContext gc) {
    gc.setFill(bgColor);
    gc.fillRect(0, 0, menu.getWidth(), menu.getHeight());
    gc.setFill(textColor);
    gc.setFont(new Font("Comic Sans MS", 55));
    gc.fillText("SnakeFX", menu.getWidth() / 3., menu.getHeight() / 4.);
  }
}
