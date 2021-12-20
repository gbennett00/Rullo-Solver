package rullosolver;

/**
 * Object representing a Rullo Puzzle.
 * 
 * @author Garrett Bennett
 * @version 1.0.2
 */
public abstract class RulloPuzzle {
    // all fields are base cases and can be overwritten in constructors of child classes
    protected int[][] value = {
        {3, 2, 4, 2, 1, 5, 5, 7},
        {2, 6, 7, 1, 9, 1, 7, 4},
        {9, 5, 7, 3, 3, 4, 8, 8},
        {3, 4, 6, 7, 6, 4, 5, 8},
        {1, 7, 9, 8, 9, 2, 4, 2},
        {2, 4, 5, 9, 4, 2, 4, 7},
        {5, 8, 1, 1, 8, 9, 4, 4},
        {9, 8, 9, 2, 2, 4, 8, 6}
    };

    protected int gridSize = 8;

    protected int[] columns = {32, 36, 26, 26, 30, 23, 33, 33}; 
    
    protected int[] rows = {25, 28, 25, 30, 36, 23, 28, 44}; 

    /** Child classes must contain an outputValues() method. */
    protected abstract void outputValues();
}
