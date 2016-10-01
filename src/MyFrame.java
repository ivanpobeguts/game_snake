
import javax.swing.*;

/*
    Класс отвечает за создания окна приложения определённых размеров и с
    определёнными параметрами.
 */
/**
 *
 * @author IvanP_000
 */
public class MyFrame extends JFrame {

    private static final int GWIDTH = 800;
    private static final int GHEIGHT = 650;

    public MyFrame() {
        GamePanel gamePanel = new GamePanel();
        getContentPane().add(gamePanel);
        setTitle("Игра \"Змейка\"");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(GWIDTH, GHEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

    }

}
