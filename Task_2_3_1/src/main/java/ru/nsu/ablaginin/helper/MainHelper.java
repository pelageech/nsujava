package ru.nsu.ablaginin.helper;

import javafx.scene.Node;
import javafx.scene.Scene;
import lombok.AllArgsConstructor;
import lombok.Setter;
import ru.nsu.ablaginin.Main;
import ru.nsu.ablaginin.controller.Controller;

/**
 * Functions connected with Main class.
 */
@AllArgsConstructor
public class MainHelper {
  @Setter
  private static Main main;

  /**
   * Stops the current controller and puts a new one.
   * It doesn't load it.
   *
   * @param controller new controller
   */
  public static void replaceController(Controller controller) {
    if (main.getCurrentController() != null) {
      main.getCurrentController().stop();
    }
    main.setCurrentController(controller);
  }

  /**
   * Clears the window but doesn't delete canvas.
   */
  public static void clearWindow() {
    main.getRoot().getChildren().remove(1, main.getRoot().getChildren().size());
  }

  /**
   * Puts a node to main's root.
   *
   * @param node new node
   */
  public static void putNode(Node node) {
    main.getRoot().getChildren().add(node);
  }
}
