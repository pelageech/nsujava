package ru.nsu.ablaginin.builder;

import com.google.gson.Gson;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import lombok.AllArgsConstructor;
import ru.nsu.ablaginin.Main;
import ru.nsu.ablaginin.controller.ingame.InGameController;
import ru.nsu.ablaginin.helper.MainHelper;
import ru.nsu.ablaginin.model.ingame.BotSnake;
import ru.nsu.ablaginin.model.ingame.Field;
import ru.nsu.ablaginin.model.ingame.HumanSnake;
import ru.nsu.ablaginin.model.ingame.bricks.Aim;
import ru.nsu.ablaginin.model.ingame.bricks.Barrier;
import ru.nsu.ablaginin.model.ingame.bricks.Direction;
import ru.nsu.ablaginin.model.menu.bricks.Button;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Builds levels. Returns a button.
 */
@AllArgsConstructor
public class LevelBuilder {
  /**
   * The information from snake init.
   *
   * @param spawn spawn point
   * @param velocity velocity
   * @param direction direction
   * @param targetFood target food aim
   */
  public record InitSnake(Point spawn, int velocity, String direction, int targetFood) {
  }

  /**
   * The configuration of the level.
   *
   * @param name name
   * @param squareSize square size
   * @param number number
   * @param musicPath music path
   * @param human human
   * @param bots bots
   * @param barriers barriers
   */
  public record Config(
      String name,
      int squareSize,
      int number,
      String musicPath,
      InitSnake human,
      InitSnake[] bots,
      Barrier[] barriers,
      int maxFood
  ) {
  }

  private Button exitButton;
  private List<Image> fruitImages;
  private Image winImage;
  private Image loseImage;

  /**
   * Creates a button that loads a level.
   * Takes a config that is a record.
   *
   * @param gc graphical context
   * @param config level configuration
   * @param scene current scene
   * @return a button for loading a level
   */
  public Button buildNewLevel(GraphicsContext gc, Config config, Scene scene) {
    var button = new Button(config.name(), new Point());
    button.setOnMouseClicked(event -> {
      HumanSnake humanSnake = new HumanSnake(
          config.human().spawn().x,
          config.human().spawn().y,
          config.human().velocity(),
          Direction.fromString(config.human().direction()),
          new Aim(config.human().targetFood())
      );
      humanSnake.configureChangingDirection(scene);

      List<BotSnake> botSnakes = new ArrayList<>();
      for (var b : config.bots()) {
        botSnakes.add(new BotSnake(
            b.spawn().x,
            b.spawn().y,
            b.velocity(),
            Direction.fromString(b.direction()),
            new Aim(b.targetFood())
        ));
      }

      var field = new Field(Main.WIDTH, Main.HEIGHT, config.squareSize);
      field.setBarriers(Arrays.stream(config.barriers()).toList());

      var url = getClass().getClassLoader().getResource("music/" + config.musicPath());
      if (url == null) {
        throw new IllegalArgumentException("menu musicPath not found");
      }
      Media media = new Media(url.toExternalForm());

      var newController = new InGameController(
          gc,
          field,
          humanSnake,
          botSnakes,
          150,
          fruitImages,
          winImage,
          loseImage,
          media, config.maxFood
      );
      MainHelper.replaceController(newController);

      MainHelper.clearWindow();

      MainHelper.putNode(exitButton);

      newController.load();
    });
    return button;
  }

  /**
   * Parses json to Config and builds a level.
   * Uses the previous function.
   *
   * @param gc graphical controller
   * @param is input stream with json
   * @param scene current scenes
   * @return a button for loading a level
   */
  public Button buildNewLevel(GraphicsContext gc, InputStream is, Scene scene) {
    Gson gson = new Gson();
    var config = gson.fromJson(new BufferedReader(new InputStreamReader(is)), Config.class);
    if (config == null) {
      throw new IllegalArgumentException("json wasn't parsed");
    }
    return buildNewLevel(gc, config, scene);
  }
}
