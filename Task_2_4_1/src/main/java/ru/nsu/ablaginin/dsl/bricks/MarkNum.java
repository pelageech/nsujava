package ru.nsu.ablaginin.dsl.bricks;

public enum MarkNum {
    EXCELLENT,
    GOOD,
    SATISFYING,
    BAD;

    public static MarkNum fromInt(int i) {
        return switch (i) {
            case 2 -> BAD;
            case 3 -> SATISFYING;
            case 4 -> GOOD;
            case 5 -> EXCELLENT;
            default -> throw new IllegalStateException("Unexpected value: " + i);
        };
    }
}
