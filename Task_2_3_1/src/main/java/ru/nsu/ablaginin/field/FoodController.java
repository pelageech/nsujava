package ru.nsu.ablaginin.field;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import ru.nsu.ablaginin.snake.Snake;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FoodController {
  private static final String[] FOODS = new String[]{
      "img/food1.png",
      "img/food2.png",
      "img/food3.png",
      "img/food4.png",
      "img/food5.png"
  };

  private final Field field;

  public FoodController(Field field) {
    this.field = field;
  }

  public Food generateFood(Point[] except) {
    Point foodPoint;
    Image foodImage;
    var str = FOODS[(int)(Math.random() * FOODS.length)];
    start:
    while (true) {
      int foodX = (int)(Math.random() * field.getColumns());
      int foodY = (int)(Math.random() * field.getRows());

      for (var p : except) {
        if (p.x == foodX && p.y == foodY) {
          continue start;
        }
      }

      System.out.println(str);
      foodImage = new Image(Objects.requireNonNull(FoodController.class.getClassLoader().getResourceAsStream(str)));
      foodPoint = new Point(foodX, foodY);
      break;
    }
    return new Food(foodImage, foodPoint);
  }

  public Food generateFood(List<Snake> snakes) {
    List<Point> exceptPoints = new ArrayList<>();
    for (var s : snakes) {
      exceptPoints.addAll(s.getBody());
    }
    return this.generateFood(exceptPoints.toArray(new Point[0]));
  }

  public void drawFood(Food food, GraphicsContext gc) {
    gc.drawImage(food.image(),
        food.point().x * field.getSquareSize(),
        food.point().y * field.getSquareSize(),
        field.getSquareSize(),
        field.getSquareSize()
    );
  }

}
