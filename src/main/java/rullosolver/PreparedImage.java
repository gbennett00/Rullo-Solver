package rullosolver;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

/**
 * Prepares images to be passed read using OCR. Essentially strips everything 
 * from a Rullo puzzle photo other than the white numbers.
 */
public class PreparedImage {
    private BufferedImage image = null;
    private File file = null;
    private String filePath;

    /** Creates a prepared image with a specified file path. */
    protected PreparedImage(String filePathIn) {
        filePath = filePathIn;
        try {
            prepareImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /** Returns the prepared image. */
    protected BufferedImage getPreparedImage() {
        return image;
    }

    /** Prepares an image by stripping all colors and retaining the white numbers. */
    private void prepareImage() throws IOException {
        file = new File(filePath);
        image = ImageIO.read(file);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int p = image.getRGB(x,y);

                int r = (p>>16)&0xff;
                int a = (p>>24)&0xff;
                int b = p&0xff;
                int g = (p>>8)&0xff;

                if (r < 240 || b < 240 || g < 240) {
                    p = (a<<24) | (0<<16) | (0<<8) | 0;
                    image.setRGB(x, y, p);;
                }
            }
        }
    }

    /** Saves the prepared image to a file in the specified directory. */
    protected void saveFile(String directory) throws IOException {
        if (directory == null) {
            file = new File("C:/Users/benne/Downloads/RulloOutput.png");
        } else {
            file = new File(directory);
        }
        ImageIO.write(image, "png", file);
    }
}
