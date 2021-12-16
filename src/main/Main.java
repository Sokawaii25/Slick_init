package main;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;


public class Main extends BasicGame {

    private static final String GAMENAME = "The One";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private Image character = null;
    private Player player = null;
    private Player symmetry = null;
    private Vector2f p_pos = null;
    private Vector2f s_pos = null;
    private int p_size;
    private boolean collides;
    private boolean rotate = false;

    /**
     * Create a new basic game
     *
     * @param title The title for the game
     */
    public Main(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        //load resource, create stuff...
        //loading screen!!!!
        //hide mouse
        try {
            Cursor emptyCursor = new Cursor(1, 1, 0, 0, 1, BufferUtils.createIntBuffer(1), null);
            Mouse.setNativeCursor(emptyCursor);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        //init variables
        player = new Player(new Vector2f(0, 0), new Rectangle(0, 0, 50, 50), new Image("assets/images/tungsten_block.png"), 50);
        symmetry = new Player(new Vector2f(WIDTH, HEIGHT), new Rectangle(WIDTH, HEIGHT, 50, 50), new Image("assets/images/tungsten_block.png"), 50);
    }

    @Override
    public void update(GameContainer container, int delta) {
        Input input = container.getInput();
        int x = input.getMouseX();
        int y = input.getMouseY();  //-Mouse... + window height ti go to normal

        player.update(x, y);
        symmetry.update(-x+WIDTH, -y+HEIGHT);

        collides = player.collides(symmetry);
    }

    @Override
    public void render(GameContainer container, Graphics g) {
        if (collides) {
            player.hits(symmetry);
            collides = false;
        }

        g.setColor(Color.white);
        player.render(g);
        g.setColor(Color.green);
        symmetry.render(g);
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        System.out.println("x: " + x + " y: " + y);
        player.toggleRotation();
        symmetry.toggleRotation();
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Main(GAMENAME));

        app.setDisplayMode(WIDTH, HEIGHT, false);
        app.setTargetFrameRate(30);
        app.setAlwaysRender(true);
        app.setMaximumLogicUpdateInterval(100);
        app.setMinimumLogicUpdateInterval(20);
        app.start();
    }
}
