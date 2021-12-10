package rullosolver;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RecursiveSolveTest {
    @Test
    public void checkSolveTest() {
        int[][] solution = {
            {0, 5, 3, 0, 0},
            {0, 0, 5, 7, 0},
            {2, 6, 0, 0, 2},
            {1, 5, 1, 8, 0},
            {0, 6, 9, 6, 2}
        };

        RecursiveSolve rs = new RecursiveSolve();
        assertTrue("checkSolve method failure.", rs.checkSolve(solution));
        
    }
}