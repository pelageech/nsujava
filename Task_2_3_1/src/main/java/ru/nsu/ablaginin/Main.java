package ru.nsu.ablaginin;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.nsu.ablaginin.field.Field;
import ru.nsu.ablaginin.field.Food;
import ru.nsu.ablaginin.field.FoodController;
import ru.nsu.ablaginin.snake.SnakeHuman;
import ru.nsu.ablaginin.snake.SnakeThread;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {
  private Field field;
  private FoodController foodController;
  private SnakeThread[] snakes;

  private void run(GraphicsContext gc) {
    field.drawField(gc);
    foodController.drawFood(field.getFood(), gc);
    for (var s : snakes) {
      s.drawSnake(gc);
    }
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    field = new Field(15, 15, 50);
    foodController = new FoodController(field);


    Group root = new Group();
    Canvas canvas = new Canvas(field.getWidth(), field.getHeight());
    root.getChildren().add(canvas);
    Scene scene = new Scene(root);

    primaryStage.setTitle("Snake");
    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
    primaryStage.show();

    GraphicsContext gc = canvas.getGraphicsContext2D();
    snakes = new SnakeThread[1];
    snakes[0] = new SnakeThread(new SnakeHuman(field, new Point(5, 5), 1, scene), gc);
    for (var th : snakes) {
      th.start();
    }


    field.setFood(FoodController.generateFood(field, field.getSnakes()));
    run(gc);
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(130), event -> run(gc)));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
