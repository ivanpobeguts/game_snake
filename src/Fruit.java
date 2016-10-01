
import java.util.Random;

/*
    Класс объяекта для поедания "змейкой". 
 */

/**
 *
 * @author IvanP_000
 */
public class Fruit {
    
    GamePanel main;
    
    public int fx;
    public int fy;
    
    public Fruit(int startX, int startY){
        fx = startX;
        fy = startY;
    }
    
    // устанавливаем случайные координаты объекта
    public void setRandomPosition(){
        fx = new Random().nextInt(600 / 20) * 20;
        fy = new Random().nextInt(620 / 20) * 20;
    }
    
}
