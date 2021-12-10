package rullosolver;

public abstract class RulloPuzzle {
    // integer values in each location
    protected final int[][] value = {
        {7, 5, 1, 4, 4, 7},
        {7, 8, 2, 9, 2, 3},
        {3, 4, 8, 5, 9, 8},
        {2, 5, 2, 4, 2, 8},
        {6, 1, 5, 2, 5, 8},
        {3, 9, 9, 7, 6, 9}
    };

    // eventually will be adjusted by user input
    protected static final int gridSize = 6;

    protected int[] columns = {8, 19, 25, 13, 20, 35}; // = ImageReader.getColumnValues(); //
    
    protected int[] rows = {17, 13, 20, 21, 21, 28}; // = ImageReader.getRowValues(); //
    
    public RulloPuzzle() {
        //value = ImageReader.getMainValues();
        //columns = ImageReader.getColumnValues();
        //rows = ImageReader.getRowValues();
        //setContributorTrue();
    }

    protected abstract void outputValues();
}
