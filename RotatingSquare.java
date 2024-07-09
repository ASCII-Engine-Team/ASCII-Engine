import static java.util.concurrent.TimeUnit.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;

public class RotatingSquare {
	static double squareCenterX;
    static double squareCenterY;
    static double circumRadius;
    static double rotatedBy;

    static double secondsBetweenFrame;
    
    static Runnable drawFrame;
	static Runnable updateState;

	public static void main(String[] args) {
		final int millisecondPause = 1000 / Constants.FPS;
		secondsBetweenFrame = (double)millisecondPause / 1000.0;

        squareCenterX = Constants.SCREEN_WIDTH / 2;
        squareCenterY = Constants.SCREEN_HEIGHT * Constants.Y_STRETCH / 2;
        circumRadius = 7.0;
        rotatedBy = 0.0;

		// insert code to draw each frame here
		drawFrame = () -> {
            StringBuilder screen = new StringBuilder(Constants.SCREEN_WIDTH * Constants.SCREEN_HEIGHT);
            for (int y = 0; y < Constants.SCREEN_HEIGHT; y++) {
                for (int x = 0; x < Constants.SCREEN_WIDTH; x++) {
                    if (squareContains((double)x, y * Constants.Y_STRETCH)) {
                        screen.append('@');
                    } else {
                        screen.append(' ');
                    }
                }

                screen.append('\n');
            }
            
            screen.setLength(screen.length() - 1);
			System.out.print(screen);
        };

		// insert any code to update the state of the animation here
		updateState = () -> {};

		// to initialize the scheduler
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

		final Runnable makeFrame = () -> {
			Utility.clearScreen();
			drawFrame.run();
			updateState.run();
		};

		// to schedule the frames
		final ScheduledFuture<?> frameHandler = 
			scheduler.scheduleAtFixedRate(makeFrame, 0, millisecondPause, MILLISECONDS);
	}

    private static boolean squareContains(double x, double y) {
        double area = 2 * Math.pow(circumRadius, 2);
        
        double aX = Math.cos(rotatedBy);
        double aY = Math.sin(rotatedBy);

        double bX = Math.cos(rotatedBy + Math.PI / 2);
        double bY = Math.sin(rotatedBy + Math.PI / 2);

        double cX = Math.cos(rotatedBy + Math.PI);
        double cY = Math.sin(rotatedBy + Math.PI);

        double dX = Math.cos(rotatedBy - Math.PI / 2);
        double dY = Math.sin(rotatedBy - Math.PI / 2);

        // abs(x1(y2 - y3) + x2(y3 - y1) + x3(y1 - y2)) / 2;
        double triAreaSum = 0.0;
        triAreaSum += Math.abs(aX * (bY - y) + bX * (y - aY) + x * (aY - bY)) / 2;
        triAreaSum += Math.abs(bX * (cY - y) + cX * (y - bY) + x * (bY - cY)) / 2;
        triAreaSum += Math.abs(cX * (dY - y) + dX * (y - cY) + x * (cY - dY)) / 2;
        triAreaSum += Math.abs(dX * (aY - y) + aX * (y - dY) + x * (dY - aY)) / 2;

        if (triAreaSum > area) {
            return false;
        } else {
            return true;
        }
    }
}