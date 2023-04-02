package ru.nsu.ablaginin;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import ru.nsu.ablaginin.field.Field;
import ru.nsu.ablaginin.field.Food;
import ru.nsu.ablaginin.field.FoodController;

import java.util.ArrayList;

public class Main extends Application {
  private Field field;
  private FoodController foodController;
  private Food food;

  private void run(GraphicsContext gc) {
    field.drawField(gc);
    foodController.drawFood(food, gc);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    field = new Field(2, 2, 200);
    foodController = new FoodController(field);

    Group root = new Group();
    Canvas canvas = new Canvas(field.getWidth(), field.getHeight());
    root.getChildren().add(canvas);
    Scene scene = new Scene(root);

    primaryStage.setTitle("Snake");
    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
    primaryStage.show();

    GraphicsContext gc = canvas.getGraphicsContext2D();

    food = foodController.generateFood(new ArrayList<>());
    run(gc);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
