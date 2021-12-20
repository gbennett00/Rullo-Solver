package rullosolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.awt.image.BufferedImage;

import net.sourceforge.tess4j.Tesseract;

/**
 * Reads an image containing a Rullo puzzle by implementing the Tesseract OCR. 
 * Then creates an array representation of the puzzle which can be pulled using getter methods.
 * 
 * @author Garrett Bennett
 * @verison 1.0.3
 */
public class ImageReader {
    private int[] columns;
    private int[] rows;
    private int[][] values;
    private int gridSize;
    private Tesseract tesseract = new Tesseract();
    private PreparedImage prep;
    private int unitHeight;
    private int unitWidth;
    private Scanner s; 

    /**
     * Reads an image using the Tesseract OCR and prepares data to be used.
     * 
     * @param filename Name of file containing the Rullo image
     */
    protected ImageReader(String filename, int gridSiezIn) {
        // initialize gridSize
        gridSize = gridSiezIn;
        // configure tesseract
        List<String> configs = new ArrayList<String>();
        configs.add("digits");
        tesseract.setConfigs(configs);
        tesseract.setDatapath("C:/Users/benne/Downloads/tesseract-5.0.0/tesseract-5.0.0/tessdata");
        // creates a prepared image that sets the white numbers against a black background
        prep = new PreparedImage("C:/Users/benne/Downloads/" + filename);
        // reads values one by one to improve accuracy
        unitHeight = prep.getPreparedImage().getHeight() / (gridSize + 1);
        unitWidth = prep.getPreparedImage().getWidth() / (gridSize + 1);
        s = RulloSolver.getScanner();
        columns = getColumnValues();
        rows = getRowValues();
        values = getMainValues();
        confirmImageOutput();
    }

    /** 
     * Gives the user an opportunity to correct any mistakes the OCR made in reading the puzzle values.
     * 
     * @param initValues Array containing String objects for each row
    */
    private void confirmImageOutput() {
        System.out.println("Rows: ");
        outputValues(rows);
        System.out.println("Columns: ");
        outputValues(columns);
        System.out.println("Main values: ");
        outputValues(values);
        System.out.print("OCR output correct? 'y' or 'n' ");
        String initInput = s.next().strip();
        s.nextLine();
        while(!initInput.equals("y")) {
            System.out.print("Enter row number (-1 for columns and -2 for rows): ");
            int row = s.nextInt();
            s.nextLine();
            System.out.print("Enter index: ");
            int index = s.nextInt();
            s.nextLine();
            System.out.print("Enter new values: ");
            int val = s.nextInt();
            s.nextLine();
            if (row == -1) {
                columns[index] = val;
            } else if (row == -2) {
                rows[index] = val;
            } else if (row >= 0 && row < gridSize) {
                values[row][index] = val;
            } else {
                System.out.println("Invalid input");
            }
            System.out.println("Rows: ");
            outputValues(rows);
            System.out.println("Columns: ");
            outputValues(columns);
            System.out.println("Main values: ");
            outputValues(values);
            System.out.print("Is output correct now? 'y' or 'n' ");
            initInput = s.next().strip();
        }
        
    }
    
    /** Returns a double int array containing the main values of a puzzle.  */
    protected int[][] getMainValues() {
        //Scanner s = new Scanner(System.in);
        try {
            int[][] value = new int[gridSize][gridSize];
            BufferedImage bi;
            for(int r = 1; r < gridSize + 1; r++){
                for(int c = 1; c < gridSize + 1; c++){
                    bi = prep.getPreparedImage().getSubimage(c * unitWidth, r * unitHeight, unitWidth, unitHeight);
                    try {
                        //String in = tesseract.doOCR(bi);
                        //if (in.strip().equals("?")) value[r-1][c-1] = 6;
                        //else if (in.strip().equals("-")) value[r-1][c-1] = 7;
                        value[r-1][c-1] = Integer.parseInt(tesseract.doOCR(bi).replaceAll("[^0-9]", ""));
                    } catch (Exception e) {
                        System.out.println(tesseract.doOCR(bi));
                        System.out.print("Please input value at point " + r + ", " + c + ": ");
                        int num = s.nextInt();
                        value[r-1][c-1] = num;
                    }
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
            BufferedImage bi;
            for(int i = 1; i < gridSize + 1; i++){
                bi = prep.getPreparedImage().getSubimage(i * unitWidth, 0, unitWidth, unitHeight);
                try {
                    String in = tesseract.doOCR(bi);
                    if (in.strip().equals("0")) throw new Exception();
                    else columns[i-1] = Integer.parseInt(in.replaceAll("[^0-9]", ""));
                } catch (Exception e) {
                    System.out.print("Please input column value " + i + ": ");
                    //Scanner s = new Scanner(System.in);
                    columns[i-1] = s.nextInt();
                }
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
            BufferedImage bi;
            for(int i = 1; i < gridSize + 1; i++){
                bi = prep.getPreparedImage().getSubimage(0, i * unitHeight, unitWidth, unitHeight);
                try {
                    String in = tesseract.doOCR(bi);
                    if (in.strip().equals("0")) throw new Exception();
                    else rows[i-1] = Integer.parseInt(in.replaceAll("[^0-9]", ""));
                } catch (Exception e) {
                    System.out.print("Please input row value " + i + ": ");
                    //Scanner s = new Scanner(System.in);
                    rows[i-1] = s.nextInt();
                }
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

    /** Returns the object representing the column values. */
    protected int[] getColumnObj() {
        return columns;
    }

    /** Returns the object representing the row values. */
    protected int[] getRowObj() {
        return rows;
    }

    /** Returns the object representing the main values. */
    protected int[][] getValuesObj() {
        return values;
    }

    /** Prints all main values. */
    protected void outputValues(int[][] values) {
        for(int c = 0; c < values.length; c++){
            for(int r = 0; r < values[c].length; r++){
                System.out.print(values[c][r] + " ");
            }
            System.out.println();
        }
    }

    /** Prints target sum values. */
    protected void outputValues(int[] section) {
        for(int i = 0; i < section.length; i++) {
            System.out.print(section[i] + " ");
        } System.out.println();
    }
}