package rullosolver;

/**
 * Object representing a Rullo Puzzle.
 * 
 * @author Garrett Bennett
 * @version 1.0.2
 */
public abstract class RulloPuzzle {
    // all fields are base cases; can be overwritten in constructors of child classes
    protected int[][] value = {
        {7, 5, 1, 4, 4, 7},
        {7, 8, 2, 9, 2, 3},
        {3, 4, 8, 5, 9, 8},
        {2, 5, 2, 4, 2, 8},
        {6, 1, 5, 2, 5, 8},
        {3, 9, 9, 7, 6, 9}
    };

    protected int gridSize = 6;

    protected int[] columns = {8, 19, 25, 13, 20, 35}; 
    
    protected int[] rows = {17, 13, 20, 21, 21, 28}; 

    /** Child classes must contain an outputValues() method. */
    protected abstract void outputValues();
}
