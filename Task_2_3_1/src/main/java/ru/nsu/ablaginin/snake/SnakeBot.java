package ru.nsu.ablaginin.snake;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.util.Duration;
import ru.nsu.ablaginin.field.Field;

import java.awt.*;

public class SnakeBot extends Snake{
  public SnakeBot(Field field, Point spawn, int velocity) {
    super(field, spawn, velocity);
  }

  @Override
  void changeDirection() {
    System.out.println("ROBOT DOLBOEB");
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
      System.out.println("ROBOT DOLBOEB");
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
    }));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
  }

  @Override
  public void run() {
    super.run();
  }
}
