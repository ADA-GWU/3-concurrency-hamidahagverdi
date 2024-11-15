import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedImage image = ImageIO.read(new File("path/to/image.jpg"));
        int width = image.getWidth();
        int height = image.getHeight();

        // Resize image if it’s too large
        if (width > 1000 || height > 1000) {
            width /= 3;
            height /= 3;
        }
        
        BufferedImage outputImage = new BufferedImage(width, height, image.getType());
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();

        // Create JFrame and display image
        JFrame frame = new JFrame();
        JLabel label = new JLabel(new ImageIcon(outputImage));
        frame.add(label);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
