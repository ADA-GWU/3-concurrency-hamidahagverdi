import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Main implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    int squareSize;
    int top;
    int bottom;
    int left;
    int right;
    JLabel label;
    BufferedImage img;

    public Main(int squareSize, int top, int bottom, int left, int right, JLabel label, BufferedImage img) {
        this.squareSize = squareSize;
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        this.label = label;
        this.img = img;
    }

    public Main() {}

    public static void main(String[] args) {
        // Run GUI in Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                createAndShowGUI(args);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Failed to start application", e);
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        });
    }

    private static void createAndShowGUI(String[] args) throws IOException {
        // Check arguments
        if (args.length < 3) {
            JOptionPane.showMessageDialog(null, "Usage: java Main <image-file> <square-size> <mode>");
            return;
        }

        String name = args[0];
        int square = Integer.parseInt(args[1]);
        String mode = args[2];

        // Load image
        File imageFile = new File(name);
        if (!imageFile.exists()) {
            JOptionPane.showMessageDialog(null, "Image file not found: " + name);
            return;
        }

        BufferedImage image = ImageIO.read(imageFile);
        System.out.println("Image loaded: " + name);

        int width = image.getWidth();
        int height = image.getHeight();

        // Resize if needed
        if (width > 1000 || height > 1000) {
            width = width / 3;
            height = height / 3;
        }

        BufferedImage outputImage = new BufferedImage(width, height, image.getType());
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();

        // Create and show GUI
        JFrame frame = new JFrame("Image Processor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel(new GridBagLayout());
        JLabel label = new JLabel(new ImageIcon(outputImage));
        
        panel.add(label);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);  // Center on screen
        frame.setVisible(true);

        System.out.println("Processing mode: " + mode);
        
        // Process image
        if (mode.equalsIgnoreCase("S")) {
            imageProcessing(outputImage, 0, 0, width, height, square, label);
        } else if (mode.equalsIgnoreCase("M")) {
            int numOfThreads = Runtime.getRuntime().availableProcessors();
            int heightPerThread = height / numOfThreads;

            for (int thread = 0; thread < numOfThreads; thread++) {
                int startY = thread * heightPerThread;
                int endY = (thread == numOfThreads - 1) ? height : (thread + 1) * heightPerThread;
                new Thread(new Main(square, startY, endY, 0, width, label, outputImage)).start();
            }
        }
    }

    // Rest of your code remains the same...
    // (imageProcessing method and run method)
}
