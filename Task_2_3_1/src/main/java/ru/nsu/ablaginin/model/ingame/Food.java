package ru.nsu.ablaginin.model.ingame;

import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.awt.Point;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
public record Food(int x, int y, Image image) {
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
