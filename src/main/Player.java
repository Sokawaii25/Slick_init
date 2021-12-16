package main;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

public class Player {
    private Vector2f pos;

    private Shape shape;
    private Image image;
    private int size;
    private boolean rotation;

    public Player(Vector2f pos, Shape shape, Image image, int size) {
        this.pos = pos;
        this.shape = shape;
        this.image = image;
        this.size = size;
    }

    public Player(int x, int y, Shape shape, Image image, int size) {
        this.pos = new Vector2f(x, y);
        this.shape = shape;
        this.image = image;
        this.size = size;
    }


    public Shape getShape() {
        return this.shape;
    }

    public Image getImage() {
        return this.image;
    }

    public boolean collides(Shape other) {
        return this.shape.intersects(other);
    }

    public boolean collides(Player other) {
        return this.shape.intersects(other.getShape());
    }

    public void toggleRotation() {
        this.rotation = !this.rotation;
    }

    public void update(int x, int y) {
        pos.set(x-size/2, y-size/2);
        this.shape.setLocation(pos);
        this.shape.setCenterX(pos.getX()+size/2);
        this.shape.setCenterY(pos.getY()+size/2);

        if(rotation) {
            this.shape = this.shape.transform(Transform.createRotateTransform((float) Math.toRadians(5), this.shape.getCenterX(), this.shape.getCenterY()));
            this.shape = this.shape.transform(Transform.createRotateTransform((float) Math.toRadians(5), this.shape.getCenterX(), this.shape.getCenterY()));
        }
    }

    public void hits(Player other) {
        other.isHit();
    }

    public void isHit() {
        this.image.drawFlash(pos.getX()-2, pos.getY()-2, size+4, size+4);
    }

    public void render(Graphics g) {
        this.image.draw(pos.getX(), pos.getY(), size, size);

        g.draw(this.shape);

        //rotation
        if(this.rotation) {
            this.image.setCenterOfRotation(size / 2, size / 2);
            this.image.rotate(5);
        }
    }
}
