package ru.nsu.ablaginin.controller.menu;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import ru.nsu.ablaginin.controller.Controller;
import ru.nsu.ablaginin.model.menu.Menu;
import ru.nsu.ablaginin.view.menu.DrawMenu;

public class MenuController implements Controller {
  private GraphicsContext gc;
  private final Menu menu;
  private final DrawMenu drawMenu;

  public MenuController(GraphicsContext gc, Menu menu) {
    this.gc = gc;
    this.menu = menu;
    drawMenu = new DrawMenu(menu, Color.web("AAD751"), Color.web("82A149"));
  }

  @Override
  public void load() {
    drawMenu.draw(gc);
  }

  @Override
  public void stop() {

  }
}
