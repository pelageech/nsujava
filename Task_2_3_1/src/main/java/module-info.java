module ru.nsu.ablaginin {
  requires javafx.controls;
  requires java.desktop;

  requires lombok;
  requires javafx.media;
  requires com.google.gson;
  exports ru.nsu.ablaginin;
  opens ru.nsu.ablaginin.builder to com.google.gson;
}