import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class WASD implements NativeKeyListener {
    public static void main(String[] args) {
        WASD input = new WASD();

		while (true) {
			System.out.print("");

			if (input.getWPressed()) {
				System.out.println("Up");
			}
			
			if (input.getAPressed()) {
				System.out.println("Left");
			}

			if (input.getSPressed()) {
				System.out.println("Down");
			}

			if (input.getDPressed()) {
				System.out.println("Right");
			}
		}
    }

	public WASD() {
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(this);
	}

	public boolean getWPressed() {
		return wPressed;
	}

	public boolean getAPressed() {
		return aPressed;
	}

	public boolean getSPressed() {
		return sPressed;
	}

	public boolean getDPressed() {
		return dPressed;
	}

	private boolean wPressed = false;
	private boolean aPressed = false;
	private boolean sPressed = false;
	private boolean dPressed = false;
    
    public void nativeKeyPressed(NativeKeyEvent e) {
        switch (e.getKeyCode()) {
			case 17:
				wPressed = true;
				break;
			case 30:
				aPressed = true;
				break;
			case 31:
				sPressed = true;
				break;
			case 32:
				dPressed = true;
				break;
		}
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
		switch (e.getKeyCode()) {
			case 17:
				wPressed = false;
				break;
			case 30:
				aPressed = false;
				break;
			case 31:
				sPressed = false;
				break;
			case 32:
				dPressed = false;
				break;
		}
    }
}
