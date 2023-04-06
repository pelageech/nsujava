package ru.nsu.ablaginin.snake;

import javafx.scene.Scene;
import javafx.scene.robot.Robot;
import ru.nsu.ablaginin.field.Field;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class SnakeBot extends Snake{
  private final Robot robot;
  public SnakeBot(Field field, Point spawn, int velocity, Direction startDirection, Robot robot) {
    super(field, spawn, velocity, startDirection);
    this.robot = robot;
  }

  private void behaviourOne() {
    var foodPoint = getField().getFood().point();
    var head = getHead();

    if (foodPoint.x - head.x > 0 && getDirection() != Direction.LEFT) {
      setDirection(Direction.RIGHT);
    } else if (foodPoint.x - head.x < 0 && getDirection() != Direction.RIGHT) {
      setDirection(Direction.LEFT);
    } else {
      if (foodPoint.y - head.y > 0 && getDirection() != Direction.UP) {
        setDirection(Direction.DOWN);
      } else if (foodPoint.y - head.y < 0 && getDirection() != Direction.DOWN) {
        setDirection(Direction.UP);
      }
    }
  }

  public void behaviourTwo() {
    var foodPoint = getField().getFood().point();
    var head = getHead();

    var directions = whereToBend();

    if (foodPoint.x - head.x > 0) {
      if (getDirection() != Direction.LEFT) {
        setDirection(Direction.RIGHT);
      } else {
        if (directions.contains(Direction.DOWN)) {
          setDirection(Direction.DOWN);
        } else {
          setDirection(Direction.UP);
        }
      }
    } else if (foodPoint.x - head.x < 0) {
      if (getDirection() != Direction.RIGHT) {
        setDirection(Direction.LEFT);
      } else {
        if (directions.contains(Direction.DOWN)) {
          setDirection(Direction.DOWN);
        } else {
          setDirection(Direction.UP);
        }
      }
    } else {
      if (foodPoint.y - head.y > 0) {
        if (getDirection() != Direction.UP) {
          setDirection(Direction.DOWN);
        } else {
          if (directions.contains(Direction.LEFT)) {
            setDirection(Direction.LEFT);
          } else {
            setDirection(Direction.RIGHT);
          }
        }
      } else if (foodPoint.y - head.y < 0) {
        if (getDirection() != Direction.DOWN) {
          setDirection(Direction.UP);
        } else {
          if (directions.contains(Direction.LEFT)) {
            setDirection(Direction.LEFT);
          } else {
            setDirection(Direction.RIGHT);
          }
        }
      }
    }
  }

  private List<Direction> whereToBend() {
    List<Direction> result = new ArrayList<>();

    var head = getHead();
    var columns = getField().getColumns();
    var rows = getField().getRows();

    if (head.x == 0) {
      result.add(Direction.RIGHT);
    } else if (head.x == columns - 1) {
      result.add(Direction.LEFT);
    } else {
      result.add(Direction.RIGHT);
      result.add(Direction.LEFT);
    }

    if (head.y == 0) {
      result.add(Direction.DOWN);
    } else if (head.y == rows - 1) {
      result.add(Direction.UP);
    } else {
      result.add(Direction.DOWN);
      result.add(Direction.UP);
    }

    return result;
  }

  @Override
  public void keyEventBehaviour(Scene scene) {
  }

  @Override
  public void runTask() {
    behaviourOne();
    super.runTask();
  }
}
