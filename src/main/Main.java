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
    private Shape player = null;
    private Shape symmetry = null;
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
        s_pos = new Vector2f(0, 0);
        p_pos = new Vector2f(WIDTH, HEIGHT);
        p_size = 50;

        player = new Rectangle(p_pos.getX(), p_pos.getY(), p_size, p_size);
        symmetry = new Rectangle(s_pos.getX(), s_pos.getY(), p_size, p_size);
        character = new Image("assets/images/tungsten_block.png");
    }

    @Override
    public void update(GameContainer container, int delta) {
        Input input = container.getInput();
        int x = input.getMouseX()-p_size/2;
        int y = input.getMouseY()-p_size/2;  //-Mouse... + window height ti go to normal

        p_pos.set(x, y);
        player.setLocation(p_pos);
        player.setCenterX(p_pos.getX()+p_size/2);
        player.setCenterY(p_pos.getY()+p_size/2);

        s_pos.set(-x+WIDTH-p_size, -y+HEIGHT-p_size);
        symmetry.setLocation(s_pos);
        symmetry.setCenterX(s_pos.getX()+p_size/2);
        symmetry.setCenterY(s_pos.getY()+p_size/2);

        if(rotate) {
            player = player.transform(Transform.createRotateTransform((float) Math.toRadians(5), player.getCenterX(), player.getCenterY()));
            symmetry = symmetry.transform(Transform.createRotateTransform((float) Math.toRadians(5), symmetry.getCenterX(), symmetry.getCenterY()));
        }

        collides = player.intersects(symmetry);
    }

    @Override
    public void render(GameContainer container, Graphics g) {
        if (collides) {
            g.setColor(Color.red);
            character.drawFlash(s_pos.getX()-1, s_pos.getY()-1, p_size+2, p_size+2);
            g.setColor(Color.white);
            collides = false;
        }
        character.draw(p_pos.getX(), p_pos.getY(), p_size, p_size);
        character.draw(s_pos.getX(), s_pos.getY(), p_size, p_size);

        g.setColor(Color.white);
        g.draw(player);
        g.setColor(Color.green);
        g.draw(symmetry);
        //rotation
        if(rotate) {
            character.setCenterOfRotation(p_size / 2, p_size / 2);
            character.rotate(5);
        }
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        System.out.println("x: " + x + " y: " + y);
        System.out.println("x: " + p_pos.getX() + " y: " + p_pos.getY());
        rotate = !rotate;
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
