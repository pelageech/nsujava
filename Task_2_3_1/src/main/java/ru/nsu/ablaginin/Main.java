package ru.nsu.ablaginin;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
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

public class Main extends Application {
  private Field field;
  private Snake[] snakes;
  private Timeline timeline;

  private void run(GraphicsContext gc) {
    for (var s : snakes) {
      if (s.isGameOver()) {
        gc.setFill(Color.RED);
        gc.setFont(new Font("Arial", 70));
        gc.fillText("Game over!", field.getWidth() / 2., field.getHeight() / 2.);
        timeline.stop();
        return;
      }
    }
    field.drawField(gc);
    field.getController().drawFood(field.getFood(), gc);
    for (var s : snakes) {
      s.drawSnake(gc);
      s.move();
      s.gameOver();
      s.eatFood(field.getFood());
    }
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    field = new Field(25, 25, 30);

    Group root = new Group();
    Canvas canvas = new Canvas(field.getWidth(), field.getHeight());
    root.getChildren().add(canvas);
    Scene scene = new Scene(root);

    primaryStage.setTitle("Snake");
    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
    primaryStage.show();

    GraphicsContext gc = canvas.getGraphicsContext2D();
    snakes = new Snake[2];
    snakes[0] = new SnakeHuman(field, new Point(field.getColumns() / 2, field.getRows() / 2), 1, scene);
    snakes[1] = new SnakeBot(field, new Point(15, 15), 1);
    for (var th : snakes) {
      th.start();
    }


    field.setFood(field.getController().generateFood(field.getSnakes()));
    run(gc);
    timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> run(gc)));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
