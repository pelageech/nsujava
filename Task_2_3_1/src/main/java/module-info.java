module ru.nsu.ablaginin {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.web;

  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
//  requires validatorfx;
  requires org.kordamp.ikonli.javafx;
  requires org.kordamp.bootstrapfx.core;
//  requires eu.hansolo.tilesfx;
  requires com.almasb.fxgl.all;
  requires java.desktop;
  requires lombok;

  opens ru.nsu.ablaginin to javafx.fxml;
  exports ru.nsu.ablaginin;
}