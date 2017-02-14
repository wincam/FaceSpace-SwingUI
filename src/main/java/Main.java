import javax.swing.*;

public class Main {
    public static void main(String args[]) {
        // Creates a UI thread that will launch our AppContainer JFrame
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AppContainer();
            }
        });
    }
}

