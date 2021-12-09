package rullosolver;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *  Contains methods to solve a Rullo puzzle recursively.
 * 
 * @author Garrett Bennett
 * @version 1.0.1
 */
public class RecursiveSolve {
    /**
     * Returns an ArrayList<int[]> of all posible combinations whose sum equals the target t. 
     * @param i Starting index
     * @param t Target sum
     * @param sol Solution array
     * @param vals Unchanged array of input values
     * @param solutions Target for solution arrays
     */
    protected static void solve(int i, int t, int[] sol, int[] vals, ArrayList<int[]> solutions) {
        int sum = sumArray(sol);
        if (sum == t) {
            solutions.add(Arrays.copyOf(sol, vals.length));
            return;
        }

        if (i == vals.length || sum > t) {
            sol[i-1] = 0;
            return;
        }

        for(int j = i; j < vals.length; j++) {
            sol[j] = vals[j];
            solve(j+1, t, sol, vals, solutions);
        }
        if (i != 0) {
            sol[i-1] = 0; 
        }
    }

    public static void main(String[] args) {
        int[] values = {5, 6, 5, 7, 5, 8, 6};
        ArrayList<int[]> solutions = new ArrayList<int[]>();
        try {
            solve(0, 29, new int[values.length], values, solutions);
        } catch(Exception e) {
            System.out.println(e.getCause());
        }
        print_list(solutions);
    }

    private static int sumArray(int[] vals) {
        int sum = 0;
        for (int i : vals) {
            sum += i;
        }
        return sum;
    }

    private static void print_list(ArrayList<int[]> sol) {
        for (int[] ints : sol) {
            for (int i : ints) {
                System.out.print(i + " ");
            }System.out.println();
        }
    }


}