import static java.util.concurrent.TimeUnit.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;

class Test {
	private static boolean displayWhite;
	private static String whiteScreen;
	private static String greyScreen;

	public static void main(String[] args) throws InterruptedException{
		final int millisecondPause = Integer.parseInt(args[0]);

		displayWhite = true;
		whiteScreen = getFullScreen('#');
		greyScreen = getFullScreen('\'');

		final Runnable displayFrame = () -> {
			displayWhite = !displayWhite;
			if (displayWhite) {
				System.out.print(whiteScreen);
			} else {
				System.out.print(greyScreen);
			}
		};

		// initialize the scheduler
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

		// schedule the frames
		final ScheduledFuture<?> frameHandler = 
			scheduler.scheduleAtFixedRate(displayFrame, 0, millisecondPause, MILLISECONDS);
	}
	
	static String getFullScreen(char character) {
		String screen = "";
		for (int i = 0; i < Constants.SCREEN_HEIGHT; i++) {
			// so it works on nonchrome books
			for (int j = 0; j < Constants.SCREEN_WIDTH; j++) {
				screen += character;
			}
			screen += "\n";
		}
		
		return screen.substring(0, screen.length() - 1);
	}
	
	public static void clearScreen() {  
		System.out.print("\033[2J");  
		System.out.flush();  
	}  
}
