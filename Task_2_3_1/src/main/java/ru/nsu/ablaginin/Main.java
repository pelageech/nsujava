package ru.nsu.ablaginin;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import ru.nsu.ablaginin.snake.Field;

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    Field field = new Field(2, 2, 200);
    primaryStage.setTitle("Snake");
    Group root = new Group();
    Canvas canvas = new Canvas(field.getWidth(), field.getHeight());
    root.getChildren().add(canvas);
    Scene scene = new Scene(root);
    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
    primaryStage.show();
    GraphicsContext gc = canvas.getGraphicsContext2D();
    field.drawField(gc);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
