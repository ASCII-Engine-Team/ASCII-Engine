import java.awt.event.*;

public class Keyboard implements KeyListener {
    public static void main(String[] args) {
        Keyboard keyboard = new Keyboard();
        while (true) {

        }
        
    }
    
    private boolean wPressed = false;
    private boolean aPressed = false;
    private boolean sPressed = false;
    private boolean dPressed = false;

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 37:
                System.out.println(37);
                break;
            case 38:
                System.out.println(38);
                break;
            case 39:
                System.out.println(39);
                break;
            case 40:
                System.out.println(40);
                break;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {}
}