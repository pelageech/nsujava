package ru.nsu.ablaginin.controller.ingame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lombok.Getter;
import ru.nsu.ablaginin.controller.Controller;
import ru.nsu.ablaginin.model.ingame.*;
import ru.nsu.ablaginin.view.ingame.*;
import java.awt.Point;
import java.util.*;

/**
 * An in-game controller that takes all the in-game
 * models and uses views for drawing a level.
 */
public class InGameController implements Controller {
  // graphics
  private final GraphicsContext gc;
  @Getter
  private final DrawField fieldView;
  @Getter
  private final List<DrawFood> foodView = new ArrayList<>();
  @Getter
  private final List<DrawSnake> snakesView = new ArrayList<>();
  @Getter
  private final DrawLose drawLose;
  @Getter
  private final DrawWin drawWin;

  // models
  private final Field field;
  private final List<Food> foods = new ArrayList<>();
  private final List<Image> images;
  private final HumanSnake humanSnake;
  private final List<BotSnake> botSnakes;
  private List<Snake> snakes = new ArrayList<>();


  // media
  private MediaPlayer player;
  private Media lose;
  private Media win;

  // frame params
  private final Timer timer = new Timer("controller");
  private final long update;

  /**
   * Creates a single InGameController.
   *
   * @param gc graphical context
   * @param field a field with barriers
   * @param humanSnake a manual control snake
   * @param botSnakes robots
   * @param timeUpdate a period for frame update
   * @param images food images
   * @param winImage win image
   * @param loseImage lose image
   * @param media a music for a game
   */
  public InGameController(
          GraphicsContext gc,
          Field field,
          HumanSnake humanSnake,
          List<BotSnake> botSnakes,
          long timeUpdate,
          List<Image> images,
          Image winImage,
          Map<Snake.DeathType, Image> loseImage,
          Media media,
          int maxFood,
          Color fieldColorOne,
          Color fieldColorTwo,
          Color snakeBody,
          Color snakeHead,
          Color botBody,
          Color botHead,
          Color barrier
  ) {
    turnOnMusic(media);
    this.gc = gc;
    this.update = timeUpdate;
    this.images = images;

    this.field = field;
    fieldView = new DrawField(
        field,
            fieldColorOne,
        fieldColorTwo,
        Color.DARKSEAGREEN
    );
    fieldView.setColorBarrier(barrier);

    this.humanSnake = humanSnake;
    this.botSnakes = botSnakes;
    snakes.add(humanSnake);
    snakes.addAll(botSnakes);
    snakes
        .forEach(snake -> snakesView.add(new DrawSnake(
            snake,
            botHead,
            botBody,
            field.getSquareSize()
        )));
    snakesView.get(0).setHeadColor(snakeHead);
    snakesView.get(0).setBodyColor(snakeBody);

    // lose
    var losePath = getClass().getClassLoader().getResource("music/lose.mp3");
    if (losePath != null) {
      lose = new Media(losePath.toExternalForm());
    }
    drawLose = new DrawLose(Snake.DeathType.NONE, loseImage, field.getWidth(), field.getHeight());

    // win
    var winPath = getClass().getClassLoader().getResource("music/win.mp3");
    if (winPath != null) {
      win = new Media(winPath.toExternalForm());
    }
    drawWin = new DrawWin(winImage, field.getWidth(), field.getHeight());

    // food
    for (int i = 0; i < maxFood; i++) {
      foods.add(null);
    }
  }

