package ru.nsu.ablaginin.model.ingame.builder;

import ru.nsu.ablaginin.model.ingame.bricks.Point;

import java.util.Objects;

public class InitSnake {
  private final Point spawn;
  private final int velocity;
  private final String direction;
  private final int targetFood;

  public InitSnake(Point spawn, int velocity, String direction, int targetFood) {
    this.spawn = spawn;
    this.velocity = velocity;
    this.direction = direction;
    this.targetFood = targetFood;
  }

  public Point spawn() {
    return spawn;
  }

  public int velocity() {
    return velocity;
  }

  public String direction() {
    return direction;
  }

  public int targetFood() {
    return targetFood;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (InitSnake) obj;
    return Objects.equals(this.spawn, that.spawn) &&
        this.velocity == that.velocity &&
        Objects.equals(this.direction, that.direction) &&
        this.targetFood == that.targetFood;
  }

  @Override
  public int hashCode() {
    return Objects.hash(spawn, velocity, direction, targetFood);
  }

  @Override
  public String toString() {
    return "InitSnake[" +
        "spawn=" + spawn + ", " +
        "velocity=" + velocity + ", " +
        "direction=" + direction + ", " +
        "targetFood=" + targetFood + ']';
  }

}