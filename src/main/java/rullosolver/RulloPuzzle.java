package rullosolver;

public abstract class RulloPuzzle {
    // integer values in each location
    //protected int[][] value; // = ImageReader.getMainValues();
    protected final int[][] value = {
        {1, 5, 3, 4, 7},
        {4, 1, 5, 7, 1},
        {2, 6, 5, 6, 2},
        {1, 5, 1, 8, 8},
        {1, 6, 9, 6, 2}
    };

    // eventually will be adjusted by user input
    protected static final int gridSize = 6;

    protected int[] columns = {3, 22, 18, 21, 4}; // = ImageReader.getColumnValues(); //
    protected boolean[] columnFinished = new boolean[gridSize];
    protected int[] rows = {8, 12, 10, 15, 23}; // = ImageReader.getRowValues(); //
    protected boolean[] rowFinished = new boolean[gridSize];
    protected boolean puzzleFinished = false;
    protected boolean changeMade = false;
    
    protected boolean[][] contributor = new boolean[gridSize][gridSize];

    protected boolean[][] confirmedTrue = new boolean[gridSize][gridSize];

    public RulloPuzzle() {
        //value = ImageReader.getMainValues();
        //columns = ImageReader.getColumnValues();
        //rows = ImageReader.getRowValues();
        setContributorTrue();
    }

    /**
     * Prints the indices of each value and whether it should be "on" 
     * or "off" for the puzzle to be solved.
     */
    protected void outputValues(boolean[][] solution) {
        for(int c = 0; c < gridSize; c++){
            for(int r = 0; r < gridSize; r++){
                System.out.println("At point " + r + "," + c + ": " + solution[r][c]);
            }
            System.out.println(" ");
            System.out.println();
        }
    }

    /** Sets all contributors to true. */
    protected void setContributorTrue(){
        for(int x = 0; x < gridSize; x++){
            for(int y = 0; y < gridSize; y++){
                contributor[x][y] = true;
            }
        }
    }
}
