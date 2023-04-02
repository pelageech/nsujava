package ru.nsu.ablaginin.snake;

import javafx.scene.Group;
import javafx.scene.Scene;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class SnakeController {
  public static final int BODY_SIZE = 3;
  private final List<Point> body = new ArrayList<>();
  private Point head;
  private Direction direction = Direction.UP;
  private int rows;
  private int columns;
  private int squareSize;
  private boolean gameOver = false;

  SnakeController(Scene scene, int spawnX, int spawnY) {
    if (squareSize < 1 || spawnX < 1 || spawnX > columns || spawnY < 1 || spawnY > rows) {
      throw new IllegalArgumentException();
    }
    this.rows = rows;
    this.columns = columns;
    this.squareSize = squareSize;
    for (int i = 0; i < BODY_SIZE; i++) {
      body.add(new Point(spawnX, spawnY));
    }
    head = body.get(0);
  }

  abstract void changeDirection(Scene scene);

  void eatFood(Point food) {
    if (head.equals(food)) {
      body.add(new Point());
    }
  }
}
