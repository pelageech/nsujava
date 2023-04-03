package ru.nsu.ablaginin.snake;

import javafx.scene.Scene;
import ru.nsu.ablaginin.field.Field;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SnakeHuman extends Snake {
  public SnakeHuman(Field field, Point spawn, int velocity) {
    super(field, spawn, velocity);
  }

  public void run() {
    System.out.println("human");
    super.run();
  }
}
