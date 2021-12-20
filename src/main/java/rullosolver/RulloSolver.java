package rullosolver;

import java.util.Scanner;

/**
 * Driver program that solves Rullo puzzles. Contains a main method and helper methods that carry 
 * out the cycling of the program and output the solution. 
 * 
 * @author Garrett Bennett
 * @version 1.0.3
 */
public class RulloSolver{
    private static Scanner s;
    
    /**
     * Reads the image containing the Rullo puzzle then solves the puzzle.
     * 
     * @param args Command line argument - not used
     */
    public static void main(String args[]){
        // prepares and reads an image then prepares data to be used
        System.out.print("Input gridsize: ");
        s = new Scanner(System.in);
        int gridSize = Integer.parseInt(s.nextLine());
        ImageReader ir = new ImageReader("Rullo.png", gridSize);
       
        // instantiate RecursiveSolve 
            // pass values, rows, and columns into constructor
            // call solvePuzzle() and outputValues() to print solution
        RecursiveSolve rs = new RecursiveSolve(gridSize, ir.getValuesObj(), ir.getColumnObj(), ir.getRowObj());
        rs.solvePuzzle();
        System.out.println("Solution: ");
        rs.outputValues();
        s.close();
    }

    protected static Scanner getScanner() {
        return s;
    }
}

