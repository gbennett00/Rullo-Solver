package rullosolver;

/**
 * Driver program that solves Rullo puzzles. Contains a main method and helper methods that carry 
 * out the cycling of the program and output the solution. 
 * 
 * @author Garrett Bennett
 * @version 1.0.1
 */
public class RulloSolver{
    protected static boolean systemOut = false;
    
    /**
     * Reads the image containing the Rullo puzzle then solves the puzzle.
     * 
     * @param args Command line argument - not used
     */
    public static void main(String args[ ]){
        if(ImageReader.baseImage == null){
            ImageReader.readImage("RulloOutput.png");   
        }
        /*Algorithms a = new Algorithms();
        a.solvePuzzle();*/

    }

    

    
}

