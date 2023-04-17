package ru.nsu.ablaginin.controller.menu;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import ru.nsu.ablaginin.controller.Controller;
import ru.nsu.ablaginin.model.menu.Menu;
import ru.nsu.ablaginin.view.menu.DrawMenu;

public class MenuController implements Controller {
  private final GraphicsContext gc;
  private final DrawMenu drawMenu;
  private final MediaPlayer player;

  public MenuController(GraphicsContext gc, Menu menu) {
    this.gc = gc;
    drawMenu = new DrawMenu(menu, Color.web("AAD751"), Color.web("82A149"));

    var url = getClass().getClassLoader().getResource("music/menu.mp3");
    if (url == null) {
      throw new IllegalArgumentException("menu musicPath not found");
    }

    player = new MediaPlayer(new Media(url.toExternalForm()));
  }

  @Override
  public void load() {
    player.play();
    drawMenu.draw(gc);
  }

  @Override
  public void stop() {
    player.stop();
  }
}
