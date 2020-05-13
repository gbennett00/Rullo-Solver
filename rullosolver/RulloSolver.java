package rullosolver;

public class RulloSolver{
    public static PuzzleValues puzzleValues = new PuzzleValues();
    //public static Algorithms algorithms = new Algorithms();
    public static void main(String args[ ]){
        puzzleValues.deleteGreaterValues();
        puzzleValues.confirmGreaterThanTDiff();
        //System.out.println(puzzleValues.isFinished(4, false));
        /*for(int i = 0; i < 5; i++){
            for(int h = 0; h < 5; h++){
                if(PuzzleValues.confirmedTrue[i][h]){
                    System.out.println("Confirmed coordinates: " + i + "," + h + " value: " + PuzzleValues.value[i][h]);
                }
            }
        }*/
        
        

    }
}