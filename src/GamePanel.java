
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

/*
    Класс игровой панели - самый функциональный класс игры. Предназначен для:
    - отрисовки игрового поля
    - расположения кнопок "Новая игра" и "Выход"
    - изменения изображения во времени
    - отображения текущего счёта
    Скорость змейки можно изменять с помощью параметра SPEED.
 */
/**
 *
 * @author IvanP_000
 */
public class GamePanel extends JPanel {

    private static final int GWIDTH = 600;
    private static final int GHEIGHT = 620;
    private static final int SCALE = 20;
    // скорость змейки
    private static final int SPEED = 20;
    private Timer tPaint, tUpdate;
    private int sc = 0;
    private JButton exitButton, newGame;
    private Snake s;
    private Fruit f;
    private JLabel textAreaScore;
    private JLabel end;
    private Image head, body, cherry, apple, bananas, grapes, backgr, go, im;
    private GamePanel pan;
    // массив для хранение картинок объектов для поедания
    ArrayList<Image> fruits = new ArrayList<>(4); 

    public GamePanel() {
        pan = this;
        this.addKeyListener(new Keyboard());
        this.setFocusable(true);
        try {
            head = ImageIO.read(new File("head.jpg"));
            body = ImageIO.read(new File("body.jpg"));
            cherry = ImageIO.read(new File("cherries.png"));
            apple = ImageIO.read(new File("apple.png"));
            bananas = ImageIO.read(new File("bananas.png"));
            grapes = ImageIO.read(new File("grapes.png"));
            backgr = ImageIO.read(new File("img.jpg"));
            go = ImageIO.read(new File("end.jpg"));
        } catch (Exception ex) {
        }

        fruits.add(cherry);
        fruits.add(apple);
        fruits.add(bananas);
        fruits.add(grapes);
        
        // создание объекта змейки в центре поля
        s = new Snake(15, 15);
        // создание объекта для поедания со случайными координатами
        f = new Fruit(new Random().nextInt(GWIDTH / SCALE) * SCALE, new Random().nextInt(GHEIGHT / SCALE) * SCALE);
        
        // таймер для отрисовки игрового поля
        tPaint = new Timer(20, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                repaint();
            }

        });
        tPaint.start();
        
        // таймер для перемещения змейки и обновления счёта
        tUpdate = new Timer(1000 / SPEED, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                s.move();
                textAreaScore.setText("Счет: " + sc);
            }

        });
        
        tUpdate.start();
        
        setLayout(null);
        
        // добавление надписи счёта на панель
        textAreaScore = new JLabel("Счет: 0");
        textAreaScore.setForeground(Color.WHITE);
        textAreaScore.setFont(new Font("serif", 0, 30));
        textAreaScore.setBounds(630, 200, 150, 50);
        add(textAreaScore);
        
        // добавление надписи конца игры на панель
        end = new JLabel("Конец игры");
        end.setForeground(Color.RED);
        end.setFont(new Font("serif", 0, 30));
        end.setBounds(300, 250, 250, 50);
        add(end);
        end.setVisible(false);
        
        // добавление кнопки новой игры на панель
        newGame = new JButton();
        newGame.setText("Новая игра");
        newGame.setForeground(Color.DARK_GRAY);
        newGame.setFont(new Font("serif", 0, 20));
        newGame.setBounds(630, 30, 150, 50);
        newGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                tPaint.stop();
                tPaint.start();
                s = new Snake(15, 15);
                f = new Fruit(new Random().nextInt(GWIDTH / SCALE) * SCALE, new Random().nextInt(GHEIGHT / SCALE) * SCALE);
                im = RandomFruit();
                newGame.setFocusable(false);
                exitButton.setFocusable(false);
                pan.setFocusable(true);
                sc = 0;
                end.setVisible(false);
            }

        });

        add(newGame);
        
        // добавление кнопки выхода на панель
        exitButton = new JButton();
        exitButton.setText("Выход");
        exitButton.setForeground(Color.RED);
        exitButton.setFont(new Font("serif", 0, 20));
        exitButton.setBounds(630, 100, 150, 50);
        exitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }

        });

        add(exitButton);
        im = RandomFruit();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // отрисовка фона
        g.drawImage(backgr, 0, 0, 800, 650, null);

        g.setColor(Color.WHITE);
        // отрисовка поля без сетки
        g.fillRect(0, 0, GWIDTH, GHEIGHT);
        
        g.setColor(Color.GREEN);
        // отрисовка сетки игрового поля
        for (int xx = 0; xx <= GWIDTH; xx += SCALE) {
            g.drawLine(xx, 0, xx, GHEIGHT);
        }
        for (int yy = 0; yy <= GHEIGHT; yy += SCALE) {
            g.drawLine(0, yy, GWIDTH, yy);
        }
        // отрисовка головы змейки
        g.drawImage(head, s.snakeX[0] * SCALE + 1, s.snakeY[0] * SCALE + 1, this);
        // отрисовка тела змейки
        for (int d = 1; d < s.length; d++) {
            g.drawImage(body, s.snakeX[d] * SCALE + 1, s.snakeY[d] * SCALE + 1, this);
        }
        // отрисовка случайного объекта для поедания
        g.drawImage(im, f.fx, f.fy, this);
        // условие поедания змейкой объекта
        if ((s.snakeX[0] * SCALE == f.fx) && (s.snakeY[0] * SCALE == f.fy)) {
            s.length++;
            sc += 10;
            im = RandomFruit();
            f.setRandomPosition();
        }
        // если объект появляется на хвосте змейки, то изменяется его местоположение
        for (int i = 1; i < s.length; i++) {
            if ((s.snakeX[i] * SCALE == f.fx) && (s.snakeY[i] * SCALE == f.fy)) {
                f.setRandomPosition();
            }
        }
        // условие столкновения змейки с хвостом
        for (int d = s.length; d > 0; d--) {
            if ((s.snakeX[0] == s.snakeX[d]) && (s.snakeY[0] == s.snakeY[d])) {
                tPaint.stop();
                end.setVisible(true);
                g.drawImage(go, 250, 225, 250, 100, null);
                
            }

        }
    }
    
    // класс клавиатуры 
    private class Keyboard extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent kev) {
            int k = kev.getKeyCode();
            if ((k == KeyEvent.VK_RIGHT) && s.direction != 2) {
                s.direction = 0;
            }
            if ((k == KeyEvent.VK_DOWN) && s.direction != 3) {
                s.direction = 1;
            }
            if ((k == KeyEvent.VK_LEFT) && s.direction != 0) {
                s.direction = 2;
            }
            if ((k == KeyEvent.VK_UP) && s.direction != 1) {
                s.direction = 3;
            }
        }
    }
    
    // метод выбирает случайную картинку из масива объектов для поедания
    private Image RandomFruit() {
        Image result = fruits.get(new Random().nextInt(4));
        return result;
    }

}
