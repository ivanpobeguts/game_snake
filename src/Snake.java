
import java.util.Random;

/*
    Класс "змейки", где реализуется её перемещение по игровому полю.
 */

/**
 *
 * @author IvanP_000
 */
public class Snake {

    public int length = 1;
    public int direction = new Random().nextInt(3);
    public int snakeX[] = new int[1000];
    public int snakeY[] = new int[1000];
     
    public Snake(int x0, int y0) {
        snakeX[0] = x0;
        snakeY[0] = y0;
    }
      
    public void move() {
        for (int d = length; d > 0; d--){
            snakeX[d] = snakeX[d-1];
            snakeY[d] = snakeY[d-1];
        }
        if (direction == 0) snakeX[0]++;
        if (direction == 1) snakeY[0]++;
        if (direction == 2) snakeX[0]--;
        if (direction == 3) snakeY[0]--;
        
        if ((snakeX[0]* 20 == 600) && direction == 0) snakeX[0] = 0;
        if ((snakeY[0]* 20 == 620) && direction == 1) snakeY[0] = 0;
        if ((snakeX[0]* 20 == -20) && direction == 2) snakeX[0] = 580/20;      
        if ((snakeY[0]* 20 == -20) && direction == 3) snakeY[0] = 600/20;

        
    }
}
