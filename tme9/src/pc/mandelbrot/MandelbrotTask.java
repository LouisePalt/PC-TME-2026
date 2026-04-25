package pc.mandelbrot;

import java.util.concurrent.RecursiveAction;

import static pc.mandelbrot.MandelbrotCalculator.computePixelColor;

public class MandelbrotTask extends RecursiveAction {
    private static final int THRESHOLD = 5000;
    private final BoundingBox boundingBox;
    private final int maxIterations;
    private final int[] imageBuffer;
    private final int start;
    private final int end;

    public MandelbrotTask(BoundingBox boundingBox, int maxIterations, int[] imageBuffer, int start, int end) {
        this.boundingBox = boundingBox;
        this.maxIterations = maxIterations;
        this.imageBuffer = imageBuffer;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if ((end - start) * boundingBox.width <= THRESHOLD) {
            for (int py = start; py < end; py++) {
                for (int px = 0; px < boundingBox.width; px++) {
                    int color = computePixelColor(boundingBox, maxIterations, px, py);

                    imageBuffer[py * boundingBox.width + px] = color;
                }
            }
        } else {
            int middle = (start + end) / 2;

            MandelbrotTask firstTask = new MandelbrotTask(boundingBox, maxIterations, imageBuffer, start, middle);
            MandelbrotTask secondTask = new MandelbrotTask(boundingBox, maxIterations, imageBuffer, middle, end);

            firstTask.fork();
            secondTask.fork();

            secondTask.join();
            firstTask.join();
        }
    }
}
