package rullosolver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import static rullosolver.Algorithms.gridSize;
import static rullosolver.RulloSolver.systemOut;

/**
 * Reads an image containing a Rullo puzzle by implementing the Tesseract OCR. 
 * Then creates an array representation of the puzzle which can be pulled using getter methods.
 * 
 * @author Garrett Bennett
 * @verison 1.0.1
 */
public class ImageReader {
    protected static int[][] value = new int[gridSize][gridSize];

    protected static int[] columns = new int[gridSize];
    protected static int[] rows = new int[gridSize];
    
    protected static String baseImage;
    private static String[] imageOutput;
        
    /** Reads an image contianing a Rullo puzzle.
     * 
     * @param fileName File name of the image with the Rullo puzzle
     */
    protected static void readImage(String fileName){ 
        File imageFile = new File("C:/Users/benne/Downloads/" + fileName);
        ITesseract tesseract = new Tesseract();
        List<String> configs = new ArrayList<String>();
        configs.add("digits");
        tesseract.setConfigs(configs);
        tesseract.setDatapath("C:/Users/benne/Downloads/tesseract-5.0.0/tesseract-5.0.0/tessdata");
        try {
            String result = tesseract.doOCR(imageFile);
            out.println("systemOut: " + systemOut);
            baseImage = result;
            System.out.println(result);
        }   catch(TesseractException e){
            System.err.println(e.getMessage());
        }
        confirmImageOutput();
    }

    protected static void prepareImage(String fileName) {
        
    }

    /** Gives the user an opportunity to correct any mistakes the OCR made in reading the puzzle values. */
    private static void confirmImageOutput() {
        Scanner s=new Scanner(System.in);
        out.println(baseImage);
        out.print("OCR output correct? 'y' or 'n'");
        String initInput = s.next().strip();
        s.close();
        if(initInput.equals("y")){
            imageOutput = baseImage.split("[^0-9]");
        }else{
            out.println("Please copy input and set as imageOutput with needed correction, compile, then run again");
            System.exit(0);
        }
        
        
    }
    /*
       0  1  2  3  4
    5  6  7  8  9  10 
    11 12 13 14 15 16 
    17 18 19 20 21 22 
    23 24 25 26 27 28 
    29 30 31 32 33 34

       0  1  2  3  4  5
    6  7  8  9  10 11 12 
    13 14 15 16 17 18 19 
    20 21 22 23 24 25 26 
    27 28 29 30 31 32 33 
    34 35 36 37 38 39 40 
    41 42 43 44 45 46 47
    */
    /** Returns a double int array containing the main values of a puzzle.  */
    protected static int[][] getMainValues() {
        try {
            
            for(int r = 0; r < gridSize; r++){
                int firstIndex = (gridSize +1) * (r +1);
                for(int c = 0; c < gridSize; c++){
                    value[r][c] = Integer.parseInt(imageOutput[firstIndex + c]);
                }

            }
            return value;
        }  catch(Exception e){
            e.printStackTrace();
            out.println("Tesseract gave invalid values. Screw you.");
            System.exit(1);
            return value;
        }
        
    }

    /** Returns all target column values. */
    protected static int[] getColumnValues(){
        try {
            for(int i = 0; i < gridSize; i++){
                columns[i] =  Integer.parseInt(imageOutput[i]);
            }
            return columns;
        }   
        catch(Exception e){
            out.println("Invalid column input");
            System.exit(1);
            return columns;
        }
    }

    /** Returns all target row values. */
    protected static int[] getRowValues(){
        try{
            for(int i = 0; i < gridSize; i++){
                int index = i == 0 ? gridSize : (i + 1) * (gridSize + 1) - 1; 
                rows[i] = Integer.parseInt(imageOutput[index]);
            }
            return rows;
        }   
        catch(Exception e){
            out.println("Invalid column input");
            System.exit(1);
            return rows;
        }
    } 

}