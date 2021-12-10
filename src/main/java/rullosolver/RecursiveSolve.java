package rullosolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  Contains methods to solve a Rullo puzzle recursively.
 * 
 * @author Garrett Bennett
 * @version 1.0.2
 */
public class RecursiveSolve extends RulloPuzzle {
    
    /**
     * Returns an ArrayList<int[]> of all posible combinations whose sum equals the target t. 
     * @param i Starting index
     * @param t Target sum
     * @param sol Solution array
     * @param vals Unchanged array of input values
     * @param solutions Target for solution arrays
     */
    protected static ArrayList<int[]> solveSection(int i, int t, int sum, int[] sol, int[] vals, ArrayList<int[]> solutions) {
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

    protected ArrayList<int[]> solve() {
        ArrayList<ArrayList<int[]>> possibleSolutions = new ArrayList<ArrayList<int[]>>();
        for (int i = 0; i < rows.length; i++) {
            ArrayList<int[]> section = solveSection(0, rows[i], 0, new int[rows.length], value[i], new ArrayList<int[]>());
            possibleSolutions.add(section);
        }
        return possibleSolutions.get(0);
    }

    private boolean checkSolve(int[][] potentialSolution) {
        for (int r = 0; r < rows.length; r++) {
            int sum = 0;
            for (int c = 0; c < rows.length; c++) {
                sum += potentialSolution[r][c];
            }
            if (sum != columns[r]) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        RecursiveSolve rs = new RecursiveSolve();
        rs.solve();
    }


    private static void print_list(ArrayList<int[]> sol) {
        for (int[] ints : sol) {
            for (int i : ints) {
                System.out.print(i + " ");
            }System.out.println();
        }
    }


}