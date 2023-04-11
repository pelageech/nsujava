package ru.nsu.ablaginin;

import lombok.Cleanup;
import ru.nsu.ablaginin.controller.Controller;
import ru.nsu.ablaginin.model.BotSnake;
import ru.nsu.ablaginin.model.Field;
import ru.nsu.ablaginin.model.HumanSnake;
import ru.nsu.ablaginin.model.bricks.Aim;
import ru.nsu.ablaginin.model.bricks.Direction;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
  public static final int WIDTH = 675;
  public static final int HEIGHT = 540;

  private Controller controller;
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Group root = new Group();
    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    root.getChildren().add(canvas);
    Scene scene = new Scene(root);

    Field field = new Field(WIDTH, HEIGHT, 45);

    HumanSnake humanSnake = new HumanSnake(5, 5, 1, Direction.DOWN, new Aim(10));
    humanSnake.configureChangingDirection(scene);

    List<BotSnake> botSnakes = new ArrayList<>();
    botSnakes.add(new BotSnake(10, 10, 1, Direction.LEFT, new Aim(5)));


    var gc = canvas.getGraphicsContext2D();
    List<Image> images = new ArrayList<>();

    for (int i = 1; i < 6; i++) {
      @Cleanup var is = getClass().getClassLoader().getResourceAsStream("img/food" + i + ".png");
      if (is != null) {
        images.add(new Image(is));
      }
    }
    controller = new Controller(gc, field, humanSnake, botSnakes, 150, images);
    controller.load();
    primaryStage.setTitle("Snake");
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();
  }

  @Override
  public void stop() throws Exception {
    controller.stop();
  }
}
