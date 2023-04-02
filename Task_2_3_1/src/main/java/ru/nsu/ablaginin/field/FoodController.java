package ru.nsu.ablaginin.field;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import ru.nsu.ablaginin.snake.Snake;
import ru.nsu.ablaginin.snake.SnakeAtField;

import java.awt.*;
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

  public Food generateFood(List<Snake> snakes) {
    Point foodPoint;
    Image foodImage;
    start:
    while (true) {
      int foodX = (int)(Math.random() * field.getRows());
      int foodY = (int)(Math.random() * field.getColumns());

      for (var snake : snakes) {
        var body = snake.getBody();
        for (var p : body) {
          if (p.getX() == foodX && p.getY() == foodY) {
            continue start;
          }
        }
      }

      var str = FOODS[(int)(Math.random() * FOODS.length)];
      System.out.println(str);
      foodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(str)));
      foodPoint = new Point(foodX, foodY);
      break;
    }
    return new Food(foodImage, foodPoint);
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
