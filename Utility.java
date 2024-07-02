public class Utility {
    public static double distance(double x1, double y1, double x2, double y2) {
		double xDiff = x2 - x1;
		double yDiff = y2 - y1;
		return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
	}

    public static void clearScreen() {  
		System.out.print("\033[2J");  
		System.out.flush();  
	}  
}