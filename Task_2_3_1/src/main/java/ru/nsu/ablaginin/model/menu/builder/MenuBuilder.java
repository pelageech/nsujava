package ru.nsu.ablaginin.model.menu.builder;

import javafx.scene.canvas.GraphicsContext;
import lombok.AllArgsConstructor;
import java.awt.Point;
import java.util.List;
import ru.nsu.ablaginin.controller.menu.MenuController;
import ru.nsu.ablaginin.helper.MainHelper;
import ru.nsu.ablaginin.model.menu.Menu;
import ru.nsu.ablaginin.model.menu.bricks.Button;

import static ru.nsu.ablaginin.Main.HEIGHT;
import static ru.nsu.ablaginin.Main.WIDTH;

@AllArgsConstructor
public class MenuBuilder {

  private List<Button> levelButtons;

  public Button buildNewMenu(GraphicsContext gc) {
    Button menuButton = new Button("Menu", new Point());
    menuButton.setOnMouseClicked(event -> {
      MainHelper.clearWindow();
      for (var b : levelButtons) {
        MainHelper.putNode(b);
      }

      var newController = new MenuController(gc, new Menu(WIDTH, HEIGHT, levelButtons));
      MainHelper.replaceController(newController);
      newController.load();
    });
    return menuButton;
  }
}
