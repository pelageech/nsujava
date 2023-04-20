package ru.nsu.ablaginin.model.ingame;

import javafx.scene.image.Image;
import java.awt.Point;
import java.util.List;
import java.util.Random;

/**
 * The food that can be eaten by snakes.
 *
 * @param x x-coordinate
 * @param y y-coordinate
 * @param image food's image
 */
public record Food(int x, int y, Image image) {
  /**
   * Generates new food on the field set by fromX, toX, fromY and toY
   * except some points.
   * Puts a new random image for the food.
   *
   * @param fromX fromX
   * @param toX toX
   * @param fromY fromY
   * @param toY toY
   * @param except excepting points
   * @param images images
   * @return new Food
   */
  public static Food generate(int fromX, int toX, int fromY, int toY, List<? extends Point> except, List<Image> images) {
    Random random = new Random();

    int foodX;
    int foodY;
    var foodImage = images.get(random.nextInt(0, images.size()));

    start:
    while (true) {
      foodX = random.nextInt(fromX, toX);
      foodY = random.nextInt(fromY, toY);

      for (var p : except) {
        if (p.x == foodX && p.y == foodY) {
          continue start;
        }
      }
      break;
    }
    return new Food(foodX, foodY, foodImage);
  }
}
