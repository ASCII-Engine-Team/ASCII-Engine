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
    static boolean debug;
    
    static Runnable drawFrame;
	static Runnable updateState;

	public static void main(String[] args) {
		final int millisecondPause = 1000 / Constants.FPS;
		secondsBetweenFrame = (double)millisecondPause / 1000.0;
        
        if (args.length > 0) {
            debug = Boolean.parseBoolean(args[0]);
        } else {
            debug = false;
        }

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
                        if (debug) continue;
                        screen.append('@');
                    } else {
                        if (debug) continue;
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
            if (rotatedBy < 0) {
                rotatedBy += Math.PI * 2;
            } else if (rotatedBy >= Math.PI * 2) {
                rotatedBy -= Math.PI * 2;
            }
        };

		// to initialize the scheduler
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        final Runnable makeFrame = () -> {
			if (!debug) {
                Utility.clearScreen();
            }
			drawFrame.run();
			updateState.run();
		};

		// to schedule the frames
		final ScheduledFuture<?> frameHandler = 
			scheduler.scheduleAtFixedRate(makeFrame, 0, millisecondPause, MILLISECONDS);
	}

    static boolean squareContains(double x, double y) {
        double slopeFromCenter = (y - squareCenterY) / (x - squareCenterX);
        double distanceFromCenter = Utility.distance(x, y, squareCenterX, squareCenterY);
        double angleFromCenter = Math.atan(slopeFromCenter) - rotatedBy;
        
        // makes sure angle is between 0 and 2pi
        while (angleFromCenter < 0) {
            angleFromCenter += Math.PI * 2;
        } while (angleFromCenter >= 2 * Math.PI) {
            angleFromCenter -= Math.PI * 2;
        } 

        double centerToEdgeDistance;
        if (angleFromCenter < Math.PI / 4) {
            centerToEdgeDistance = circumRadius * 0.7071 / Math.sin(Math.PI / 2 + angleFromCenter);
        } else if (angleFromCenter < Math.PI * 3 / 4) {
            centerToEdgeDistance = circumRadius * 0.7071/ Math.sin(angleFromCenter);
        } else if (angleFromCenter < Math.PI * 5 / 4) {
            centerToEdgeDistance = circumRadius * 0.7071 / Math.sin(3 * Math.PI / 2 + angleFromCenter);
        } else if (angleFromCenter < Math.PI * 7 / 4) { 
            centerToEdgeDistance = circumRadius * 0.7071 / Math.sin(Math.PI + angleFromCenter);
        } else {
            centerToEdgeDistance = circumRadius * 0.7071 / Math.sin(Math.PI / 2 + angleFromCenter);
        }

        if (debug && distanceFromCenter < circumRadius + 1.0 && distanceFromCenter > circumRadius - 1.0) {
            System.out.println(angleFromCenter + ", " + centerToEdgeDistance);
        }

        if (distanceFromCenter <= centerToEdgeDistance) {
            return true;
        }

        return false;
    }
}