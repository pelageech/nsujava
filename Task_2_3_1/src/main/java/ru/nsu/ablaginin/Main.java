package ru.nsu.ablaginin;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import ru.nsu.ablaginin.build.LevelBuilder;
import ru.nsu.ablaginin.build.Runner;
import ru.nsu.ablaginin.build.SnakeProperty;
import ru.nsu.ablaginin.field.Barrier;
import ru.nsu.ablaginin.snake.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {
  LevelBuilder builder;
  Runner runner;

  @Override
  public void start(Stage primaryStage) {
    builder = new LevelBuilder(
        35, 35, 20,
        new SnakeProperty(new Point(5, 5), 1, Direction.RIGHT)
    );
    List<SnakeProperty> bots = new ArrayList<>();
    bots.add(new SnakeProperty(new Point(12, 12), 1, Direction.DOWN));
    bots.add(new SnakeProperty(new Point(20, 20), 1, Direction.DOWN));
    builder.setBots(bots);
    builder.addBarriers(Arrays.asList(new Barrier(2, 3), new Barrier(2,4), new Barrier(8, 8)));

    var field = builder.build();

    primaryStage.setTitle("Snake");
    primaryStage.setResizable(false);
    primaryStage.setScene(field.getScene());
    primaryStage.show();

    runner = new Runner(field, builder.getGc());
    runner.run(100);
  }

  @Override
  public void stop() throws Exception {
    runner.stop();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
