package test;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;


public class Test extends BasicGame {

    private static final String GAMENAME = "The One";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private Image character = null;
    private Image cursor = null;
    private Shape player = null;
    private Shape symmetry = null;
    private Vector2f p_pos = null;
    private Vector2f s_pos = null;
    private int p_size;
    private boolean collides, rotate = true;

    /**
     * Create a new basic game
     *
     * @param title The title for the game
     */
    public Test(String title) {
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
        p_pos = new Vector2f(0, 0);
        s_pos = new Vector2f(WIDTH, HEIGHT);
        p_size = 50;

        player = new Rectangle(p_pos.getX(), p_pos.getY(), p_size, p_size);
        symmetry = new Rectangle(s_pos.getX(), s_pos.getY(), p_size, p_size);
        character = new Image("assets/images/tungsten_block.png");
    }

    @Override
    public void update(GameContainer container, int delta) {
        //called every cycle (~60 fps commonly), must be similar to render speed cause render when no change is useless
        int x = Mouse.getX()-p_size/2;
        int y = Mouse.getY()-p_size/2;  //-Mouse... + window height ti go to normal
        p_pos.set(-x+WIDTH-p_size, y);
        player.setLocation(p_pos);

        s_pos.set(x, -y+HEIGHT-p_size);
        symmetry.setLocation(s_pos);

        collides = player.intersects(symmetry);

        if(rotate) {
            player.transform(new Transform().createRotateTransform(5, p_size/2, p_size/2));
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) {
        g.drawString("hello there!!", 100, 50);
        int x = Mouse.getX()-p_size/2;
        int y = Mouse.getY()-p_size/2;  //-Mouse... + window height ti go to normal
        if (collides) {
            character.drawFlash(p_pos.getX()+1, p_pos.getY()+1, p_size+2, p_size+2);
        }
        character.draw(p_pos.getX(), p_pos.getY(), p_size, p_size);
        character.draw(s_pos.getX(), s_pos.getY(), p_size, p_size);

        g.draw(player);
        //rotation
        if(rotate) {
            character.setCenterOfRotation(p_size / 2, p_size / 2);
            character.rotate(5);
        }
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        super.mouseClicked(button, x, y, clickCount);
        System.out.println("x: " + Mouse.getX() + " y: " + Mouse.getY());
        rotate = !rotate;
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Test(GAMENAME));

        app.setDisplayMode(WIDTH, HEIGHT, false);

        app.setAlwaysRender(true);
        app.start();
    }
}
