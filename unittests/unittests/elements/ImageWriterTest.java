package unittests.elements;

import org.junit.jupiter.api.Test;
import primitives.Color;
import renderer.ImageWriter;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {
    /**
     * Test method for
     * {@link ImageWriter#writeToImage()}  }
     */
    @Test
    // ============ Equivalence Partitions Tests ==============
    //TC01: building A grid of 10 on 16 squares with a resolution of 800 * 500 in blue
    void testWriteToImage() {
        ImageWriter imageWriter = new ImageWriter("blue grid", 800, 500);
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 500; j++) {
                if (i % 50 == 0) {
                    imageWriter.writePixel(i, j, Color.BLACK);
                } else if (j % 50 == 0) {
                    imageWriter.writePixel(i, j, Color.BLACK);
                } else {
                    imageWriter.writePixel(i, j, new Color(java.awt.Color.BLUE));
                }
            }
        }
        imageWriter.writeToImage();
    }

}