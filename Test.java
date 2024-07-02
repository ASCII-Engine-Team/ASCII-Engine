import java.util.concurrent.TimeUnit;

class Test {
	static void fillScreen(char character) {
		String screen = "";
		for (int i = 0; i < Constants.SCREEN_HEIGHT; i++) {
			// so it works on nonchrome books
			for (int j = 0; j < Constants.SCREEN_WIDTH; j++) {
			screen += character;
			}
			screen += "\n";
		}
		
		// System.out.print("\033[H");
		// System.out.print("\033[2J");
		System.out.print(screen);
	}
	public static void clearScreen() {  
		System.out.print("\033[2J");  
		System.out.flush();  
	}  

	public static void main(String[] args) throws InterruptedException{
		final int millisecondPause = Integer.parseInt(args[0]);
		
		while (true) {
			fillScreen('"');
			TimeUnit.MILLISECONDS.sleep(millisecondPause);
			clearScreen();
			fillScreen('#');
			TimeUnit.MILLISECONDS.sleep(millisecondPause);
			clearScreen();
		}
	}
}
