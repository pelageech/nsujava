package ru.nsu.ablaginin.model.menu;

import lombok.Getter;
import ru.nsu.ablaginin.model.menu.bricks.Button;
import java.awt.Point;
import java.util.List;

/**
 * A menu model contains necessary functions for controlling menu.
 */
public class Menu {
  @Getter
  private final int width;
  @Getter
  private final int height;
  @Getter
  private final List<Button> levelButtons;
  @Getter
  private final Button exitButton;

  /**
   * Creates a single menu model.
   *
   * @param width width screen
   * @param height height screen
   * @param levelButtons level buttons
   */
  public Menu(int width, int height, List<Button> levelButtons) {
    this.width = width;
    this.height = height;
    this.levelButtons = levelButtons;
    exitButton = new Button("Exit", new Point(0, height - 30));
  }

}
