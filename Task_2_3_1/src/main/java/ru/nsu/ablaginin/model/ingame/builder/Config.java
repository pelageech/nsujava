package ru.nsu.ablaginin.model.ingame.builder;

import ru.nsu.ablaginin.model.ingame.bricks.Barrier;

import java.util.Objects;

public class Config {
  private final String name;
  private final int squareSize;
  private final int number;
  private final InitSnake human;
  private final InitSnake[] bots;
  private final Barrier[] barriers;

  public Config(
      String name,
      int squareSize,
      int number,
      InitSnake human,
      InitSnake[] bots,
      Barrier[] barriers
  ) {
    this.name = name;
    this.squareSize = squareSize;
    this.number = number;
    this.human = human;
    this.bots = bots;
    this.barriers = barriers;
  }

  public String name() {
    return name;
  }

  public int squareSize() {
    return squareSize;
  }

  public int number() {
    return number;
  }

  public InitSnake human() {
    return human;
  }

  public InitSnake[] bots() {
    return bots;
  }

  public Barrier[] barriers() {
    return barriers;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Config) obj;
    return Objects.equals(this.name, that.name) &&
        this.squareSize == that.squareSize &&
        this.number == that.number &&
        Objects.equals(this.human, that.human) &&
        Objects.equals(this.bots, that.bots) &&
        Objects.equals(this.barriers, that.barriers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, squareSize, number, human, bots, barriers);
  }

  @Override
  public String toString() {
    return "Config[" +
        "name=" + name + ", " +
        "squareSize=" + squareSize + ", " +
        "number=" + number + ", " +
        "human=" + human + ", " +
        "bots=" + bots + ", " +
        "barriers=" + barriers + ']';
  }

}