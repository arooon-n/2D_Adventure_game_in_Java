package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Utilitytool {
    public BufferedImage scaleImage(BufferedImage original, int height, int width){
        BufferedImage scaledimage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledimage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();
        
        return scaledimage;
    }
}
