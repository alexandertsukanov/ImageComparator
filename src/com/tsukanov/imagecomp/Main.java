package com.tsukanov.imagecomp;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    private static final int NEXT_SQUARE_AFTER = 60000;

    public static void main(String[] args) {
        int minX = 0, minY = 0, maxX = 0, maxY = 0, noDifferenceBetweenPixels = 0;
        File imgFile1 = new File(args[0]);
        File imgFile2 = new File(args[1]);
        try {
            BufferedImage img1 = ImageIO.read(imgFile1);
            BufferedImage img2 = ImageIO.read(imgFile2);

            if (img1.getWidth() != img2.getWidth() && img1.getHeight() != img2.getHeight()) {
                System.err.println("Error: Images dimensions mismatch");
                System.exit(1);
            } else {
                int width = img1.getWidth();
                int height = img1.getHeight();
                BufferedImage copyOfImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = copyOfImage.createGraphics();
                File outputfile = new File(args[2]);
                g.drawImage(img2, 0, 0, null);
                g.setColor(Color.RED);
                boolean first = true;
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                            if (first) {
                                minX = x;
                                minY = y;
                                first = false;
                            }
                            maxX = x;
                            maxY = y;
                        }
                        else{
                            noDifferenceBetweenPixels++;
                        }
                        if(noDifferenceBetweenPixels == NEXT_SQUARE_AFTER){
                            g.drawRect(minX, minY, maxX - minX, maxY - minY);
                            first = true;
                            noDifferenceBetweenPixels = 0;
                        }
                    }
                }
                g.dispose();
                ImageIO.write(copyOfImage, "jpg", outputfile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

