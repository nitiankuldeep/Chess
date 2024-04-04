package main;
import javax.swing.JFrame;

public class Main {
    public static void main(String []args) {

        JFrame window = new JFrame("Chess");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GameSpace g= new GameSpace();
        window.add(g);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        g.runGame();

    }
}
