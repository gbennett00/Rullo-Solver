package rullosolver;

import static rullosolver.Algorithms.gridSize;

import static java.lang.System.out;

/**
 * Driver program that solves Rullo puzzles. Contains a main method and helper methods that carry 
 * out the cycling of the program and output the solution. 
 * 
 * @author Garrett Bennett
 * @version 1.0.1
 */
public class RulloSolver{
    public static Algorithms algorithms = new Algorithms();
    protected static boolean systemOut = false;
    
    /**
     * Reads the image containing the Rullo puzzle then solves the puzzle.
     * 
     * @param args Command line argument - not used
     */
    public static void main(String args[ ]){
        if(ImageReader.baseImage == null){
            ImageReader.readImage("Rullo.png");   
        }
        Algorithms.setPuzzleValues();
        Algorithms.setContributorTrue();
        solvePuzzle();
    }

    /**
     * Prints the indices of each value and whether it should be "on" 
     * or "off" for the puzzle to be solved.
     */
    private static void outputValues() {
        for(int c = 0; c < gridSize; c++){
            for(int r = 0; r < gridSize; r++){
                out.println("At point " + r + "," + c + ": " + Algorithms.confirmedTrue[r][c]);
            }
            System.out.println(" ");
            out.println();
        }
    }

    /**
     * Cycles through all algorithms until the puzzle is complete 
     * or one complete cycle was made without any changes.
     */
    private static void solvePuzzle(){
        int cycle = 0;
        Algorithms.setContributorTrue();
        algorithms.deleteGreaterValues();
        while(!Algorithms.puzzleFinished){ 
            Algorithms.changeMade = false;
            algorithms.confirmGreaterThanTDiff();
            algorithms.checkForSolve();
            if(!Algorithms.changeMade && !Algorithms.puzzleFinished){
                Algorithms.puzzleFinished = true;
                System.out.println("Forgive me father, for I have failed");
                outputValues();
                return;
            }
            cycle++;
            if(systemOut)System.out.println("Cycle " + cycle + " finished");
        }
        outputValues(); 
    }
}

