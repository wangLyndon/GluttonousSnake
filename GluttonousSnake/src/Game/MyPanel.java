package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class MyPanel extends JPanel implements KeyListener, Runnable {

    Snake snake;

    boolean running = true;

    List<Snake> bodies = new ArrayList<>();

    int score;

    Snake endBody;

    Food food;

    public MyPanel() {
        snake = new Snake(510, 720, 0);
    }

    public void drawSnake(Graphics g, int x, int y, int direct) {
        g.setColor(Color.red);
        switch (direct) {
            case 0:
                g.fillOval(x, y, 30, 30);
                g.setColor(Color.black);
                g.fillOval(x + 5, y + 5, 7, 7);
                g.fillOval(x + 18, y + 5, 7, 7);
                break;
            case 1:
                g.fillOval(x, y, 30, 30);
                g.setColor(Color.black);
                g.fillOval(x + 18, y + 5, 7, 7);
                g.fillOval(x + 18, y + 18, 7, 7);
                break;
            case 2:
                g.fillOval(x, y, 30, 30);
                g.setColor(Color.black);
                g.fillOval(x + 5, y + 18, 7, 7);
                g.fillOval(x + 18, y + 18, 7, 7);
                break;
            case 3:
                g.fillOval(x, y, 30, 30);
                g.setColor(Color.black);
                g.fillOval(x + 5, y + 5, 7, 7);
                g.fillOval(x + 5, y + 18, 7, 7);
                break;
        }
    }

    public void drawBody(Graphics g, int x, int y) {
        g.setColor(Color.red);
        g.fillOval(x, y, 30, 30);
    }


    public void eatFood() {
        if (food == null) {
            int x = (int) (Math.random() * 800 + 100);
            int y = (int) (Math.random() * 600 + 100);
            food = new Food(x, y);
        }
        int distance = (int) (Math.sqrt((Math.pow(((food.x - 15) - (snake.x - 15)), 2) + Math.pow(((food.y - 15) - (snake.y - 15)), 2))));
        if (distance < 30) {
            score++;
            if (bodies.isEmpty()) {
                //此处 snake.x + 1是为了防止在 touchSelf方法中的不足， 避免吃食物直接死亡
                bodies.add(new Snake(snake.x + 1, snake.y, snake.direct));
            } else {
                bodies.add(endBody);
            }
            food = null;
        }
    }

    public void move() {
        if (!inArea() || !touchSelf()) {
            return;
        }

        if (!bodies.isEmpty()) {
            endBody = new Snake(bodies.get(bodies.size() - 1).x, bodies.get(bodies.size() - 1).y, bodies.get(bodies.size() - 1).direct);

            for (int i = bodies.size() - 1; i > 0; i--) {
                bodies.get(i).x = bodies.get(i - 1).x;
                bodies.get(i).y = bodies.get(i - 1).y;
                repaint();
            }
            bodies.get(0).x = snake.x;
            bodies.get(0).y = snake.y;
            repaint();
        }
        switch (snake.direct) {
            case 0:
                snake.y = snake.y - 30;
                break;
            case 1:
                snake.x = snake.x + 30;
                break;
            case 2:
                snake.y = snake.y + 30;
                break;
            case 3:
                snake.x = snake.x - 30;
                break;
        }


        repaint();
    }

    public boolean inArea() {
        if (snake.x < 1 || (snake.x + 30) > 999 || snake.y < 1 || (snake.y + 30) > 799) {
            System.out.println("游戏结束");
            running = false;
            return false;
        }
        return true;
    }

    public boolean touchSelf(){
        for (Snake s : bodies) {
            if (s.x == snake.x && s.y == snake.y){
                System.out.println("游戏结束");
                running = false;
                return false;
            }
        }
        return true;
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 800);
        drawSnake(g, snake.x, snake.y, snake.direct);
        if (food != null) {
            g.setColor(Color.green);
            g.fillOval(food.x, food.y, 30, 30);
        }
        for (Snake s : bodies) {
            drawBody(g, s.x, s.y);
        }
        g.setColor(Color.black);
        g.setFont(new Font("微软雅黑", Font.BOLD, 25));
        g.drawString("分数：" + score, 1050, 100);
        g.setColor(Color.green);
        g.fillOval(1050, 150, 30, 30);
        g.setColor(Color.black);
        g.setFont(new Font("微软雅黑", Font.BOLD, 25));
        g.drawString(score + "", 1125, 175);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            snake.direct = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            snake.direct = 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            snake.direct = 2;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            snake.direct = 3;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void run() {
        while (running) {
            eatFood();
            move();
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
