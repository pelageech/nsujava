package ru.nsu.ablaginin.view.ingame;

import ru.nsu.ablaginin.model.ingame.Snake;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.ablaginin.view.Drawable;

import java.util.stream.IntStream;

/**
 * View module for drawing snake.
 */
@AllArgsConstructor
public class DrawSnake implements Drawable {
  @Getter private Snake snake;
  @Setter private Color headColor;
  @Setter private Color bodyColor;
  private int squareSize;

  @Override
  public void draw(GraphicsContext gc) {
    gc.setFill(headColor); // 4674E9
    gc.fillRoundRect(
        snake.getHead().x * squareSize,
        snake.getHead().y * squareSize,
        squareSize - 1,
        squareSize - 1,
        squareSize,
        squareSize
    );
    gc.setFill(bodyColor);

    IntStream
        .range(1, snake.getBody().size())
        .forEach(
            i -> gc.fillRoundRect(
                snake.getBody().get(i).x * squareSize,
                snake.getBody().get(i).y * squareSize,
                squareSize - 1,
                squareSize - 1,
                squareSize / 2.,
                squareSize / 2.
            )
        );
  }
}
