package ru.nsu.ablaginin.helper;

import javafx.scene.Node;
import javafx.scene.Scene;
import lombok.AllArgsConstructor;
import lombok.Setter;
import ru.nsu.ablaginin.Main;
import ru.nsu.ablaginin.controller.Controller;

@AllArgsConstructor
public class MainHelper {
  @Setter
  private static Main main;

  public static void replaceController(Controller controller) {
    if (main.getCurrentController() != null) {
      main.getCurrentController().stop();
    }
    main.setCurrentController(controller);
  }

  public static void clearWindow() {
    main.getRoot().getChildren().remove(1, main.getRoot().getChildren().size());
  }

  public static void putNode(Node node) {
    main.getRoot().getChildren().add(node);
  }
}
