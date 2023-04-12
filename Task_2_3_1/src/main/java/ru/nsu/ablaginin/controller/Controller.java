package ru.nsu.ablaginin.controller;


/**
 * Interface must implement a controller that loads a level
 * or loads some menu. It must be stoppable.
 */
public interface Controller {
  void load();
  void stop();
}
