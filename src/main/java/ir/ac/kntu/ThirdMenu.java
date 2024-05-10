package ir.ac.kntu;

import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThirdMenu {
    public ThirdMenu(GraphicsContext gc, AnchorPane pane, Stage stage,User user) {
        AtomicInteger x= new AtomicInteger(1);
        AtomicInteger y1= new AtomicInteger(0);
        AtomicInteger y2= new AtomicInteger(1);
        AtomicInteger y3= new AtomicInteger(1);
        Text level=new Text();
        level.setFill(Color.rgb(225,225,225));
        level.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        AnchorPane.setTopAnchor(level, 140.0);
        AnchorPane.setLeftAnchor(level, 390.0);
        pane.getChildren().addAll(level);
        draw(gc,x,y1,y2,y3,pane,level);
        stage.addEventHandler(KeyEvent.KEY_PRESSED, e ->{
            if (e.getCode().equals(KeyCode.UP)){
                if(x.get() !=1){
                    x.set(x.get() - 1);
                }
                draw(gc,x,y1,y2,y3,pane,level);
            }
        });
        stage.addEventHandler(KeyEvent.KEY_PRESSED, e ->{
            if (e.getCode().equals(KeyCode.DOWN)){
                if(x.get() !=3){
                    x.set(x.get() +1);
                }
                draw(gc,x,y1,y2,y3,pane,level);
            }
        });
        stage.addEventHandler(KeyEvent.KEY_PRESSED, e ->{
            if (e.getCode().equals(KeyCode.LEFT)){
                if(x.get()==1 &&y1.get() !=0){
                    y1.set(y1.get() - 1);
                }else if(x.get()==2 &&y2.get() !=1){
                    y2.set(y2.get() - 1);
                }else if(x.get()==3 &&y3.get() !=1){
                    y3.set(y3.get() - 1);
                }
                draw(gc,x,y1,y2,y3,pane,level);
            }
        });
        stage.addEventHandler(KeyEvent.KEY_PRESSED, e ->{
            if (e.getCode().equals(KeyCode.RIGHT)){
                if (x.get()==2 && y2.get() != 3) {
                    y2.set(y2.get() + 1);
                }else if (x.get()==3 && y3.get() != 3) {
                    y3.set(y3.get() + 1);
                }else if (x.get()==1 && y1.get() != 20) {
                    y1.set(y1.get() + 1);
                }
                draw(gc,x,y1,y2,y3,pane,level);
            }
        });
        stage.addEventHandler(KeyEvent.KEY_PRESSED, e ->{
            if (e.getCode().equals(KeyCode.ENTER)){
                gameScene(gc,stage,user,y1.get(),y2.get(),y3.get());
            }
        });
    }

    public void gameScene(GraphicsContext gc,Stage stage,User user,int y1,int y2,int y3){
        stage.close();
        Canvas gameCanvas = new Canvas(670, 585);
        GraphicsContext gc2 = gameCanvas.getGraphicsContext2D();
        AnchorPane gamePane = new AnchorPane(gameCanvas);
        Scene thirdScene = new Scene(gamePane);
        Stage newWindow = new Stage();
        new GameScene(gc2, gamePane, newWindow,y1,y2,y3,user);
        newWindow.setTitle("Game");
        newWindow.setScene(thirdScene);
        newWindow.show();
    }

    public void draw(GraphicsContext gc,AtomicInteger x,AtomicInteger y1,AtomicInteger y2,AtomicInteger y3,AnchorPane pane,Text level){
        level.setText(""+y1.get());
        String dir = System.getProperty("user.dir");
        gc.drawImage(new Image(dir.concat("\\src\\main\\resources\\images\\Background.png"),548, 500,true,true),0,0);
        gc.drawImage(new Image(dir.concat("\\src\\main\\resources\\images\\Rectangle.png"),470, 400,true,true),90,50);
        gc.drawImage(new Image(dir.concat("\\src\\main\\resources\\images\\1PlayerGame.png"),220, 70,true,true),150,60);
        gc.drawImage(new Image(dir.concat("\\src\\main\\resources\\images\\1P.png"),35, 35,true,true),110,155);
        gc.drawImage(new Image(dir.concat("\\src\\main\\resources\\images\\SelectionFlag.png"), 30, 15, true, true),145+((8.5)*(y1.get())),150);
        gc.drawImage(new Image(dir.concat("\\src\\main\\resources\\images\\axis.png"),200, 60,true,true),140,155);
        gc.drawImage(new Image(dir.concat("\\src\\main\\resources\\images\\numberBox.png"),90, 90,true,true),350,120);
        gc.drawImage(new Image(dir.concat("\\src\\main\\resources\\images\\Low.png"),60, 40,true,true),150,260);
        gc.drawImage(new Image(dir.concat("\\src\\main\\resources\\images\\Med.png"),70, 50,true,true),250,265);
        gc.drawImage(new Image(dir.concat("\\src\\main\\resources\\images\\Hi.png"),50, 30,true,true),350,264);
        drawX(gc,x);
        drawY(gc,y1,y2,y3);
    }

    public void drawX(GraphicsContext gc,AtomicInteger x){
        String dir = System.getProperty("user.dir");
        if(x.get()==1) {
            gc.drawImage(new Image(dir.concat("\\src\\main\\resources\\images\\Level.png"), 180, 60, true, true), 115, 110);
        }else {
            gc.drawImage(new Image(dir.concat("\\src\\main\\resources\\images\\virusLevel.png"), 180, 60, true, true), 115, 110);
        }
        if(x.get()==2) {
            gc.drawImage(new Image(dir.concat("\\src\\main\\resources\\images\\Speed2.png"), 100, 40, true, true), 110, 200);
        }else {
            gc.drawImage( new Image(dir.concat("\\src\\main\\resources\\images\\Speed.png"), 100, 40, true, true), 110, 200);
        }
        if(x.get()==3) {
            gc.drawImage( new Image(dir.concat("\\src\\main\\resources\\images\\MusicType2.png"), 180, 70, true, true), 110, 320);
        }else {
            gc.drawImage( new Image(dir.concat("\\src\\main\\resources\\images\\Music.png"), 180, 70, true, true), 110, 320);
        }
    }

    public void drawY(GraphicsContext gc,AtomicInteger y1,AtomicInteger y2,AtomicInteger y3){
        String dir = System.getProperty("user.dir");
        Image selector=new Image(dir.concat("\\src\\main\\resources\\images\\SelectionFlag.png"), 50, 20, true, true);
        if(y2.get()==1){
            gc.drawImage(selector,160,240);
        }else if(y2.get()==2){
            gc.drawImage(selector,260,240);
        }else if(y2.get()==3){
            gc.drawImage(selector,350,240);
        }
        if (y3.get() == 1) {
            gc.drawImage(new Image(dir.concat("\\src\\main\\resources\\images\\Fever2.png"),100, 30,true,true),120,370);
        }else {
            gc.drawImage(new Image(dir.concat("\\src\\main\\resources\\images\\Fever.png"), 100, 30, true, true), 120, 370);
        }
        if (y3.get()==2){
            gc.drawImage(new Image(dir.concat("\\src\\main\\resources\\images\\Chill2.png"), 100, 30, true, true), 230, 370);
        }else {
            gc.drawImage(new Image(dir.concat("\\src\\main\\resources\\images\\Chill.png"), 100, 30, true, true), 230, 370);
        }
        if (y3.get()==3){
            gc.drawImage(new Image(dir.concat("\\src\\main\\resources\\images\\Off2.png"), 105, 35, true, true), 355, 365);
        }else {
            gc.drawImage(new Image(dir.concat("\\src\\main\\resources\\images\\Off.png"), 105, 35, true, true), 355, 365);
        }
    }
}
