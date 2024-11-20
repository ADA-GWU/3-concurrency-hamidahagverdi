package src.utils;

import java.util.logging.*;
import java.io.IOException;

public class LoggerUtils {
    private static final Logger LOGGER = Logger.getLogger("ImageProcessor");

    static {
        try {
            FileHandler fh = new FileHandler("imageprocessor.log", true);
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}