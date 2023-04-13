package ru.nsu.ablaginin.model.ingame;

import ru.nsu.ablaginin.model.ingame.bricks.Aim;
import ru.nsu.ablaginin.model.ingame.bricks.Direction;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake {
  public static final int INIT_BODY_LENGTH = 4;

  @Getter private List<Point> body = new ArrayList<>();
  @Getter private Point head;
  @Getter private int velocity;
  @Getter @Setter private Direction direction;
  @Getter private Aim aim;
  @Getter @Setter private boolean gameOver = false;
  @Getter @Setter private boolean won = false;
  @Getter private int initX;
  @Getter private int initY;

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

  public void toStart() {
    body.clear();
    body.add(new Point(initX, initY));
    head = body.get(0);

    for (int i = 1; i < INIT_BODY_LENGTH; i++) {
      // warning appears because of INIT_BODY_LENGTH = 1
      growUp();
    }
  }

  public void growUp() {
    int deltaX = 0;
    int deltaY = 0;

    if (body.size() == 1) {
      switch (direction) {
        case UP -> deltaY = 1;
        case RIGHT -> deltaX = -1;
        case DOWN -> deltaY = -1;
        case LEFT -> deltaX = 1;
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

  public boolean eatFood(Food food) {
    if (head.x == food.getX() && head.y == food.getY()) {
      growUp();
      return true;
    }
    return false;
  }

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
    }
  }
}
