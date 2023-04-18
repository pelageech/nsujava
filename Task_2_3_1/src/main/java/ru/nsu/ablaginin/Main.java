package ru.nsu.ablaginin;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import ru.nsu.ablaginin.controller.Controller;
import ru.nsu.ablaginin.controller.menu.MenuController;
import ru.nsu.ablaginin.helper.MainHelper;
import ru.nsu.ablaginin.builder.LevelBuilder;
import ru.nsu.ablaginin.model.menu.Menu;
import ru.nsu.ablaginin.model.menu.bricks.Button;
import ru.nsu.ablaginin.builder.MenuBuilder;

/**
 * Main starts an application. There can only be one working controller.
 */
public final class Main extends Application {
  public static final int WIDTH = 900;
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

    // menu and button
    MenuBuilder menuBuilder = new MenuBuilder(levelButtons);
    var exitButton = menuBuilder.buildNewMenu(gc);

    // build levels
    @Cleanup var winIs = getClass().getClassLoader().getResourceAsStream("img/win.jpg");
    if (winIs == null) {
      throw new NoSuchFileException("no win image");
    }
    Image winImage = new Image(winIs);

    @Cleanup var loseIs = getClass().getClassLoader().getResourceAsStream("img/dead.jpeg");
    if (loseIs == null) {
      throw new NoSuchFileException("no lose image");
    }
    Image loseImage = new Image(loseIs);

    LevelBuilder levelBuilder = new LevelBuilder(exitButton, images, winImage, loseImage);

    for (int i = 0; i < 10; i++) {
      @Cleanup var is = getClass().getClassLoader().getResourceAsStream("levels/level" + i + ".json");
      if (is != null) {
        levelButtons.add(levelBuilder.buildNewLevel(gc, is, scene));
      }
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
  public void stop() {
    currentController.stop();
  }
}
