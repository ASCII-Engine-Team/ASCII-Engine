import java.util.concurrent.TimeUnit;

class Test {
	static void fillScreen(char character) {
		String screen = "";
		for (int i = 0; i < Constants.SCREEN_WIDTH * Constants.SCREEN_HEIGHT; i++) {
			screen += character;
		}
		
		// System.out.print("\033[H");
		// System.out.print("\033[2J");
		System.out.print(screen);
	}

	public static void main(String[] args) throws InterruptedException{
		final int millisecondPause = Integer.parseInt(args[0]);
		while (true) {
			fillScreen('"');
			TimeUnit.MILLISECONDS.sleep(millisecondPause);
			fillScreen('#');
			TimeUnit.MILLISECONDS.sleep(millisecondPause);
		}
	}
}
