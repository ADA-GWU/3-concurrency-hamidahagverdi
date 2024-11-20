package src;

import src.utils.ImageUtils;
import src.utils.ThreadUtils;
import src.utils.LoggerUtils;

import src.models.ImageProcessor;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

public class Main {
    public static void main(String[] args) {
        try {
            // Validate arguments
            if (args.length != 3) {
                throw new IllegalArgumentException(
                        "Usage: java Main <filename> <square_size> <mode>");
            }

            // Parse inputs
            BufferedImage image = ImageUtils.loadImage(args[0]);
            int squareSize = Integer.parseInt(args[1]);
            String mode = args[2].toUpperCase();

            // Initialize display
            JLabel label = new JLabel(new ImageIcon(image));
            JFrame frame = src.utils.DisplayUtils.createMainFrame(label);
            frame.setVisible(true);

            // Process image
            if (mode.equals("S")) {
                new ImageProcessor(image, label, squareSize, 0, image.getHeight()).run();
            } else if (mode.equals("M")) {
                ThreadUtils.processImageMultiThreaded(image, label, squareSize);
            } else {
                throw new IllegalArgumentException(
                        "Mode must be 'S' for single-threaded or 'M' for multi-threaded");
            }

        } catch (Exception e) {
            LoggerUtils.getLogger().log(Level.SEVERE, "Error in main", e);
            System.err.println("Error: " + e.getMessage());
        }
    }
}