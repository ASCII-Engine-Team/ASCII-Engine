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
        circumRadius = 20.0;
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
		updateState = () -> {
            rotatedBy += secondsBetweenFrame * Math.PI;
        };

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

    static boolean squareContains(double x, double y) {
        double apothem = circumRadius / Math.sqrt(2);
        double slopeFromCenter = (y - squareCenterY) / (x - squareCenterX);
        double angleFromCenter = Math.atan(slopeFromCenter) % (Math.PI * 2);
        double distanceFromCenter = Utility.distance(x, y, squareCenterX, squareCenterY);

        if (distanceFromCenter <= apothem) {
            return true;
        } 

        double centerToEdgeDistance;
        if (angleFromCenter < Math.PI / 4) {
            centerToEdgeDistance = 0.7071 / Math.sin(Math.PI / 2 + angleFromCenter);
        } else if (angleFromCenter < Math.PI * 3 / 4) {
            centerToEdgeDistance = 0.7071 / Math.sin(Math.PI + angleFromCenter);
        } else if (angleFromCenter < Math.PI * 5 / 4) {
            centerToEdgeDistance = 0.7071 / Math.sin(3 * Math.PI / 2 + angleFromCenter);
        } else if (angleFromCenter < Math.PI * 7 / 4) { 
            centerToEdgeDistance = 0.7071 / Math.sin(Math.PI * 2 + angleFromCenter);
        } else {
            centerToEdgeDistance = 0.7071 / Math.sin(Math.PI / 2 + angleFromCenter);
        }

        if (distanceFromCenter <= centerToEdgeDistance) {
            return true;
        }

        return false;
    }
}