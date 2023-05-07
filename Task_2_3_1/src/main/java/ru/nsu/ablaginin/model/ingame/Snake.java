package ru.nsu.ablaginin.model.ingame;

import lombok.Getter;
import lombok.Setter;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import ru.nsu.ablaginin.model.ingame.bricks.Aim;
import ru.nsu.ablaginin.model.ingame.bricks.Direction;

/**
 * A snake model. There are many parameters of snakes.
 * A snake has a particular behaviour like move, eat, win, lose.
 */
public class Snake {
  public static final int INIT_BODY_LENGTH = 4;

  @Getter private final List<Point> body = new ArrayList<>();
  @Getter private Point head;
  @Getter private final int velocity;
  @Getter @Setter private Direction direction;
  @Getter private final Aim aim;
  @Getter private int eaten = 0;
  @Getter @Setter private boolean gameOver = false;
  @Getter @Setter private boolean won = false;
  @Getter private final int initX;
  @Getter private final int initY;

  /**
   * Creates a single snake model.
   *
   * @param initX snake spawnX
   * @param initY snake spawnY
   * @param velocity velocity
   * @param direction direction
   * @param aim aim
   */
  public Snake(int initX, int initY, int velocity, Direction direction, Aim aim) {
    if (velocity <= 0) {
      throw new IllegalArgumentException("velocity is below zero");
    }

    this.velocity = velocity;
    this.direction = direction;
    this.aim = aim;
    this.initX = initX;
    this.initY = initY;

    toStart();
  }

  /**
   * Put the snake to the start position.
   */
  public void toStart() {
    body.clear();
    body.add(new Point(initX, initY));
    head = body.get(0);

    for (int i = 1; i < INIT_BODY_LENGTH; i++) {
      // warning appears because of INIT_BODY_LENGTH = 1
      growUp();
    }
  }

  /**
   * Increases a length of the snake.
   */
  public void growUp() {
    int deltaX = 0;
    int deltaY = 0;

    if (body.size() == 1) {
      switch (direction) {
        case UP -> deltaY = 1;
        case RIGHT -> deltaX = -1;
        case DOWN -> deltaY = -1;
        case LEFT -> deltaX = 1;
        default -> throw new IllegalStateException("incorrect direction");
      }
    } else {
      deltaX = body.get(body.size() - 2).x - body.get(body.size() - 1).x;
      deltaY = body.get(body.size() - 2).y - body.get(body.size() - 1).y;
    }

    body.add(new Point(
        body.get(body.size() - 1).x + deltaX,
        body.get(body.size() - 1).y + deltaY
    ));
  }

  /**
   * Checks if the snake can eat the food.
   *
   * @param food food
   * @return if the food has been eaten
   */
  public boolean eatFood(Food food) {
    if (head.x == food.x() && head.y == food.y()) {
      growUp();
      eaten++;
      return true;
    }
    return false;
  }

  /**
   * Moves the snake depending on a snake's direction.
   */
  public void move() {
    for (int i = body.size() - 1; i >= 1; i--) {
      body.get(i).x = body.get(i - 1).x;
      body.get(i).y = body.get(i - 1).y;
    }
    switch (direction) {
      case UP -> head.y -= velocity;
      case DOWN -> head.y += velocity;
      case LEFT -> head.x -= velocity;
      case RIGHT -> head.x += velocity;
      default -> throw new IllegalStateException("incorrect direction");
    }
  }

  /**
   * Checks if the snake achieved an aim.
   *
   * @return true if the snake has won
   */
  public boolean win() {
    return aim.foodCollect() == eaten;
  }
}
