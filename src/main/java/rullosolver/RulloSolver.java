package rullosolver;

import static rullosolver.Algorithms.gridSize;

import static java.lang.System.out;


public class RulloSolver{
    public static Algorithms algorithms = new Algorithms();
    protected static boolean systemOut = false;
    
    public static void main(String args[ ]){
        if(ImageReader.baseImage == null){
            ImageReader.readImage("Rullo.png");   
        }
        Algorithms.setPuzzleValues();
        Algorithms.setContributorTrue();
        solvePuzzle();
    }

    private static void outputValues() {
        for(int c = 0; c < gridSize; c++){
            for(int r = 0; r < gridSize; r++){
                out.println("At point " + r + "," + c + ": " + Algorithms.confirmedTrue[r][c]);
            }
            System.out.println(" ");
            out.println();
        }
    }

    private static void solvePuzzle(){
        int cycle = 0;
        Algorithms.setContributorTrue();
        algorithms.deleteGreaterValues();
        while(!Algorithms.puzzleFinished){ 
            Algorithms.changeMade = false;
            algorithms.confirmGreaterThanTDiff();
            algorithms.checkForSolve();
            if(!Algorithms.changeMade && !Algorithms.puzzleFinished){
                Algorithms.puzzleFinished = true;
                System.out.println("Forgive me father, for I have failed");
                outputValues();
                return;
            }
            cycle++;
            if(systemOut)System.out.println("Cycle " + cycle + " finished");
        }
        outputValues(); 
    }
}

