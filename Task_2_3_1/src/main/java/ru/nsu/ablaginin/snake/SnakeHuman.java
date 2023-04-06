package ru.nsu.ablaginin.snake;

import javafx.scene.Scene;
import ru.nsu.ablaginin.field.Field;

import java.awt.*;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class SnakeHuman extends Snake {

  public SnakeHuman(Field field, Point spawn, int velocity, Direction startDirection) {
    super(field, spawn, velocity, startDirection);
  }

  @Override
  public void keyEventBehaviour(Scene scene) {
    scene.setOnKeyPressed(event -> {
      Direction newDirection = this.getDirection();
      var code = event.getCode();
      switch (code) {
        case W, UP -> newDirection = newDirection != Direction.DOWN ? Direction.UP : newDirection;
        case A, LEFT -> newDirection = newDirection != Direction.RIGHT ? Direction.LEFT : newDirection;
        case S, DOWN -> newDirection = newDirection != Direction.UP ? Direction.DOWN : newDirection;
        case D, RIGHT -> newDirection = newDirection != Direction.LEFT ? Direction.RIGHT : newDirection;
      }
      try {
        TimeUnit.MILLISECONDS.sleep(100);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      this.setDirection(newDirection);
    });
  }

  public void run() {
    System.out.println("human");
    super.run();
  }
}
