package src.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.*;

public class ImageUtils {
    public static BufferedImage loadImage(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("Image file not found: " + filePath);
        }
        return ImageIO.read(file);
    }

    public static void saveImage(BufferedImage image, String fileName) throws IOException {
        ImageIO.write(image, "jpg", new File(fileName));
    }

    public static Color calculateAverageColor(BufferedImage image, int x, int y, int squareSize, int width, int height) {
        int sumR = 0, sumG = 0, sumB = 0;
        int endY = Math.min(y + squareSize, height);
        int endX = Math.min(x + squareSize, width);
        int pixelCount = (endY - y) * (endX - x);

        for (int y1 = y; y1 < endY; y1++) {
            for (int x1 = x; x1 < endX; x1++) {
                Color color = new Color(image.getRGB(x1, y1));
                sumR += color.getRed();
                sumG += color.getGreen();
                sumB += color.getBlue();
            }
        }

        return new Color(sumR/pixelCount, sumG/pixelCount, sumB/pixelCount);
    }
}
