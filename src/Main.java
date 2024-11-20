package src;
import src.utils.ImageUtils;
import src.utils.ThreadUtils;
import src.utils.LoggerUtils;
import src.models.ImageProcessor;
import javax.swing.*;
import java.awt.*;
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

            // Check screen size and resize if needed
            Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
            if (image.getWidth() > displaySize.width || image.getHeight() > displaySize.height) {
                double ratio = Math.min(
                        (double) displaySize.width / image.getWidth(),
                        (double) displaySize.height / image.getHeight()
                );
                int scaledWidth = (int) (image.getWidth() * ratio);
                int scaledHeight = (int) (image.getHeight() * ratio);

                BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight, image.getType());
                Graphics2D graphics = scaledImage.createGraphics();
                graphics.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
                graphics.dispose();
                image = scaledImage;
                System.out.println("Image resized to fit screen dimensions");
            }

            // Initialize display
            JLabel label = new JLabel(new ImageIcon(image));
            JFrame frame = src.utils.DisplayUtils.createMainFrame(label);
            frame.setVisible(true);

            // Process image
            System.out.println("Processing in " + (mode.equals("S") ? "single" : "multi") + "-threaded mode...");
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