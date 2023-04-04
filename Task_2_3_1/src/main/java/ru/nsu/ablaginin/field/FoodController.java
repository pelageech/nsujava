package ru.nsu.ablaginin.field;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import ru.nsu.ablaginin.snake.Snake;

import java.awt.*;
import java.io.InputStream;
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

  private final List<InputStream> foodInputStreams;

  private final Field field;

  public FoodController(Field field) {
    this.field = field;
    foodInputStreams = new ArrayList<>();
    for (String food : FOODS) {
      var is = getClass().getClassLoader().getResourceAsStream(food);
      if (is != null) {
        foodInputStreams.add(is);
        System.out.println("Image " + food + " has been successfully opened!");
      } else {
        System.out.println("Image " + food + " won't open!");
      }
    }
    if (foodInputStreams.size() == 0) {
      throw new IllegalArgumentException("there isn't any images opened");
    }
  }

  public Food generateFood(Point[] except) {
    Point foodPoint;
    var chosenImage = foodInputStreams.get((int)(Math.random() * foodInputStreams.size()));
    var foodImage = new Image(chosenImage);

    start:
    while (true) {
      int foodX = (int)(Math.random() * field.getColumns());
      int foodY = (int)(Math.random() * field.getRows());

      for (var p : except) {
        if (p.x == foodX && p.y == foodY) {
          continue start;
        }
      }

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
