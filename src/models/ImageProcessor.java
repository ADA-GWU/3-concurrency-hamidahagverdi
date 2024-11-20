package src.models;

import src.utils.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;

public class ImageProcessor implements Runnable {
    private final BufferedImage image;
    private final JLabel label;
    private final int squareSize;
    private final int startY;
    private final int endY;

    public ImageProcessor(BufferedImage image, JLabel label, int squareSize,
                          int startY, int endY) {
        this.image = image;
        this.label = label;
        this.squareSize = squareSize;
        this.startY = startY;
        this.endY = endY;
    }

    @Override
    public void run() {
        try {
            processRegion();
        } catch (IOException e) {
            LoggerUtils.getLogger().log(Level.SEVERE,
                    "Error processing image region", e);
        }
    }

    private void processRegion() throws IOException {
        for (int y = startY; y < endY; y += squareSize) {
            for (int x = 0; x < image.getWidth(); x += squareSize) {
                Color avgColor = ImageUtils.calculateAverageColor(
                        image, x, y, squareSize, image.getWidth(), endY);

                fillSquare(x, y, avgColor);
                ImageUtils.saveImage(image, "result.jpg");
                DisplayUtils.updateDisplay(image, label);
            }
        }
    }

    private void fillSquare(int x, int y, Color color) {
        int endY = Math.min(y + squareSize, this.endY);
        int endX = Math.min(x + squareSize, image.getWidth());

        for (int yd = y; yd < endY; yd++) {
            for (int xd = x; xd < endX; xd++) {
                image.setRGB(xd, yd, color.getRGB());
            }
        }
    }
}