package ru.nsu.ablaginin.build;

import javafx.scene.canvas.GraphicsContext;
import ru.nsu.ablaginin.field.Field;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

public class Runner {
  private Timer timer;
  private final Field field;
  private final GraphicsContext gc;

  public Runner(Field field, GraphicsContext gc) {
    this.field = field;
    this.gc = gc;
  }

  private void iteration() {
    field.drawField(gc);
    field.getFood().drawFood(gc, field.getSquareSize());
    for (var s : field.getSnakes()) {
      if (!s.isGameOver()) {
        s.drawSnake(gc);
      }
    }
  }

  public void run(long milliseconds) {
    for (var s : field.getSnakes()) {
      s.run();
    }

    timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        iteration();
      }
    }, 0, milliseconds);
  }

  public void stop() {
    timer.cancel();
    for (var s : field.getSnakes()) {
      s.stop();
    }
  }
}
