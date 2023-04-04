package ru.nsu.ablaginin;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.nsu.ablaginin.build.LevelBuilder;
import ru.nsu.ablaginin.build.Runner;
import ru.nsu.ablaginin.build.SnakeProperty;
import ru.nsu.ablaginin.snake.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
  LevelBuilder builder;
  Runner runner;

  @Override
  public void start(Stage primaryStage) {
    builder = new LevelBuilder(
        25, 25, 20,
        new SnakeProperty(new Point(5, 5), 1, Direction.UP)
    );

    List<SnakeProperty> bots = new ArrayList<>();
    bots.add(new SnakeProperty(new Point(12, 12), 1, Direction.DOWN));
    builder.setBots(bots);

    var field = builder.build();

    primaryStage.setTitle("Snake");
    primaryStage.setResizable(false);
    primaryStage.setScene(field.getScene());
    primaryStage.show();

    runner = new Runner(field, builder.getGc());
    runner.run(130);
  }

  @Override
  public void stop() throws Exception {
    runner.stop();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
