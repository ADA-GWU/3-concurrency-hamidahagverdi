package src.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DisplayUtils {
    public static Dimension getScaledDimension(BufferedImage image) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int)(screenSize.getWidth() * 0.8);
        int screenHeight = (int)(screenSize.getHeight() * 0.8);

        double widthScale = (double)screenWidth / image.getWidth();
        double heightScale = (double)screenHeight / image.getHeight();
        double scale = Math.min(1.0, Math.min(widthScale, heightScale));

        return new Dimension(
                (int)(image.getWidth() * scale),
                (int)(image.getHeight() * scale)
        );
    }

    public static JFrame createMainFrame(JLabel imageLabel) {
        JFrame frame = new JFrame("Image Processor");
        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(imageLabel);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);

        return frame;
    }

    public static void updateDisplay(BufferedImage image, JLabel label) {
        Dimension scaledDim = getScaledDimension(image);
        BufferedImage scaledImage = new BufferedImage(
                scaledDim.width, scaledDim.height, image.getType());
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(image, 0, 0, scaledDim.width, scaledDim.height, null);
        g2d.dispose();

        label.setIcon(new ImageIcon(scaledImage));
        label.repaint();
    }
}