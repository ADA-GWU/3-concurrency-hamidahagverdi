import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class Main implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    int squareSize;
    int top;
    int bottom;
    int left;
    int right;
    JLabel label;
    BufferedImage img;

    static {
        try {
            FileHandler fh = new FileHandler("imageprocessor.log", true);
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }

    public Main(int squareSize, int top, int bottom, int left, int right, JLabel jlabel, BufferedImage img) {
        this.squareSize = squareSize;
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        this.label = jlabel;
        this.img = img;
    }

    public Main() {}

    public static void main(String[] args) {
        try {
            // Force console output and add delay
            System.out.println("Enter image filename, square size and mode");
            System.out.println("Example: image.jpg 20 S");
            System.out.flush();
            Thread.sleep(100);

            Main obj = new Main();
            Thread thread = new Thread(obj);
            thread.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = reader.readLine();

            while (input == null || input.trim().isEmpty()) {
                System.out.println("Please enter input: ");
                input = reader.readLine();
            }

            String[] parts = input.split(" ");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid input format. Use: filename square_size mode");
            }

            String name = parts[0];
            int square = Integer.parseInt(parts[1]);
            String mode = parts[2];

            File file = new File(name);
            if (!file.exists()) {
                throw new FileNotFoundException("Image file not found: " + name);
            }

            BufferedImage image = ImageIO.read(file);
            int width = image.getWidth();
            int height = image.getHeight();

            if(width > 1000 || height > 1000) {
                width = width/3;
                height = height/3;
            }

            BufferedImage outputImage = new BufferedImage(width, height, image.getType());
            Graphics2D g2d = outputImage.createGraphics();
            g2d.drawImage(image, 0, 0, width, height, null);
            g2d.dispose();

            JFrame frame = new JFrame("Image Processor");
            JPanel panel = new JPanel();
            JLabel label = new JLabel();

            ImageIcon imageIcon = new ImageIcon(outputImage);
            label.setIcon(imageIcon);
            label.setSize(width, height);
            panel.setLayout(new GridBagLayout());
            panel.add(label);
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.add(panel);
            frame.setSize(width + 40, height + 60);
            frame.setVisible(true);

            if(mode.equals("S")) {
                imageProcessing(outputImage, 0, 0, width, height, square, label);
            }
            else if(mode.equals("M")) {
                int numOfThreads = Runtime.getRuntime().availableProcessors();
                for(int threads = 0; threads < numOfThreads; threads++) {
                    int heightTemp = (height/square)/(numOfThreads-1);
                    if(threads == (numOfThreads-1)) {
                        int borderLow = heightTemp*square*(threads-1);
                        new Thread(new Main(square, borderLow, height, 0, width, label, outputImage)).start();
                    } else {
                        int borderLow = heightTemp*square*threads;
                        int borderHigh = heightTemp*square*(threads+1);
                        new Thread(new Main(square, borderLow, borderHigh, 0, width, label, outputImage)).start();
                    }
                }
            } else {
                throw new IllegalArgumentException("Invalid mode. Use 'S' for single-threaded or 'M' for multi-threaded");
            }

        } catch(IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Input validation error: " + e.getMessage());
            System.err.println("Error: " + e.getMessage());
        } catch(IOException e) {
            LOGGER.log(Level.SEVERE, "IO error occurred", e);
            System.err.println("IO Error: " + e.getMessage());
        } catch(Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error occurred", e);
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    static void imageProcessing(BufferedImage outputImage, int top, int left, int width, int height, int square, JLabel label) throws IOException {
        try {
            for(int y = top; y < height; y += square) {
                for(int x = left; x < width; x += square) {
                    int sumR = 0, sumG = 0, sumB = 0;
                    int endY = Math.min(y + square, height);
                    int endX = Math.min(x + square, width);

                    for(int y1 = y; y1 < endY; y1++) {
                        for(int x1 = x; x1 < endX; x1++) {
                            Color color = new Color(outputImage.getRGB(x1,y1));
                            sumR += color.getRed();
                            sumG += color.getGreen();
                            sumB += color.getBlue();
                        }
                    }

                    int pixelCount = (endY - y) * (endX - x);
                    Color avgColor = new Color(sumR/pixelCount, sumG/pixelCount, sumB/pixelCount);

                    for(int yd = y; yd < endY; yd++) {
                        for(int xd = x; xd < endX; xd++) {
                            outputImage.setRGB(xd, yd, avgColor.getRGB());
                        }
                    }

                    ImageIO.write(outputImage, "jpg", new File("result.jpg"));
                    label.setIcon(new ImageIcon(outputImage));
                    label.repaint();
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error processing image", e);
            throw e; // Re-throw the exception to be handled by the caller
        }
    }

    @Override
    public void run() {
        try {
            if(img != null) {
                imageProcessing(img, top, left, right, bottom, squareSize, label);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error in thread execution", e);
            System.err.println("Thread error: " + e.getMessage());
        }
    }
}