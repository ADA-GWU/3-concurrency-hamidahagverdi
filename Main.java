import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.out.println("Usage: java Main <image-file> <square-size> <mode>");
            return;
        }

        final String filename = args[0];
        final int squareSize = Integer.parseInt(args[1]);
        final String mode = args[2].toUpperCase();

        final BufferedImage originalImage = ImageIO.read(new File(filename));

        // Basic display
        JFrame frame = new JFrame("Image Pixelator");
        final JLabel label = new JLabel(new ImageIcon(originalImage));
        frame.add(label);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}