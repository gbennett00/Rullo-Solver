package rullosolver;

public class RulloSolver{
    public static PuzzleValues puzzleValues = new PuzzleValues();
    //public static Algorithms algorithms = new Algorithms();
    public static void main(String args[ ]){
        puzzleValues.deleteGreaterValues();
        for(int x = 0; x < 5; x++){ 
            puzzleValues.confirmGreaterThanTDiff();
            puzzleValues.deleteGTLowerDiff();
        }
        outputValues();
        
    }

    private static void outputValues(){
        for(int c = 0; c < 5; c++){
            for(int r = 0; r < 5; r++){
                System.out.println("At point " + r + "," + c + ": " + PuzzleValues.confirmedTrue[r][c]);
            }
        }
    }
}

