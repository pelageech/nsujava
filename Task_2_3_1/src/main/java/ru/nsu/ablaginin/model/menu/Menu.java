package ru.nsu.ablaginin.model.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.nsu.ablaginin.model.menu.bricks.Button;

import java.awt.*;
import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;

public class Menu {
  @Getter
  private final int width;
  @Getter
  private final int height;
  @Getter
  private final List<Button> levelButtons;
  @Getter
  private final Button exitButton;

  public Menu(int width, int height, List<Button> levelButtons) {
    this.width = width;
    this.height = height;
    this.levelButtons = levelButtons;
    exitButton = new Button("Exit", new Point(0, height - 30));
  }

}
