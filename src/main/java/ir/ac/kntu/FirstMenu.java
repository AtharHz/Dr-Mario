package ir.ac.kntu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FirstMenu {
    public FirstMenu(GraphicsContext gc, AnchorPane pane,Stage stage){
        String dir = System.getProperty("user.dir");
        Image image=new Image(dir.concat("\\src\\main\\resources\\images\\img.png"),980,550,true,true);
        gc.drawImage(image,0,0);
        Button start=new Button("Start");
        Button exit=new Button("Exit");
        AnchorPane.setTopAnchor(start, 80.0);
        AnchorPane.setLeftAnchor(start, 800.0);
        AnchorPane.setRightAnchor(start, 50.0);
        AnchorPane.setBottomAnchor(start, 420.0);
        AnchorPane.setTopAnchor(exit, 180.0);
        AnchorPane.setLeftAnchor(exit, 800.0);
        AnchorPane.setRightAnchor(exit, 50.0);
        AnchorPane.setBottomAnchor(exit, 320.0);
        pane.getChildren().addAll(start,exit);
        start.setOnAction(event -> {
            stage.close();
            Canvas secondCanvas=new Canvas(980,550);
            GraphicsContext gc2= secondCanvas.getGraphicsContext2D();
            AnchorPane secondPane=new AnchorPane(secondCanvas);
            Scene secondScene = new Scene(secondPane);
            Stage newWindow = new Stage();
            new SecondMenu(gc2,secondPane,newWindow);
            newWindow.setTitle("Second menu");
            newWindow.setScene(secondScene);
            newWindow.show();
        });
        exit.setOnAction(actionEvent -> {
            stage.close();
        });
    }
}
