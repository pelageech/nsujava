package ru.nsu.ablaginin;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.nsu.ablaginin.field.Field;
import ru.nsu.ablaginin.field.Food;
import ru.nsu.ablaginin.field.FoodController;
import ru.nsu.ablaginin.snake.Snake;
import ru.nsu.ablaginin.snake.SnakeBot;
import ru.nsu.ablaginin.snake.SnakeHuman;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ThreadPoolExecutor;

public class Main extends Application {
  private Field field;
  private Snake[] snakes;
  private Timeline timeline;

  private void run(GraphicsContext gc) {
    field.drawField(gc);
    field.getController().drawFood(field.getFood(), gc);
    for (var s : snakes) {
      if (!s.isGameOver()) {
        s.drawSnake(gc);
      }
    }
  }

  @Override
  public void start(Stage primaryStage) {

    Robot robot = new Robot();
    Group root = new Group();
    Scene scene = new Scene(root);

    field = new Field(25, 25, 30, scene);
    Canvas canvas = new Canvas(field.getWidth(), field.getHeight());
    root.getChildren().add(canvas);

    primaryStage.setTitle("Snake");
    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
    primaryStage.show();

    GraphicsContext gc = canvas.getGraphicsContext2D();
    field.setFood(field.getController().generateFood(field.getSnakes()));

    snakes = new Snake[2];
    snakes[0] = new SnakeHuman(field, new Point(field.getColumns() / 2, field.getRows() / 2), 1);
    snakes[1] = new SnakeBot(field, new Point(5, 5), 1, robot);
    for (var th : snakes) {
      th.run();
    }
    System.out.println("Snakes started!");

    run(gc);

    timeline = new Timeline(new KeyFrame(Duration.millis(10), event -> run(gc)));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
  }

  @Override
  public void stop() throws Exception {
    for (var s : snakes) {
      s.getTimer().cancel();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
