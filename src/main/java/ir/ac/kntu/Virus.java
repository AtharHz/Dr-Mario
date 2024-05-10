package ir.ac.kntu;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Virus {

    private double x;

    private double y;

    private Image image;

    public Virus(double x, double y, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void draw(GraphicsContext gc){
        gc.drawImage(image,x,y);
    }
}
