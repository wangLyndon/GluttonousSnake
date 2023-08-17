package Game;

import javax.swing.*;

public class App extends JFrame {
    MyPanel myPanel;

    public App(){
        myPanel = new MyPanel();
        this.add(myPanel);
        this.setSize(1300, 800);
        this.addKeyListener(myPanel);
        new Thread(myPanel).start();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new App();
    }
}
