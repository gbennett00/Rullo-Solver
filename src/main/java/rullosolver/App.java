package rullosolver;


import java.io.File;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;



/**
 * Hello world!
 *
 */
public class App 
{
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

    protected static final int[][] value = {
        {7, 1, 9, 4, 1, 8, 7},
        {5, 6, 3, 4, 2, 2, 2},
        {9, 7, 8, 4, 5, 2, 8}, 
        {3, 5, 1, 2, 5, 9, 5},
        {9, 5, 4, 8, 9, 3, 5},
        {5, 1, 1, 9, 7, 4, 2},
        {9, 9, 6, 1, 2, 4, 2}
    };
}