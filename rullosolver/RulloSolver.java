package rullosolver;

public class RulloSolver{
    public static PuzzleValues puzzleValues = new PuzzleValues();
    //public static Algorithms algorithms = new Algorithms();
    public static void main(String args[ ]){
        puzzleValues.deleteGreaterValues();
        while(!PuzzleValues.puzzleFinished){ 
            PuzzleValues.changeMade = false;
            puzzleValues.confirmGreaterThanTDiff();
            puzzleValues.deleteGTLowerDiff();
            puzzleValues.checkForSolve();
            if(!PuzzleValues.changeMade){
                PuzzleValues.puzzleFinished = true;
                System.out.println("Forgive me father, for I have failed");
            }
        }
        outputValues();
       
        
        
    }

    private static void outputValues(){
        for(int c = 0; c < PuzzleValues.gridSize; c++){
            for(int r = 0; r < PuzzleValues.gridSize; r++){
                System.out.println("At point " + r + "," + c + ": " + PuzzleValues.confirmedTrue[r][c]);
            }
        }
    }
}

