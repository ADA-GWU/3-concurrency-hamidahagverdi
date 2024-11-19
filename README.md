# Image Processing Application

A robust Java application that processes images by applying pixel averaging effects with support for both single-threaded and multi-threaded processing. The application provides real-time visual feedback of the processing through a graphical user interface.

## Features

- **Flexible Image Processing**
  - Support for various image formats (JPG, PNG, etc.)
  - Customizable square size for pixel averaging
  - Real-time preview of processing effects
  - Automatic scaling for large images (>1000px)

- **Processing Modes**
  - Single-threaded processing (S)
  - Multi-threaded processing (M) utilizing available CPU cores
  
- **Error Handling**
  - Comprehensive error logging system
  - File-based logging with `imageprocessor.log`
  - User-friendly error messages
  - Robust input validation

## Requirements

- Java Development Kit (JDK) 8 or higher
- Minimum 2GB RAM (recommended)
- Graphics support for GUI display

## Installation

1. Clone the repository:
```bash
git clone
```

2. Compile the Java file:
```bash
javac Main.java
```

## Usage

1. Run the compiled program:
```bash
java Main
```

2. When prompted, enter the input in the following format:
```
filename square_size mode
```

Parameters:
- `filename`: Path to your image file (e.g., "image.jpg")
- `square_size`: Size of the averaging square in pixels (e.g., 20)
- `mode`: Processing mode - 'S' for single-threaded or 'M' for multi-threaded

Example input:
```
image.jpg 20 S
```

## Processing Modes Explained

### Single-threaded Mode (S)
- Processes the image sequentially
- Suitable for smaller images
- More predictable memory usage

### Multi-threaded Mode (M)
- Automatically utilizes available CPU cores
- Faster processing for larger images
- Divides image into sections for parallel processing

## Output

- Processed image is saved as "result.jpg" in the working directory
- Live preview is shown in a GUI window
- Processing logs are written to "imageprocessor.log"
