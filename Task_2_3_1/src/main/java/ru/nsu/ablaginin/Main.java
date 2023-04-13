package ru.nsu.ablaginin;

import javafx.scene.canvas.GraphicsContext;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.ablaginin.controller.Controller;
import ru.nsu.ablaginin.controller.ingame.InGameController;
import ru.nsu.ablaginin.controller.menu.MenuController;
import ru.nsu.ablaginin.helper.MainHelper;
import ru.nsu.ablaginin.model.ingame.BotSnake;
import ru.nsu.ablaginin.model.ingame.Field;
import ru.nsu.ablaginin.model.ingame.HumanSnake;
import ru.nsu.ablaginin.model.ingame.bricks.Aim;
import ru.nsu.ablaginin.model.ingame.bricks.Barrier;
import ru.nsu.ablaginin.model.ingame.bricks.Direction;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.nsu.ablaginin.model.ingame.builder.LevelBuilder;
import ru.nsu.ablaginin.model.menu.Menu;
import ru.nsu.ablaginin.model.menu.bricks.Button;
import ru.nsu.ablaginin.model.menu.builder.MenuBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Main starts an application. There can only be one working controller.
 */
public final class Main extends Application {
  public static final int WIDTH = 675;
  public static final int HEIGHT = 540;
  @Getter @Setter
  private Controller currentController;
  @Getter private final List<Button> levelButtons = new ArrayList<>();
  @Getter private final Group root = new Group();

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    // configuration
    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    root.getChildren().add(canvas);
    Scene scene = new Scene(root);
    var gc = canvas.getGraphicsContext2D();
    MainHelper.setMain(this);

    // prepare images
    List<Image> images = new ArrayList<>();

    for (int i = 1; i < 6; i++) {
      @Cleanup var is = getClass().getClassLoader().getResourceAsStream("img/food" + i + ".png");
      if (is != null) {
        images.add(new Image(is));
      }
    }

    MenuBuilder menuBuilder = new MenuBuilder(levelButtons);
    var exitButton = menuBuilder.buildNewMenu(gc);

    LevelBuilder levelBuilder = new LevelBuilder(exitButton, images);

    @Cleanup var is1 = getClass().getClassLoader().getResourceAsStream("levels/level1.json");
    if (is1 != null) {
      levelButtons.add(levelBuilder.buildNewLevel(gc, is1, scene));
    }
    levelButtons.add(levelBuilder.buildNewLevel(gc, new LevelBuilder.Config(
        "Level 1",
        45,
        1,
        new LevelBuilder.InitSnake(
            new Point(5, 5),
            1,
            "Up",
            5
        ),
        new LevelBuilder.InitSnake[]{},
        new Barrier[]{}
    ), scene));

    @Cleanup var is2 = getClass().getClassLoader().getResourceAsStream("levels/level2.json");
    if (is2 != null) {
      levelButtons.add(levelBuilder.buildNewLevel(gc, is2, scene));
    }

    // init game
    root.getChildren().addAll(levelButtons);
    currentController = new MenuController(gc, new Menu(WIDTH, HEIGHT, levelButtons));
    currentController.load();

    // post-configure
    primaryStage.setTitle("Snake");
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();
  }

  @Override
  public void stop() throws Exception {
    currentController.stop();
  }
}
