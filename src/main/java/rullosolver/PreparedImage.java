package rullosolver;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;


public class PreparedImage {
    BufferedImage image = null;
    File file = null;


    public static void main(String[] args) {
        PreparedImage pi = new PreparedImage();
        try {
            pi.prepareImage();
            System.out.println("Conversion successful.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void prepareImage() throws IOException {
        file = new File("C:/Users/benne/Downloads/Rullo.png");
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
                //calculate average

                //p = (a<<24) | (avg<<16) | (avg<<8) | avg;
                //replace RGB value with avg

                //image.setRGB(x, y, p);
            }
        }
        file = new File("C:/Users/benne/Downloads/RulloOutput.png");
        ImageIO.write(image, "png", file);
    }
}
