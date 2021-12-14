package rullosolver;

import java.util.Scanner;

/**
 * Driver program that solves Rullo puzzles. Contains a main method and helper methods that carry 
 * out the cycling of the program and output the solution. 
 * 
 * @author Garrett Bennett
 * @version 1.0.2
 */
public class RulloSolver{
    /**
     * Reads the image containing the Rullo puzzle then solves the puzzle.
     * 
     * @param args Command line argument - not used
     */
    public static void main(String args[]){
        // prepares and reads an image then prepares data to be used
        System.out.println("Input gridsize: ");
        Scanner s = new Scanner(System.in);
        int gridSize = s.nextInt();
        ImageReader ir = new ImageReader("Rullo.png", gridSize);
        
        
        // instantiate RecursiveSolve 
            // pass values, rows, and columns into constructor
            // call solvePuzzle() and outputValues() to print solution
        RecursiveSolve rs = new RecursiveSolve(gridSize, ir.getMainValues(), ir.getColumnValues(), ir.getRowValues());
        rs.solvePuzzle();
        System.out.println("Solution: ");
        rs.outputValues();
    }
}

