package frame;

import main.*;

import javax.swing.JFrame;


public class SnakeFrame extends JFrame{
    public SnakeFrame(){
        JFrame frame = new JFrame("Snake game");
        // frame.addKeyListener(new KeyAdapter(){
        // 	public void keyPressed(KeyEvent e){
        // 		if(e.getKeyCode() == KeyEvent.VK_ENTER){
        // 		}
        // 	}
        // });

        SnakeGame panel = new SnakeGame();
        frame.setContentPane(panel);
        frame.setSize(600,600);
        frame.setResizable(false);  //la fenetre ne peut être redimensionné
        frame.setLocationRelativeTo(null);  //on centre la fenêtre sur l'écran
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
