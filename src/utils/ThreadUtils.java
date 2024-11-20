package src.utils;

import src.models.ImageProcessor;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class ThreadUtils {
    public static void processImageMultiThreaded(BufferedImage image, JLabel label,
                                                 int squareSize) {
        int numThreads = Runtime.getRuntime().availableProcessors();
        int height = image.getHeight();

        for (int i = 0; i < numThreads; i++) {
            int startY = (height / numThreads) * i;
            int endY = (i == numThreads - 1) ? height : (height / numThreads) * (i + 1);

            new Thread(new ImageProcessor(image, label, squareSize, startY, endY))
                    .start();
        }
    }
}
