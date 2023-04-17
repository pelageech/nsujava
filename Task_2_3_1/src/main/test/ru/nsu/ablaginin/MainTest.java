package ru.nsu.ablaginin;

import org.junit.jupiter.api.Test;

public class MainTest {
    @Test
    public void mainTest() {
        Main app = new Main();
        Main.launch();
        app.stop();
    }
}
