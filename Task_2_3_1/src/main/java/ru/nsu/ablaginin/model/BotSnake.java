package ru.nsu.ablaginin.model;

import ru.nsu.ablaginin.model.bricks.Aim;
import ru.nsu.ablaginin.model.bricks.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BotSnake extends Snake {
  public BotSnake(int initX, int initY, int velocity, Direction direction, Aim aim) {
    super(initX, initY, velocity, direction, aim);
  }

  public void analyze(Field field, Food food, List<Snake> snakes) {
    var head = this.getHead();
    var direction = this.getDirection();

    List<Direction> allowedDirections = new ArrayList<>();
    allowedDirections.add(Direction.UP);
    allowedDirections.add(Direction.DOWN);
    allowedDirections.add(Direction.LEFT);
    allowedDirections.add(Direction.RIGHT);


    Direction priorityX = null;
    Direction priorityY = null;

    if (food.getX() - head.x > 0) {
      priorityX = Direction.RIGHT;
    } else if (food.getX() - head.x < 0) {
      priorityX = Direction.LEFT;
    }
    if (food.getY() - head.y > 0) {
      priorityY = Direction.DOWN;
    } else if (food.getY() - head.y < 0) {
      priorityY = Direction.UP;
    }

    if (head.x == 0) {
      allowedDirections.remove(Direction.LEFT);
    } else if (head.x == field.getColumns() - 1) {
      allowedDirections.remove(Direction.RIGHT);
    }
    if (head.y == 0) {
      allowedDirections.remove(Direction.UP);
    } else if (head.y == field.getRows() - 1) {
      allowedDirections.remove(Direction.DOWN);
    }

    for (var s : snakes) {
      for (var p : s.getBody()) {
        if (p.x == head.x - 1 && p.y == head.y) {
          allowedDirections.remove(Direction.LEFT);
        } else if (p.x == head.x + 1 && p.y == head.y) {
          allowedDirections.remove(Direction.RIGHT);
        } else if (p.x == head.x && p.y == head.y - 1) {
          allowedDirections.remove(Direction.UP);
        } else if (p.x == head.x && p.y == head.y + 1) {
          allowedDirections.remove(Direction.DOWN);
        }
      }
    }

    Point next = nextPoint(direction, head);
    List<Point> body = this.getBody();
    for (int i = 0; i < body.size(); i++) {
      Point p = body.get(i);
      if (next.x == p.x && next.y == p.y) {
        var temp = this.getBody().get(i-1);
        var deltaX = temp.x - head.x;
        var deltaY = temp.y - head.y;
        if (deltaX == 1) {
          allowedDirections.remove(Direction.RIGHT);
        } else if (deltaX == -1) {
          allowedDirections.remove(Direction.LEFT);
        } else if (deltaY == 1) {
          allowedDirections.remove(Direction.DOWN);
        } else if (deltaY == -1) {
          allowedDirections.remove(Direction.UP);
        }
      }
    }

    if (priorityX != null && allowedDirections.contains(priorityX)) {
      this.setDirection(priorityX);
    } else if (priorityY != null && allowedDirections.contains(priorityY)) {
      this.setDirection(priorityY);
    } else if (allowedDirections.size() > 0) {
      Random random = new Random();
      this.setDirection(allowedDirections.get(
          random.nextInt(0, allowedDirections.size())
      ));
    }

  }

  private Point nextPoint(Direction direction, Point current) {
    Point next = (Point) current.clone();
    switch (direction) {
      case UP -> {
        next.y--;
      }
      case RIGHT -> {
        next.x++;
      }
      case DOWN -> {
        next.y++;
      }
      case LEFT -> {
        next.x--;
      }
    }
    return next;
  }
}
