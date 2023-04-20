package ru.nsu.ablaginin.builder;

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

/**
 * Builder for menu. Loads the buttons for loading a level.
 * Creates a new controller and loads it.
 */
@AllArgsConstructor
public class MenuBuilder {

  private List<Button> levelButtons;

  /**
   * Just takes a graphical context for
   * setting up a controller and loads a menu.
   *
   * @param gc graphical context
   * @return a button for loading a menu
   */
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
