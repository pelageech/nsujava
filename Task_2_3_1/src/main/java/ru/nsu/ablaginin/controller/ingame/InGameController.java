package ru.nsu.ablaginin.controller.ingame;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import ru.nsu.ablaginin.Main;
import ru.nsu.ablaginin.controller.Controller;
import ru.nsu.ablaginin.model.ingame.BotSnake;
import ru.nsu.ablaginin.model.ingame.Field;
import ru.nsu.ablaginin.model.ingame.Food;
import ru.nsu.ablaginin.model.ingame.HumanSnake;
import ru.nsu.ablaginin.model.ingame.Snake;
import ru.nsu.ablaginin.model.menu.bricks.Button;
import ru.nsu.ablaginin.view.ingame.DrawField;
import ru.nsu.ablaginin.view.ingame.DrawFood;
import ru.nsu.ablaginin.view.ingame.DrawLose;
import ru.nsu.ablaginin.view.ingame.DrawSnake;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import lombok.Getter;
import ru.nsu.ablaginin.view.ingame.DrawWin;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class InGameController implements Controller {
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
  @Getter
  private DrawLose drawLose;
  @Getter
  private DrawWin drawWin;
  private long update;

  public InGameController(
      GraphicsContext gc,
      Field field,
      HumanSnake humanSnake,
      List<BotSnake> botSnakes,
      long timeUpdate,
      List<Image> images,
      Image winImage,
      Image loseImage
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
    drawLose = new DrawLose(loseImage, field.getWidth(), field.getHeight());
    drawWin = new DrawWin(winImage, field.getWidth(), field.getHeight());
  }

  public void view() {
    fieldView.draw(gc);
    foodView.draw(gc);
    snakesView
        .stream().filter(s -> !s.getSnake().isGameOver())
        .forEach(snakeView -> snakeView.draw(gc));
  }

  public void load() {
    food = Food.generate(0, field.getColumns(), 0, field.getRows(), field.getBarriers(), images);
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
                  snake.setGameOver(true);
                }
                if (snake.eatFood(food)) {
                  List<Point> points = new ArrayList<>(snakes.stream().flatMap(s -> s.getBody().stream()).toList());
                  points.addAll(field.getBarriers());
                  food = Food.generate(0, field.getColumns(), 0, field.getRows(), points, images);
                  foodView.setFood(food);
                }
                snake.setWon(snake.win());
            });
        view();
        if (humanSnake.isGameOver()) {
          stop();
          drawLose.draw(gc);
          return;
        }
        snakes = snakes.stream().filter(snake -> !snake.isGameOver()).toList();
        if (humanSnake.isWon()) {
          stop();
          drawWin.draw(gc);
          return;
        }
        for (var s : snakes) {
          if (s.win()) {
            stop();
            drawLose.draw(gc);
            return;
          }
        }
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
