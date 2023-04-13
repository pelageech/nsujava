package ru.nsu.ablaginin.model.ingame;

import javafx.scene.image.Image;
import lombok.Getter;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Food {
  @Getter private int x;
  @Getter private int y;
  @Getter private Image image;

  public Food(int x, int y, Image image) {
    this.x = x;
    this.y = y;
    this.image = image;
  }

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
