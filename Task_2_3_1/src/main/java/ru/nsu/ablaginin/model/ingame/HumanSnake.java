package ru.nsu.ablaginin.model.ingame;

import javafx.scene.Scene;
import ru.nsu.ablaginin.model.ingame.bricks.Aim;
import ru.nsu.ablaginin.model.ingame.bricks.Direction;

public class HumanSnake extends Snake {
  public HumanSnake(int initX, int initY, int velocity, Direction direction, Aim aim) {
    super(initX, initY, velocity, direction, aim);
  }

  public void configureChangingDirection(Scene scene) {
    scene.setOnKeyPressed(e -> {
        Direction newDirection = this.getDirection();
        var code = e.getCode();
        switch (code) {
          case W, UP -> newDirection =
              getBody().size() == 1 || getBody().get(0).y == getBody().get(1).y
                  ? Direction.UP
                  : newDirection;
          case A, LEFT -> newDirection =
              getBody().size() == 1 || getBody().get(0).x == getBody().get(1).x
                  ? Direction.LEFT
                  : newDirection;
          case S, DOWN -> newDirection =
              getBody().size() == 1 || getBody().get(0).y == getBody().get(1).y
                  ? Direction.DOWN
                  : newDirection;
          case D, RIGHT -> newDirection =
              getBody().size() == 1 || getBody().get(0).x == getBody().get(1).x
                  ? Direction.RIGHT
                  : newDirection;
        }
        this.setDirection(newDirection);
    });
  }
}
