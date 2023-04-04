package ru.nsu.ablaginin.build;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.robot.Robot;
import ru.nsu.ablaginin.field.Field;
import ru.nsu.ablaginin.snake.Direction;
import ru.nsu.ablaginin.snake.Snake;
import ru.nsu.ablaginin.snake.SnakeBot;
import ru.nsu.ablaginin.snake.SnakeHuman;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LevelBuilder {
  private int columns;
  private int rows;
  private int squareSize;
  private final SnakeProperty humanProperty;
  private List<SnakeProperty> botProperties = new ArrayList<>();
  private final List<Snake> snake = new ArrayList<>();

  private GraphicsContext gc;

  public LevelBuilder(int columns, int rows, int squareSize, SnakeProperty human) {
    this.columns = columns;
    this.rows = rows;
    this.squareSize = squareSize;
    humanProperty = human;
  }

  public Field build() {
    Group root = new Group();
    Scene scene = new Scene(root);

    Field field = new Field(columns, rows, squareSize, scene);
    field.setFood(field.getController().generateFood(field.getSnakes()));

    Canvas canvas = new Canvas(field.getWidth(), field.getHeight());
    root.getChildren().add(canvas);

    gc = canvas.getGraphicsContext2D();

    snake.clear();
    snake.add(new SnakeHuman(
        field, humanProperty.spawn(), humanProperty.velocity(), humanProperty.direction()
    ));
    for (SnakeProperty prop : botProperties) {
      snake.add(new SnakeBot(
          field, prop.spawn(), prop.velocity(), prop.direction(), new Robot()
      ));
    }

    return field;
  }

  public int getColumns() {
    return columns;
  }

  public void setColumns(int columns) {
    this.columns = columns;
  }

  public int getRows() {
    return rows;
  }

  public void setRows(int rows) {
    this.rows = rows;
  }

  public int getSquareSize() {
    return squareSize;
  }

  public void setSquareSize(int squareSize) {
    this.squareSize = squareSize;
  }

  public void setBots(List<SnakeProperty> properties) {
    this.botProperties = List.copyOf(properties);
  }

  public GraphicsContext getGc() {
    return gc;
  }
}
