package rullosolver;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *  Contains values of a Rullo puzzle and methods to solve the puzzle recursively.
 * 
 * @author Garrett Bennett
 * @version 1.0.2
 */
public class RecursiveSolve extends RulloPuzzle {
    private int[][] solvedPuzzle;

    /** Creates RecursiveSolve object with default values. */
    protected RecursiveSolve() {}

    /** 
     * Creates RecursiveSolve object with user-defined values.
     * 
     * @param gridSiezIn Grid size
     * @param mainValues Main values
     * @param columnsIn Column values
     * @param rowsIn Row values
     */
    protected RecursiveSolve(int gridSiezIn, int[][] mainValues, int[] columnsIn, int[] rowsIn) {
        value = mainValues;
        columns = columnsIn;
        rows = rowsIn;
        gridSize = gridSiezIn;
    }

    /** Returns the solved puzzle. */
    protected int[][] solvePuzzle() {
        try{
            solvedPuzzle = solve(0, new int[gridSize][gridSize], new ArrayList<int[][]>(), getPossibleSolutions()).get(0);
        } catch (Exception e) {
            System.out.println("No solution found.");
        }
        return solvedPuzzle;
    }

    /**
     * Returns an ArrayList<int[]> of all posible combinations whose sum equals the target t. 
     * @param i Starting index
     * @param t Target sum
     * @param sol Solution array
     * @param vals Unchanged array of input values
     * @param solutions Target for solution arrays
     * @return ArrayList<int[]> representing all possible combinations of the given section
     */
    private ArrayList<int[]> solveSection(int i, int t, int sum, int[] sol, int[] vals, ArrayList<int[]> solutions) {
        if (sum == t) {
            solutions.add(Arrays.copyOf(sol, vals.length));
            sol[i-1] = 0;
            return solutions;
        }

        if (i == vals.length || sum > t) {
            sol[i-1] = 0;
            return solutions;
        }

        for(int j = i; j < vals.length; j++) {
            sol[j] = vals[j];
            solveSection(j+1, t, sum+sol[j], sol, vals, solutions);
        }

        if (i != 0) {
            sol[i-1] = 0; 
        }
        return solutions;
    }

    /**
     * Recursively solves the puzzle using all possible combinations of each row. 
     * @param solIndex
     * @param solution
     * @param finalSolution
     * @param possibilities
     * @return ArrayList<int[][]> representing the solution 
     */
    private ArrayList<int[][]> solve(int solIndex, int[][] solution, ArrayList<int[][]> finalSolution, ArrayList<ArrayList<int[]>> possibilities) {
        if (solIndex == gridSize) {
            if (checkSolve(solution)) {
                int[][] solutionCopy = new int[gridSize][gridSize];
                for (int i = 0; i < gridSize; i++) {
                    solutionCopy[i] = Arrays.copyOf(solution[i], gridSize);
                }
                finalSolution.add(solutionCopy);
            }
            return finalSolution;
        }

        for (int k = 0; k < possibilities.get(solIndex).size(); k++) {
            
            solution[solIndex] = possibilities.get(solIndex).get(k);
            solve(solIndex+1, solution, finalSolution, possibilities);
        }
        return finalSolution;
    }

    /** Returns an object representing all possible solutions for each row.  */
    private ArrayList<ArrayList<int[]>> getPossibleSolutions() {
        ArrayList<ArrayList<int[]>> possibleSolutions = new ArrayList<ArrayList<int[]>>();
        for (int i = 0; i < rows.length; i++) {
            ArrayList<int[]> section = solveSection(0, rows[i], 0, new int[rows.length], value[i], new ArrayList<int[]>());
            possibleSolutions.add(section);
        }
        return possibleSolutions;
    }

    /** Returns true if the current values are a valid solution. */
    private boolean checkSolve(int[][] potentialSolution) {
        for (int c = 0; c < rows.length; c++) {
            int sum = 0;
            for (int r = 0; r < rows.length; r++) {
                sum += potentialSolution[r][c];
            }
            if (sum != columns[c]) {
                return false;
            }
        }
        return true;
    }

    /** Outputs the finished puzzle. */
    protected void outputValues() {
        for(int c = 0; c < gridSize; c++){
            for(int r = 0; r < gridSize; r++){
                System.out.print(solvedPuzzle[c][r] + " ");
            }
            System.out.println();
        }
    }
}