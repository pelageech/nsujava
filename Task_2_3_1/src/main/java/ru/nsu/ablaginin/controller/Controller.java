package ru.nsu.ablaginin.controller;

import ru.nsu.ablaginin.model.BotSnake;
import ru.nsu.ablaginin.model.Field;
import ru.nsu.ablaginin.model.Food;
import ru.nsu.ablaginin.model.HumanSnake;
import ru.nsu.ablaginin.model.Snake;
import ru.nsu.ablaginin.view.DrawField;
import ru.nsu.ablaginin.view.DrawFood;
import ru.nsu.ablaginin.view.DrawSnake;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import lombok.Getter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
  private GraphicsContext gc;
  private Timer timer = new Timer("controller");
  private Field field;
  @Getter
  private DrawField fieldView;
  private Food food;
  @Getter
  private DrawFood foodView;
  private List<Image> images;
  private HumanSnake humanSnake;
  private List<BotSnake> botSnakes;
  private List<Snake> snakes = new ArrayList<>();
  @Getter
  private List<DrawSnake> snakesView = new ArrayList<>();
  private long update;

  public Controller(
      GraphicsContext gc,
      Field field,
      HumanSnake humanSnake,
      List<BotSnake> botSnakes,
      long timeUpdate,
      List<Image> images
  ) {
    this.field = field;
    this.humanSnake = humanSnake;
    this.botSnakes = botSnakes;
    snakes.add(humanSnake);
    snakes.addAll(botSnakes);
    this.update = timeUpdate;
    this.gc = gc;

    fieldView = new DrawField(
        field,
        Color.web("AAD751"),
        Color.web("A2D149"),
        Color.DARKSEAGREEN
    );
    foodView = new DrawFood(food, field.getSquareSize());
    snakes
        .forEach(snake -> snakesView.add(new DrawSnake(
            snake,
            Color.web("4674E9"),
            Color.web("A604E9"),
            field.getSquareSize()
        )));

    this.images = images;
  }

  public void view() {
    fieldView.draw(gc);
    foodView.draw(gc);
    snakesView
        .stream().filter(s -> !s.getSnake().isGameOver())
        .forEach(snakeView -> snakeView.draw(gc));
  }

  public void load() {
    food = Food.generate(0, field.getColumns(), 0, field.getRows(), new ArrayList<>(), images);
    foodView.setFood(food);

    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        view();

        if (snakes.size() == 0) {
          timer.cancel();
          return;
        }

        botSnakes.stream()
            .filter(snake -> !snake.isGameOver())
            .forEach(snake -> snake.analyze(field, food, snakes));

        snakes
            .forEach(snake -> {
                snake.move();
                if (die(snake)) {
                  return;
                }
                if (snake.eatFood(food)) {
                  List<Point> points = new ArrayList<>(snakes.stream().flatMap(s -> s.getBody().stream()).toList());
                  points.addAll(field.getBarriers());
                  food = Food.generate(0, field.getColumns(), 0, field.getRows(), points, images);
                  foodView.setFood(food);
                }
            });

        snakes = snakes.stream().filter(snake -> !snake.isGameOver()).toList();
      }
    }, 0, update);
  }

  public void stop() {
    timer.cancel();
  }

  private boolean die(Snake snake) {
    var head = snake.getHead();

    // out of field
    if (head.x < 0 || head.y < 0 || head.x >= field.getColumns() || head.y >= field.getRows()) {
      snake.setGameOver(true);
      return true;
    }

    // bumped into another snake
    for (var s : snakes) {
      if (s == snake || s.isGameOver()) {
        continue;
      }
      for (var p : s.getBody()) {
        if (p.x == head.x && p.y == head.y) {
          snake.setGameOver(true);
          return true;
        }
      }
    }

    // bumped into itself
    List<Point> body = snake.getBody();
    for (int i = 1; i < body.size(); i++) {
      Point p = body.get(i);
      if (p.x == head.x && p.y == head.y) {
        snake.setGameOver(true);
        return true;
      }
    }

    // bumped into barrier
    for (var p : field.getBarriers()) {
      if (p.x == head.x && p.y == head.y) {
        snake.setGameOver(true);
        return true;
      }
    }
    return false;
  }
}
