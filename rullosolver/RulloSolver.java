package rullosolver;

import java.util.*;
import java.io.*;

public class RulloSolver{
    public static PuzzleValues puzzleValues = new PuzzleValues();
    public static void main(String args[ ]){
        System.out.println(PuzzleValues.columns[0]);
        System.out.println(puzzleValues.columnCurrentSum(0));

        System.out.println(puzzleValues.columnIsFinished(0));
    }
}