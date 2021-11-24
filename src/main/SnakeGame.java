package main;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SnakeGame extends JPanel {

    private final int width = 50;
    private final int height = 50;

    private LinkedList<SnakePart> snake = new LinkedList<SnakePart>();
    private Point apple = new Point(0,0);
    private Random r = new Random();

    private boolean isGrowing = false;
    private boolean lost = false;

    private int offset = 0;
    private int newDir = 39;

    public SnakeGame() {
        createApple();
        snake.add(new SnakePart(0, 0, 39)); //39 = touche flèche droite clavier
        setBackground(Color.BLACK);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(500/60);   //on raffraichit 60 fois par secondes
                    }catch(InterruptedException e) {
                        System.out.println(e);
                    }
                    repaint();
                }
            }
        }).start();
    }

    public void createApple() {
        boolean atSamePos = true;
        do {
            apple.x = r.nextInt(12);    //12 = taille de l'écran nombre de carré en x ou y
            apple.y = r.nextInt(12);
            for(SnakePart p : snake) {
                if(p.x == apple.x && p.y == apple.y) {
                    atSamePos = false;
                    break;
                }
            }
        }while(!atSamePos);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(lost){
            g.setColor(Color.RED);
            g.setFont(new Font("Arial",60,60));
            g.drawString("Vous avez perdu !", 13*50/2 - g.getFontMetrics().stringWidth("Vous avez perdu !")/2, 13*50/2);
            return;
        }
        offset += 2;
        SnakePart head = null;
        if(offset == width){    //on passe alors à une autre case donc décalage à 0
            offset = 0;
            try {
                head = (SnakePart) snake.getFirst().clone();  //on duplique la tête
                head.move();
                head.dir = newDir;
                snake.addFirst(head);
                if(head.x == apple.x && head.y == apple.y) {
                    isGrowing = true;
                    createApple();
                }
                if(!isGrowing)
                    snake.pollLast();
                else
                    isGrowing = false;
            }catch (CloneNotSupportedException e) {
                System.out.println(e);
            }

        }
        g.setColor(Color.YELLOW);
        g.fillOval((apple.x * width) + (width/4), (apple.y * height) + (height/4), width/2, height/2);

        g.setColor(Color.RED);
        for(SnakePart p : snake) {
            if(offset == 0) {
                if(p != head) {
                    if(p.x == head.x && p.y == head.y) {
                        lost = true;
                    }
                }
            }
            if(p.dir == 39 || p.dir == 37) { //directions horizontales
                g.fillRect(p.x * width + ((p.dir == 37) ? -offset : offset), p.y * height, width, height);
            }else{
                g.fillRect(p.x * width, p.y * height + ((p.dir == 38) ? -offset : offset), width, height); //38 = flèche du haut clavier
            }
        }

        g.setColor(Color.WHITE);
        g.drawString("Votre score : " + (snake.size() -1), 20, 30);
    }

    public void onKeyPressed(int keyCode) {
        if(keyCode >= 37 && keyCode <= 40){ //on ne s'intéresse qu'aux flèches sur le clavier
            if(Math.abs(keyCode - newDir) != 2) {
                newDir = keyCode;
            }
        }
    }
    public static void main(String [] args) {
        JFrame frame = new JFrame("Snake game");
        SnakeGame panel = new SnakeGame();

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                panel.onKeyPressed(e.getKeyCode());
            }
        });

        frame.setContentPane(panel);
        frame.setSize(13*50,13*50);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}