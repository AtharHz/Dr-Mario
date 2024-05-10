package ir.ac.kntu;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Pill {

    private double x;

    private double y;

    private Image image;

    public Pill(double x, double y,Image image) {
        this.image=image;
        this.x = x;
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void draw(GraphicsContext gc){
        gc.drawImage(image,190+x,85+y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void renew(double y, double x, Image image) {
        this.y = y;
        this.x=x;
        this.image=image;
    }

}
