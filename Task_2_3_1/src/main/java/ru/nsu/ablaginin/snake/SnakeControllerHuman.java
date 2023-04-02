package ru.nsu.ablaginin.snake;

import javafx.scene.Scene;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SnakeControllerHuman extends SnakeController {
  public static final int BODY_SIZE = 3;
  private final List<Point> body = new ArrayList<>();
  private Point head;
  private Direction direction = Direction.UP;
  private int rows;
  private int columns;
  private int squareSize;
  private boolean gameOver = false;

  SnakeControllerHuman(Scene scene, int spawnX, int spawnY) {
    super(scene, spawnX, spawnY);
  }


  @Override
  void changeDirection(Scene scene) {
    scene.setOnKeyPressed(event -> {
      var code = event.getCode();
      switch (code) {
        case W, UP:
          direction = direction != Direction.LEFT
              ? Direction.UP : direction;
        case A, LEFT:
          direction = direction != Direction.RIGHT
              ? Direction.LEFT : direction;
        case S, DOWN:
          direction = direction != Direction.UP
              ? Direction.DOWN : direction;
        case D, RIGHT:
          direction = direction != Direction.LEFT
              ? Direction.RIGHT : direction;
      }
    });
  }
}
