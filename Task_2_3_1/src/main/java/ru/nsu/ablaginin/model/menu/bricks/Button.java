package ru.nsu.ablaginin.model.menu.bricks;

import lombok.Getter;

import java.awt.*;

/**
 * Button that contains a point where the button is located.
 */
public class Button extends javafx.scene.control.Button {
  @Getter private final Point point;

  public Button(String name, Point point) {
    super(name);
    this.point = point;
  }
}
