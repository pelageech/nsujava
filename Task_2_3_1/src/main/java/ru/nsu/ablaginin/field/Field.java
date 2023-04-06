package ru.nsu.ablaginin.field;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.ablaginin.snake.Snake;

import java.util.ArrayList;
import java.util.List;

public class Field {
  private final int width;
  private final int height;
  private final int columns;
  private final int rows;
  private final int squareSize;

  private final Scene scene;
  private final List<Snake> snakes = new ArrayList<>();
  private final FoodController controller;
  private Food food;
  private List<Barrier> barriers = new ArrayList<>();

  public Field(int columns, int rows, int squareSize, Scene scene) {
    this.columns = columns;
    this.rows = rows;
    this.squareSize = squareSize;
    width = squareSize * columns;
    height = squareSize * rows;
    controller = new FoodController(this);
    this.scene = scene;
  }

  public void drawField(GraphicsContext gc) {
    for (int i = 0; i < columns; i++) {
      for (int j = 0; j < rows; j++) {
        if ((i + j) % 2 == 0) {
          gc.setFill(Color.web("AAD751"));
        } else {
          gc.setFill(Color.web("A2D149"));
        }
        gc.fillRect(i * squareSize, j * squareSize, squareSize, squareSize);
      }
    }
    for (var b : barriers) {
      b.drawBarrier(gc, squareSize);
    }
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int getColumns() {
    return columns;
  }

  public int getRows() {
    return rows;
  }

  public int getSquareSize() {
    return squareSize;
  }

  public Food getFood() {
    return food;
  }

  public void setFood(Food food) {
    this.food = food;
  }

  public List<Snake> getSnakes() {
    return snakes;
  }

  public FoodController getController() {
    return controller;
  }

  public Scene getScene() {
    return scene;
  }

  public void setBarriers(List<Barrier> barriers) {
    this.barriers = barriers;
  }
}
