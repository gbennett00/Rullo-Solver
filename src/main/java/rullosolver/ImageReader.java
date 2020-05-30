package rullosolver;

import java.io.File;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * Hello world!
 *
 */
public class ImageReader {
    private static int gridSize = PuzzleValues.gridSize;
    
    protected static int[][] value = new int[gridSize][gridSize];

    protected static int[] columns = new int[gridSize];
    protected static int[] rows = new int[gridSize];
    
    protected static String baseImage = readImage("Rullo.png");
    private static String[] imageOutput = readImage("Rullo.png").split("[^0-9]");
    
    protected static String readImage(String fileName){
        File imageFile = new File("C:/Users/benne/Desktop/" + fileName);
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:/Users/benne/Desktop/tesseract/tessdata");
        try {
            String result = tesseract.doOCR(imageFile);
            System.out.println(result);
            return result;
        }   catch(TesseractException e){
            System.err.println(e.getMessage());
            return "";
        }

    }

    protected static int[][] getMainValues(){
        for(int i = gridSize + 1; i <= gridSize * (gridSize + 1); i += (gridSize + 1)){
            int row = i / (gridSize + 1) - 1;
            for(int h = 0; h < 5; h++){
                value[row][h] = Integer.parseInt(imageOutput[i + h]);

            }
        }
        return value;
    }

    protected static int[] getColumnValues(){
        for(int i = 0; i < gridSize; i++){
            columns[i] =  Integer.parseInt(imageOutput[i]);
        }
        return columns;
    }

    protected static int[] getRowValues(){
        for(int i = 0; i < gridSize; i++){
            int index = i == 0 ? gridSize : (i + 1) * (gridSize + 1) - 1; 
            rows[i] = Integer.parseInt(imageOutput[index]);
        }
        return rows;
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


}