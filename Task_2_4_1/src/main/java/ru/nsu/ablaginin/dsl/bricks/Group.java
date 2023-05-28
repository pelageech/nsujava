package ru.nsu.ablaginin.dsl.bricks;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Students study in a group.
 */
@AllArgsConstructor
@Data
public class Group {
    int number;


    /**
     * wtf??? Java requires this whereas there is a Data annotation.
     * Moreover, IT STRANGELY WORKS WITH LIST CLASSES! THEY DON'T HAVE AN EMPTY CONSTRUCTOR!
     */
    public Group() {

    }
}
