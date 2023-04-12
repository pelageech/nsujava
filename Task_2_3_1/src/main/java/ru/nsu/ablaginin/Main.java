package ru.nsu.ablaginin;

import javafx.scene.canvas.GraphicsContext;
import lombok.Cleanup;
import ru.nsu.ablaginin.controller.Controller;
import ru.nsu.ablaginin.controller.ingame.InGameController;
import ru.nsu.ablaginin.controller.menu.MenuController;
import ru.nsu.ablaginin.model.ingame.BotSnake;
import ru.nsu.ablaginin.model.ingame.Field;
import ru.nsu.ablaginin.model.ingame.HumanSnake;
import ru.nsu.ablaginin.model.ingame.bricks.Aim;
import ru.nsu.ablaginin.model.ingame.bricks.Direction;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.nsu.ablaginin.model.menu.Menu;
import ru.nsu.ablaginin.model.menu.bricks.Button;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Main starts an application. There can only be one working controller.
 */
public class Main extends Application {
  public static final int WIDTH = 675;
  public static final int HEIGHT = 540;
  private Controller currentController;
  private final List<Button> levelButtons = new ArrayList<>();
  private final Group root = new Group();

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    // configure
    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    root.getChildren().add(canvas);
    Scene scene = new Scene(root);
    var gc = canvas.getGraphicsContext2D();

    // prepare images
    List<Image> images = new ArrayList<>();

    for (int i = 1; i < 6; i++) {
      @Cleanup var is = getClass().getClassLoader().getResourceAsStream("img/food" + i + ".png");
      if (is != null) {
        images.add(new Image(is));
      }
    }

    // prepare menu
    levelButtons.add(new Button("Level 1", new Point()));
    levelButtons.add(new Button("Level 2", new Point()));
    levelButtons.add(new Button("Level 3", new Point()));
    levelButtons.add(new Button("Level 4", new Point()));
    levelButtons.add(new Button("Level 5", new Point()));
    levelButtons.add(new Button("Level 6", new Point()));
    levelButtons.add(new Button("Level 7", new Point()));
    levelButtons.add(new Button("Level 8", new Point()));
    levelButtons.add(new Button("Level 9", new Point()));
    levelButtons.add(new Button("Level 10", new Point()));
    levelButtons.get(0).setOnMouseClicked(event -> {
      HumanSnake humanSnake = new HumanSnake(5, 5, 1, Direction.DOWN, new Aim(10));
      humanSnake.configureChangingDirection(scene);

      List<BotSnake> botSnakes = new ArrayList<>();
      botSnakes.add(new BotSnake(10, 10, 1, Direction.LEFT, new Aim(5)));

      constructLevel(gc, images, humanSnake, 45, botSnakes);
    });
    loadMenu(gc);


    primaryStage.setTitle("Snake");
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();
  }

  @Override
  public void stop() throws Exception {
    currentController.stop();
  }

  private void clearWindow() {
    root.getChildren().remove(1, root.getChildren().size());
  }

  private void loadMenu(GraphicsContext gc) {
    clearWindow();
    root.getChildren().addAll(levelButtons);
    if (currentController != null) {
      currentController.stop();
    }
    currentController = new MenuController(gc, new Menu(WIDTH, HEIGHT, levelButtons));
    currentController.load();
  }

  private void constructLevel(GraphicsContext gc, List<Image> images, HumanSnake humanSnake, int squareSize, List<BotSnake> botSnakes) {
    clearWindow();
    currentController.stop();
    Field field = new Field(WIDTH, HEIGHT, squareSize);

    var exitButton = new Button("Exit", new Point());
    exitButton.setOnMouseClicked(event1 -> loadMenu(gc));
    root.getChildren().add(exitButton);
    currentController = new InGameController(gc, field, humanSnake, botSnakes, 150, images);
    currentController.load();
  }
}
