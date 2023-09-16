package ru.nsu.ablaginin.model.ingame;

import javafx.scene.Scene;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.ablaginin.model.ingame.bricks.Aim;
import ru.nsu.ablaginin.model.ingame.bricks.Direction;

/**
 * Snake that's controlled by a human.
 */
public class HumanSnake extends Snake {

  /**
   * Creates a single model of a human snake.
   *
   * @param initX spawnX
   * @param initY spawnY
   * @param velocity velocity
   * @param direction direction
   * @param aim aim
   */
  public HumanSnake(int initX, int initY, int velocity, Direction direction, Aim aim) {
    super(initX, initY, velocity, direction, aim);
  }

  /**
   * Configs the snake's behaviour on key pressing.
   *
   * @param scene the current scene
   */
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
          default -> {
              return;
          }
        }
        this.setDirection(newDirection);
    });
  }
}
