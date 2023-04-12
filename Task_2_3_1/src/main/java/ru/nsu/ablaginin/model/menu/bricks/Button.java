package ru.nsu.ablaginin.model.menu.bricks;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

public class Button extends javafx.scene.control.Button {
  @Getter private final Point point;

  public Button(String name, Point point) {
    super(name);
    this.point = point;
  }
}
