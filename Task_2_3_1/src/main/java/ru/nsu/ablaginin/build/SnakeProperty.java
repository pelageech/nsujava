package ru.nsu.ablaginin.build;

import ru.nsu.ablaginin.snake.Direction;

import java.awt.*;

public record SnakeProperty(Point spawn, int velocity, Direction direction) {
}
