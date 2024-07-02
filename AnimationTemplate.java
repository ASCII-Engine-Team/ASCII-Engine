import static java.util.concurrent.TimeUnit.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;

public class AnimationTemplate {
	static Runnable drawFrame;

	public static void main(String[] args) {
		final int millisecondPause = Integer.parseInt(args[0]);

		// insert code to draw each frame here
		drawFrame = () -> {};

		// insert any code to update the state of the animation here
		updateState = () -> {};

		// to initialize the scheduler
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

		final Runnable makeFrame = () -> {
			clearScreen();
			drawFrame.run();
			updateState.run();
		};

		// to schedule the frames
		final ScheduledFuture<?> frameHandler = 
			scheduler.scheduleAtFixedRate(makeFrame, 0, millisecondPause, MILLISECONDS);
	}
}