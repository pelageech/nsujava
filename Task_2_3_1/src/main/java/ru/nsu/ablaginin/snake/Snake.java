package ru.nsu.ablaginin.snake;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.ablaginin.field.Field;
import ru.nsu.ablaginin.field.Food;
import ru.nsu.ablaginin.field.FoodController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Snake extends Thread {
  public static final int BODY_SIZE = 3;
  private final List<Point> body = new ArrayList<>();
  private final Point head;
  private Direction direction = Direction.UP;
  private int velocity;
  private Field field;
  private boolean gameOver = false;

  Snake(Field field, Point spawn, int velocity) {

    this.field = field;
    field.getSnakes().add(this);

    this.velocity = velocity;

    if (spawn.x < 0 || spawn.x >= field.getColumns()
        || spawn.y < 0 || spawn.y >= field.getRows()) {
      throw new IllegalArgumentException();
    }

    for (int i = 0; i < BODY_SIZE; i++) {
      body.add(new Point(spawn.x, spawn.y));
    }
    head = body.get(0);
  }

  abstract void changeDirection();

  public void eatFood(Food food) {
    if (head.x == food.point().x && head.y == food.point().y) {
      var deltaX = body.get(body.size() - 2).x - body.get(body.size() - 1).x;
      var deltaY = body.get(body.size() - 2).y - body.get(body.size() - 1).y;
      body.add(new Point(body.get(body.size() - 1).x + deltaX, body.get(body.size() - 1).y + deltaY));
      field.setFood(field.getController().generateFood(field.getSnakes()));
    }
  }

  public void move() {
    for (int i = body.size() - 1; i >= 1; i--) {
      body.get(i).x = body.get(i - 1).x;
      body.get(i).y = body.get(i - 1).y;
    }
    switch (direction) {
      case UP -> head.y -= velocity;
      case DOWN -> head.y += velocity;
      case LEFT -> head.x -= velocity;
      case RIGHT -> head.x += velocity;
    }
  }

  public void gameOver() {
    if (head.getX() < 0
        || head.getY() < 0
        || head.getX() * field.getSquareSize() >= field.getWidth()
        || head.getY() * field.getSquareSize() >= field.getHeight()
    ) {
      gameOver = true;
    }
    for (int i = 1; i < body.size(); i++) {
      if (head.getX() == body.get(i).getX() && head.getY() == body.get(i).getY()) {
        gameOver = true;
        break;
      }
    }
  }

  public void drawSnake(GraphicsContext gc) {
    var squareSize = field.getSquareSize();
    gc.setFill(Color.web("4674E9"));
    gc.fillRoundRect(head.x * squareSize, head.y * squareSize, squareSize - 1, squareSize - 1, squareSize, squareSize);

    for (int i = 1; i < body.size(); i++) {
      gc.fillRoundRect(body.get(i).x * squareSize, body.get(i).y * squareSize, squareSize - 1, squareSize - 1, squareSize / 2., squareSize / 2.);
    }
  }

  public List<Point> getBody() {
    return body;
  }

  public Point getHead() {
    return head;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public Field getField() {
    return field;
  }

  public int getVelocity() {
    return velocity;
  }

  public boolean isGameOver() {
    return gameOver;
  }

  @Override
  public void run() {
    changeDirection();
  }
}
