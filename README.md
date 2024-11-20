# Concurrency
A high-performance Java application for image processing using pixel averaging effects, featuring both single and multi-threaded processing with real-time visualization.

## Features

Performance & Processing
- Single-threaded (S): Sequential, memory-efficient processing
- Multi-threaded (M): Parallel processing utilizing all CPU cores

Smart Image Handling
- Automatic screen-size adaptation
- Real-time preview of processing effects
- Support for various image formats (JPG, PNG)
- Customizable square size for pixel averaging

## Requirements

- Java Development Kit (JDK) 8 or higher
- Minimum 2GB RAM (recommended)
- Graphics support for GUI display

## Project Structure
```
src/
├── Main.java                 # Application entry point
├── models/
│   └── ImageProcessor.java   # Core processing logic
└── utils/
    ├── DisplayUtils.java     # GUI handling
    ├── ImageUtils.java       # Image operations
    ├── LoggerUtils.java      # Logging system
    └── ThreadUtils.java      # Thread management
```

## Components

[ImageProcessor](https://github.com/ADA-GWU/3-concurrency-hamidahagverdi/blob/main/src/models/ImageProcessor.java) class handles the core image processing:

- Takes an image section (defined by startY to endY)
- Processes image in square blocks
For each block:
- Calculates average color
- Fills block with that color
- Saves progress
- Updates display

Implements Runnable for multi-threading support

Key parameters:

1. image: Image to process
2. label: GUI display element
3. squareSize: Block size
4. startY/endY: Section boundaries
## Installation

1. Clone the repository:
```
# Clone & Navigate
git clone https://github.com/ADA-GWU/3-concurrency-hamidahagverdi.git
cd 3-concurrency-hamidahagverdi
```
2. Compile the Java file:
```
# Compile
mkdir out
javac -d out src/Main.java src/models/*.java src/utils/*.java
```
3. Run the compiled program:
```
#Run
java -cp out src.Main resources/images/monalisa.jpg 20 M
```
## Input

Parameters:
- `filename`: Path to your image file (e.g., "image.jpg")
- `square_size`: Size of the averaging square in pixels (e.g., 20)
- `mode`: Processing mode - 'S' for single-threaded or 'M' for multi-threaded

Example input:
```
resources/images/monalisa.jpg 20 S
resources/images/monalisa.jpg 20 M
```
- resources/images/monalisa.jpg - Image file path
- 20 - Pixel averaging block size
- S/M - Processing mode: Single-threaded/Multi-threaded Mode

## Output

- Processed image is saved as "result.jpg" in the working directory
- Live preview is shown in a GUI window
- Processing logs are written to "imageprocessor.log"

## Processing Modes

### Single-threaded Mode (S)
- Sequential top-to-bottom processing
- Optimal for smaller images
- Consistent memory usage
- Predictable processing pattern

### Multi-threaded Mode (M)
- Parallel processing across CPU cores
- Faster processing for larger images
- Divides image into sections for parallel processing
- Real-time concurrent updates

### Results

### Before Processing
![Original Image](resources/images/monalisa.jpg)

### After Processing
![Processing Result](result.jpg)
