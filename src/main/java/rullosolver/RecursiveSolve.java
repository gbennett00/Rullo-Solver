package rullosolver;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *  Contains methods to solve a Rullo puzzle recursively.
 * 
 * @author Garrett Bennett
 * @version 1.0.2
 */
public class RecursiveSolve extends RulloPuzzle {
    private int[][] solvedPuzzle;

    protected RecursiveSolve() {
        
    }

    protected RecursiveSolve(int gridSiezIn, int[][] mainValues, int[] columnsIn, int[] rowsIn) {
        value = mainValues;
        columns = columnsIn;
        rows = rowsIn;
        gridSize = gridSiezIn;
    }

    protected int[][] solvePuzzle() {
        solvedPuzzle = solve(0, new int[gridSize][gridSize], new ArrayList<int[][]>(), getPossibleSolutions()).get(0);
        return solvedPuzzle;
    }

    /**
     * Returns an ArrayList<int[]> of all posible combinations whose sum equals the target t. 
     * @param i Starting index
     * @param t Target sum
     * @param sol Solution array
     * @param vals Unchanged array of input values
     * @param solutions Target for solution arrays
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

    private ArrayList<ArrayList<int[]>> getPossibleSolutions() {
        ArrayList<ArrayList<int[]>> possibleSolutions = new ArrayList<ArrayList<int[]>>();
        for (int i = 0; i < rows.length; i++) {
            ArrayList<int[]> section = solveSection(0, rows[i], 0, new int[rows.length], value[i], new ArrayList<int[]>());
            possibleSolutions.add(section);
        }
        return possibleSolutions;
    }

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

    protected void outputValues() {
        for(int c = 0; c < gridSize; c++){
            for(int r = 0; r < gridSize; r++){
                System.out.print(solvedPuzzle[c][r] + " ");
            }
            System.out.println();
        }
    }
}