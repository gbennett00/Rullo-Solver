package rullosolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * Reads an image containing a Rullo puzzle by implementing the Tesseract OCR. 
 * Then creates an array representation of the puzzle which can be pulled using getter methods.
 * 
 * @author Garrett Bennett
 * @verison 1.0.1
 */
public class ImageReader {
    private String[] imageOutput;
    private int gridSize;
        
    /**
     * Reads an image using the Tesseract OCR and prepares data to be used.
     * 
     * @param filename Name of file containing the Rullo image
     */
    protected ImageReader(String filename) {
        readImage(filename);
        gridSize = (int) (Math.sqrt(imageOutput.length + 1) - 1);
    }

    /** 
     * Reads an image contianing a Rullo puzzle. Calls confirmImageOutput in succession.
     * 
     * @param fileName File name of the image with the Rullo puzzle
     */
    protected void readImage(String fileName){ 
        ITesseract tesseract = new Tesseract();
        List<String> configs = new ArrayList<String>();
        configs.add("digits");
        tesseract.setConfigs(configs);
        tesseract.setDatapath("C:/Users/benne/Downloads/tesseract-5.0.0/tesseract-5.0.0/tessdata");
        try {
            PreparedImage prep = new PreparedImage("C:/Users/benne/Downloads/" + fileName);
            String baseImage = tesseract.doOCR(prep.getPreparedImage());
            String[] imageRows = baseImage.split("\n");
            confirmImageOutput(imageRows);
        }   catch(TesseractException e){
            System.err.println(e.getMessage());
        }
    }

    /** 
     * Gives the user an opportunity to correct any mistakes the OCR made in reading the puzzle values.
     * 
     * @param initValues Array containing String objects for each row
    */
    private void confirmImageOutput(String[] initValues) {
        Scanner s=new Scanner(System.in);
        outputValues(initValues);
        System.out.print("OCR output correct? 'y' or 'n' ");
        String initInput = s.next().strip();
        while(!initInput.equals("y")) {
            System.out.print("Enter row number: ");
            int row = s.nextInt();
            s.nextLine();
            System.out.print("Enter new row values: ");
            Scanner s1 = new Scanner(s.nextLine());
            String nextLine = "";
            while(s1.hasNext()) {
                nextLine += s1.next() + " ";
            }
            initValues[row] = nextLine.strip();
            outputValues(initValues);
            System.out.print("Is output correct now? 'y' or 'n' ");
            initInput = s.next().strip();
        }
        initInput = "";
        for (int r = 0; r < initValues.length; r++) {
            initInput += initValues[r] + " ";
        }
        imageOutput = initInput.split("[^0-9]");
        s.close();
    }
    
    /** Returns a double int array containing the main values of a puzzle.  */
    protected int[][] getMainValues() {
        try {
            int[][] value = new int[gridSize][gridSize];
            for(int r = 0; r < gridSize; r++){
                int firstIndex = (gridSize +1) * (r +1);
                for(int c = 0; c < gridSize; c++){
                    value[r][c] = Integer.parseInt(imageOutput[firstIndex + c]);
                }

            }
            return value;
        }  catch(Exception e){
            e.printStackTrace();
            System.out.println("Tesseract gave invalid values.");
            System.exit(1);
            return null;
        }
        
    }

    /** Returns all target column values. */
    protected int[] getColumnValues(){
        try {
            int[] columns = new int[gridSize];
            for(int i = 0; i < gridSize; i++){
                columns[i] =  Integer.parseInt(imageOutput[i]);
            }
            return columns;
        }   
        catch(Exception e){
            System.out.println("Invalid column input");
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    /** Returns all target row values. */
    protected int[] getRowValues(){
        try{
            int[] rows = new int[gridSize];
            for(int i = 0; i < gridSize; i++){
                int index = i == 0 ? gridSize : (i + 1) * (gridSize + 1) - 1; 
                rows[i] = Integer.parseInt(imageOutput[index]);
            }
            return rows;
        }   
        catch(Exception e){
            System.out.println("Invalid column input");
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    } 

    /** Returns gridSize for this image. */
    protected int getGridSize() {
        return gridSize;
    }

    /** Prints all main values. */
    protected void outputValues(String[][] values) {
        for(int c = 0; c < values.length; c++){
            for(int r = 0; r < values[c].length; r++){
                System.out.print(values[c][r] + " ");
            }
            System.out.println();
        }
    }

    /** Prints target sum values. */
    protected void outputValues(String[] section) {
        for(int i = 0; i < section.length; i++) {
            System.out.println(section[i] + " ");
        }
    }
}