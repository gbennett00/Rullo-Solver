package rullosolver;

public class RulloSolver{
    public static PuzzleValues puzzleValues = new PuzzleValues();
    public static void main(String args[ ]){
        int cycle = 0;
        PuzzleValues.setContributorTrue();
        puzzleValues.deleteGreaterValues();
        while(!PuzzleValues.puzzleFinished){ 
            PuzzleValues.changeMade = false;
            puzzleValues.confirmGreaterThanTDiff();
            puzzleValues.checkForSolve();
            if(!PuzzleValues.changeMade && !PuzzleValues.puzzleFinished){
                PuzzleValues.puzzleFinished = true;
                System.out.println("Forgive me father, for I have failed");
                outputValues();
                return;
            }
            cycle += 1;
            if(PuzzleValues.systemOut)System.out.println("Cycle " + cycle + " finished");
        }
        outputValues();    
    }

    private static void outputValues(){
        for(int c = 0; c < PuzzleValues.gridSize; c++){
            for(int r = 0; r < PuzzleValues.gridSize; r++){
                System.out.println("At point " + r + "," + c + ": " + PuzzleValues.confirmedTrue[r][c]);
            }
            System.out.println(" ");
        }
    }
}

