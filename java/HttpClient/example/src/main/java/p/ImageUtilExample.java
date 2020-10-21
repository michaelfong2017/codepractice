package p;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class ImageUtilExample {

    public static void main(String args[]) {
        long startTime = System.nanoTime();

        String inputImageLocation = "image_to_post.jpg";
        String outputImageLocationHF = "image_to_post_flipped.jpg";
        String extension = "jpg";

        BufferedImage inputImage = readImage(inputImageLocation);
        BufferedImage outputImage = rotate180(inputImage);
        writeImage(outputImage, outputImageLocationHF, extension);

        long endTime = System.nanoTime();
        System.out.println("convert takes "+(endTime-startTime)+" nanoseconds");
    }

    /**
     * This method flips the image vertically
     * @param img --> BufferedImage object to be flipped
     * @return
     */
    private static BufferedImage verticalflip(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, img.getColorModel()
                .getTransparency());
        Graphics2D g = dimg.createGraphics();
        g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
        g.dispose();
        return dimg;
    }

    private static BufferedImage horizontalflip(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, img.getType());
        Graphics2D g = dimg.createGraphics();
        /*
         * img - the specified image to be drawn. This method does nothing if
         * img is null. dx1 - the x coordinate of the first corner of the
         * destination rectangle. dy1 - the y coordinate of the first corner of
         * the destination rectangle. dx2 - the x coordinate of the second
         * corner of the destination rectangle. dy2 - the y coordinate of the
         * second corner of the destination rectangle. sx1 - the x coordinate of
         * the first corner of the source rectangle. sy1 - the y coordinate of
         * the first corner of the source rectangle. sx2 - the x coordinate of
         * the second corner of the source rectangle. sy2 - the y coordinate of
         * the second corner of the source rectangle. observer - object to be
         * notified as more of the image is scaled and converted.
         *
         */
        g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
        g.dispose();
        return dimg;
    }

    private static BufferedImage rotate180(BufferedImage bufferedImage) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, -1);
        tx.translate(-bufferedImage.getWidth(null), -bufferedImage.getHeight(null));
        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(bufferedImage, null);
    }

    /**
     * This method reads an image from the file
     * @param fileLocation -- > eg. "C:/testImage.jpg"
     * @return BufferedImage of the file read
     */
    private static BufferedImage readImage(String fileLocation) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileLocation));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    private static void writeImage(BufferedImage img, String fileLocation,
                           String extension) {
        try {
            BufferedImage bi = img;
            File outputfile = new File(fileLocation);
            ImageIO.write(bi, extension, outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