  @Override
  public void load() {
    Random random = new Random();

    // preloader
    player.play();
    for (var b : botSnakes) {
      b.setIndexHuntFood(random.nextInt(0, foods.size()));
    }

    for (int i = 0; i < foods.size(); i++) {
      var food = Food.generate(
              0, field.getColumns(),
              0, field.getRows(),
              field.getBarriers(), foodToPoint(), images
      );

      foods.set(i, food);
      foodView.add(new DrawFood(food, field.getSquareSize()));
    }

    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        updateFrame(random);
      }
    }, 0, update);
  }

  @Override
  public void stop() {
    player.stop();
    timer.cancel();
  }
  
  private void updateFrame(Random random) {
    view();
    if (snakes.isEmpty()) {
      timer.cancel();
      return;
    }

    // choose the next direction for each bot
    botSnakes.stream()
          .filter(snake -> !snake.isGameOver())
          .forEach(snake -> snake.analyze(field, foods.get(snake.getIndexHuntFood()), snakes));

    // update snakes' state
    snakes
          .forEach(snake -> {
            snake.move();
            die(snake);
            for (var i = 0; i < foods.size(); i++) {
              ifFoodEaten(random, snake, i);
            }
            snake.setWon(snake.win());
          });

    view();

    // handle win/lose human snake
    if (humanSnake.isGameOver()) {
      lose(humanSnake.getDeathType());
      return;
    }
    snakes = snakes.stream().filter(snake -> !snake.isGameOver()).toList();
    if (humanSnake.isWon()) {
      win();
      return;
    }
    for (var s : botSnakes) {
      if (s.win()) {
        lose(Snake.DeathType.ANOTHER_SNAKE_GOT_TARGET);
        return;
      }
    }
    gc.setEffect(new DropShadow());
    gc.setFont(new Font("Verdana", 15));
    gc.setFill(Color.WHITE);
    gc.fillText("Eaten: " + humanSnake.getEaten() + "/" + humanSnake.getAim().foodCollect(), 0, field.getHeight()-10);
    gc.setEffect(null);
  }

  private void view() {
    fieldView.draw(gc);
    for (var v : foodView) {
      v.draw(gc);
    }
    snakesView
            .stream().filter(s -> !s.getSnake().isGameOver())
            .forEach(snakeView -> snakeView.draw(gc));
  }

  private List<Point> foodToPoint() {
    List<Point> list = new ArrayList<>();
    for (var f : foods) {
      if (f == null) {
        continue;
      }
      list.add(new Point(f.x(), f.y()));
    }
    return list;
  }

  private void turnOnMusic(Media media) {
    if (media == null) {
      return;
    }
    if (player != null) {
      player.stop();
    }
    player = new MediaPlayer(media);
    player.play();
  }

  private void ifFoodEaten(Random random, Snake snake, int foodIndex) {
    if (snake.eatFood(foods.get(foodIndex))) {
      if (snake instanceof BotSnake) {
        ((BotSnake) snake).setIndexHuntFood(random.nextInt(0, foods.size()));
      }
      List<Point> points = new ArrayList<>(
              snakes.stream().flatMap(s -> s.getBody().stream()).toList()
      );

      foods.set(foodIndex, null);
      points.addAll(field.getBarriers());
      foods.set(foodIndex, Food.generate(0,
              field.getColumns(), 0, field.getRows(),
              points, foodToPoint(),
              images)
      );
      foodView.get(foodIndex).setFood(foods.get(foodIndex));
    }
  }

  private void win() {
    stop();
    drawWin.draw(gc);
    if (win != null) {
      turnOnMusic(win);
    }
  }

  private void lose(Snake.DeathType deathType) {
    stop();
    drawLose.setDeathType(deathType);
    drawLose.draw(gc);
    if (lose != null) {
      turnOnMusic(lose);
    }
  }

  private void die(Snake snake) {
    var head = snake.getHead();

    // out of field
    if (head.x < 0 || head.y < 0 || head.x >= field.getColumns() || head.y >= field.getRows()) {
      snake.setDeathType(Snake.DeathType.BUMP_BARRIER);
      snake.setGameOver(true);
    }

    // bumped into another snake
    for (var s : snakes) {
      if (s == snake || s.isGameOver()) {
        continue;
      }
      for (var p : s.getBody()) {
        if (p.x == head.x && p.y == head.y) {
          snake.setGameOver(true);
          snake.setDeathType(Snake.DeathType.BUMP_SNAKE);
        }
      }
    }

    // bumped into itself
    List<Point> body = snake.getBody();
    for (int i = 1; i < body.size(); i++) {
      Point p = body.get(i);
      if (p.x == head.x && p.y == head.y) {
        snake.setGameOver(true);
        snake.setDeathType(Snake.DeathType.SELF_EATEN);
      }
    }

    // bumped into barrier
    for (var p : field.getBarriers()) {
      if (p.x == head.x && p.y == head.y) {
        snake.setGameOver(true);
        snake.setDeathType(Snake.DeathType.BUMP_BARRIER);
      }
    }
  }
}
