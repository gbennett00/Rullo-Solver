package rullosolver;

public class RulloSolver{
    public static PuzzleValues puzzleValues = new PuzzleValues();
    //public static Algorithms algorithms = new Algorithms();
    public static void main(String args[ ]){
        puzzleValues.deleteGreaterValues();
        puzzleValues.confirmGreaterThanTDiff();
        puzzleValues.deleteGTLowerDiff();
    }
}