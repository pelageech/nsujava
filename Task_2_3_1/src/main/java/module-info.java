module ru.nsu.ablaginin {
  requires javafx.controls;
  requires java.desktop;

  requires lombok;
  requires javafx.media;
  requires com.google.gson;
  requires org.junit.jupiter;
  opens ru.nsu.ablaginin.model.ingame.builder to com.google.gson;
  exports ru.nsu.ablaginin;
}