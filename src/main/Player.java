package main;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Player {
    private Vector2f pos;
    private Shape shape;
    private Image image;
    private int size;
    private boolean rotate;

    public Player(Vector2f pos, Shape shape, int size) {
        this.pos = pos;
        this.shape = shape;
        this.size = size;
    }

    public Player(int x, int y, Shape shape, int size) {
        this.pos = new Vector2f(x, y);
        this.shape = shape;
        this.size = size;
    }

    public boolean collides(Shape other) {
        return this.shape.intersects(other);
    }

    public void toggleRotate(boolean) {

    }

    public void update(int x, int y) {
        pos.set(x, y);
        this.shape.setLocation(pos);
        this.shape.setCenterX(pos.getX()+size/2);
        this.shape.setCenterY(pos.getY()+size/2);
    }

    public void render() {
        this.image.draw(pos.getX(), pos.getY(), size, size);

        //rotation
        if(rotate) {
            this.image.setCenterOfRotation(size / 2, size / 2);
            this.image.rotate(5);
        }
    }
}
