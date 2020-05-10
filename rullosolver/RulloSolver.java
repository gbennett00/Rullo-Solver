package rullosolver;

public class RulloSolver{
    public static PuzzleValues puzzleValues = new PuzzleValues();
    public static Algorithms algorithms = new Algorithms();
    public static void main(String args[ ]){
        System.out.println(PuzzleValues.contributor[0][1]);
        System.out.println(PuzzleValues.contributor[1][1]);
        
        algorithms.deleteGreaterValues();
        //puzzleValues.deactivateNumber(2, 1);
        System.out.println(PuzzleValues.contributor[0][1]);
        System.out.println(PuzzleValues.contributor[1][1]);


    }
}