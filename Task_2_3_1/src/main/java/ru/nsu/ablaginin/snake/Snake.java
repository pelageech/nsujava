package ru.nsu.ablaginin.snake;

import javafx.scene.Scene;
import ru.nsu.ablaginin.field.Field;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Snake {
  public static final int BODY_SIZE = 3;
  private final List<Point> body = new ArrayList<>();
  private Point head;
  private Direction direction = Direction.UP;
  private Field field;
  private boolean gameOver = false;

  Snake(Field field, Point spawn) {

    this.field = field;

    if (spawn.x < 0 || spawn.x >= field.getColumns()
        || spawn.y < 0 || spawn.y >= field.getRows()) {
      throw new IllegalArgumentException();
    }

    for (int i = 0; i < BODY_SIZE; i++) {
      body.add(new Point(spawn.x, spawn.y));
    }
    head = body.get(0);
  }

  abstract void changeDirection(Scene scene);

  void eatFood(Point food) {
    if (head.equals(food)) {
      body.add(new Point());
    }
  }

  public List<Point> getBody() {
    return body;
  }
}
