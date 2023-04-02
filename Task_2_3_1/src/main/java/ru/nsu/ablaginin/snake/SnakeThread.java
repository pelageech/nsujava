package ru.nsu.ablaginin.snake;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class SnakeThread extends Thread {
  private final Snake snake;

  public SnakeThread(Snake snake, GraphicsContext gc) {
    this.snake = snake;
  }
  public void drawSnake(GraphicsContext gc) {
    var head = snake.getHead();
    var squareSize = snake.getField().getSquareSize();
    gc.setFill(Color.web("4674E9"));
    gc.fillRoundRect(head.x * squareSize, head.y * squareSize, squareSize - 1, squareSize - 1, squareSize, squareSize);

    for (int i = 1; i < snake.getBody().size(); i++) {
      var body = snake.getBody().get(i);
      gc.fillRoundRect(body.x * squareSize, body.y * squareSize, squareSize - 1, squareSize - 1, squareSize / 2., squareSize / 2.);
    }
  }

  public Snake getSnake() {
    return snake;
  }

  @Override
  public void run() {
    snake.changeDirection();
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(130), event -> {
      if (!snake.isGameOver()) {
        snake.move();
        snake.gameOver();
        snake.eatFood(snake.getField().getFood());
      }
    }));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
  }
}
