package ru.nsu.ablaginin.model.ingame.builder;

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
import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class LevelBuilder {
  public record InitSnake(Point spawn, int velocity, String direction, int targetFood) {
  }

  public record Config(
      String name,
      int squareSize,
      int number,
      String musicPath,
      InitSnake human,
      InitSnake[] bots,
      Barrier[] barriers
  ) {
  }

  private Button exitButton;
  private List<Image> fruitImages;
  private Image winImage;
  private Image loseImage;

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
          media
      );
      MainHelper.replaceController(newController);

      MainHelper.clearWindow();

      MainHelper.putNode(exitButton);

      newController.load();
    });
    return button;
  }

  public Button buildNewLevel(GraphicsContext gc, InputStream is, Scene scene) {
    Gson gson = new Gson();
    var config = gson.fromJson(new BufferedReader(new InputStreamReader(is)), Config.class);
    if (config == null) {
      throw new IllegalArgumentException("json wasn't parsed");
    }
    return buildNewLevel(gc, config, scene);
  }
}
