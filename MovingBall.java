import static java.util.concurrent.TimeUnit.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;

public class MovingBall {
	private static double secondsBetweenFrame;
	
	private static double ballCenterX;
	private static double ballCenterY;
	private static double ballRadius;

	private static double ballXSpeed;
	private static double ballYSpeed;

	private static WASD input;

	private static Runnable drawFrame;
	private static Runnable updateState;
	
	public static void main(String[] args) {
		final int millisecondPause = 1000 / Constants.FPS;
		secondsBetweenFrame = (double)millisecondPause / 1000.0;

		// don't divide height by 2 because 
		// char height ~= 2 * char width
		ballCenterX = Constants.SCREEN_WIDTH / 2;
		ballCenterY = Constants.SCREEN_HEIGHT;

		ballRadius = 10.0;

		ballXSpeed = 10.0;
		ballYSpeed = 10.0;

		// insert code to draw each frame here
		drawFrame = () -> {
			String screen = "";
			for (int i = 0; i < Constants.SCREEN_HEIGHT; i++) {
				for (int j = 0; j < Constants.SCREEN_WIDTH; j++) {
					if (Utility.distance((double)j, (double)i*Constants.Y_STRETCH, ballCenterX, ballCenterY) <= ballRadius) {
						screen += 'o';
					} else {
						screen += ' ';
					}
				}

				screen += '\n';
			}

			System.out.print(screen.substring(0, screen.length() - 1));
		};

		input = new WASD();

		// insert any code to update the state of the animation here
		updateState = () -> {
			if (input.getWPressed()) {
				ballCenterY -= ballYSpeed * secondsBetweenFrame;
			}
			if (input.getSPressed()) {
				ballCenterY += ballYSpeed * secondsBetweenFrame;
			}
			if (input.getAPressed()) {
				ballCenterX -= ballXSpeed * secondsBetweenFrame;
			}
			if (input.getDPressed()) {
				ballCenterX += ballXSpeed * secondsBetweenFrame;
			}
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
