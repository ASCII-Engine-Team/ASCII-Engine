import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class Keyboard implements NativeKeyListener {
    public static void main(String[] args) {
        try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

        GlobalScreen.addNativeKeyListener(new Keyboard());
    }
    
    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println(e.getKeyCode());
    }

    public void nativeKeyReleased() {

    }

    public void nativeKeyTyped() {}
}