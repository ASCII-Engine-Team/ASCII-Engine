import static java.util.concurrent.TimeUnit.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;

public class MovingBall {
	private static double secondsBetweenFrame;
	
	private static double ballCenterX;
	private static double ballCenterY;
	private static double ballRadius;

	private static double ballXChange;
	private static double ballYChange;

	static Runnable drawFrame;
	static Runnable updateState;
	
	public static void main(String[] args) {
		final int millisecondPause = 1000 / Constants.FPS;
		secondsBetweenFrame = (double)millisecondPause / 1000.0;

		// don't divide height by 2 because 
		// char height ~= 2 * char width
		ballCenterX = Constants.SCREEN_WIDTH / 2;
		ballCenterY = Constants.SCREEN_HEIGHT;

		ballRadius = 10.0;

		ballXChange = 13.7;
		ballYChange = -21.3;

		// insert code to draw each frame here
		drawFrame = () -> {
			String screen = "";
			for (int i = 0; i < Constants.SCREEN_HEIGHT; i++) {
				for (int j = 0; j < Constants.SCREEN_WIDTH; j++) {
					if (Utility.distance((double)j, (double)i*2, ballCenterX, ballCenterY) <= ballRadius) {
						screen += 'o';
					} else {
						screen += ' ';
					}
				}

				screen += '\n';
			}

			System.out.print(screen.substring(0, screen.length() - 1));
		};

		// insert any code to update the state of the animation here
		updateState = () -> {
			if (ballCenterX - ballRadius <= 0 && ballXChange < 0) {
				ballXChange = -ballXChange;
			} else if (ballCenterX + ballRadius >= Constants.SCREEN_WIDTH && ballXChange > 0) {
				ballXChange = -ballXChange;
			}

			if (ballCenterY - ballRadius <= 0 && ballYChange < 0) {
				ballYChange = -ballYChange;
			} else if (ballCenterY + ballRadius >= Constants.SCREEN_HEIGHT * 2 && ballYChange > 0) {
				ballYChange = -ballYChange;
			}

			ballCenterX += ballXChange * secondsBetweenFrame;
			ballCenterY += ballYChange * secondsBetweenFrame;
		};

		// initialize the scheduler
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		
		final Runnable makeFrame = () -> {
			Utility.clearScreen();
			drawFrame.run();
			updateState.run();
		};

		// schedule the frames
		final ScheduledFuture<?> frameHandler = 
			scheduler.scheduleAtFixedRate(makeFrame, 0, millisecondPause, MILLISECONDS);
	}
}
