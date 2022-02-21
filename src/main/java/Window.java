import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Window {
    public Window(int w, int h, String title, GameDriver gameDriver) {
        //set dimensions and colour of window
        gameDriver.setBackground(Color.white);
        gameDriver.setPreferredSize(new Dimension(w, h));
        gameDriver.setMaximumSize(new Dimension(w, h));
        gameDriver.setMinimumSize(new Dimension(w, h));
        
        //create frame and settings of frame
        JFrame frame = new JFrame(title);
        frame.add(gameDriver);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //start gameDriver
        gameDriver.start();
    }
}
