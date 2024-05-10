package ir.ac.kntu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class SecondMenu {
    public SecondMenu(GraphicsContext gc, AnchorPane pane, Stage stage){
        String dir = System.getProperty("user.dir");
        Image image=new Image(dir.concat("\\src\\main\\resources\\images\\SecondMenu.png"),980,550,true,true);
        gc.drawImage(image,0,0);
        Button addPlayer=new Button("Add Player");
        Button choosePlayer=new Button("Choose Player");
        AnchorPane.setTopAnchor(addPlayer, 60.0);
        AnchorPane.setLeftAnchor(addPlayer, 40.0);
        AnchorPane.setRightAnchor(addPlayer, 820.0);
        AnchorPane.setBottomAnchor(addPlayer, 440.0);
        AnchorPane.setTopAnchor(choosePlayer, 120.0);
        AnchorPane.setLeftAnchor(choosePlayer, 40.0);
        AnchorPane.setRightAnchor(choosePlayer, 820.0);
        AnchorPane.setBottomAnchor(choosePlayer, 380.0);
        pane.getChildren().addAll(addPlayer,choosePlayer);
        ArrayList<User> users=new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream("Players.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            String str= (String) ois.readObject();
            str=str.replaceAll("]","");
            str=str.replaceAll("\\[","");
            String[] strings=str.split(",");
            for (int i=0;i<strings.length;i++){
                String[] parts=strings[i].replace(" ","").split("\\;");
                users.add(new User(parts[0],Integer.valueOf(parts[1]),Integer.valueOf(parts[2])));
            }
        } catch (FileNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addPlayer.setOnAction(event -> {
            Label nameLabel=new Label("Name :");
            TextField name=new TextField();
            Button save=new Button("Save");
            AnchorPane.setTopAnchor(nameLabel, 60.0);
            AnchorPane.setLeftAnchor(nameLabel, 600.0);
            AnchorPane.setRightAnchor(nameLabel, 80.0);
            AnchorPane.setBottomAnchor(nameLabel, 440.0);
            AnchorPane.setTopAnchor(name, 60.0);
            AnchorPane.setLeftAnchor(name, 660.0);
            AnchorPane.setRightAnchor(name, 30.0);
            AnchorPane.setBottomAnchor(name, 450.0);
            AnchorPane.setTopAnchor(save, 110.0);
            AnchorPane.setLeftAnchor(save, 650.0);
            AnchorPane.setRightAnchor(save, 240.0);
            AnchorPane.setBottomAnchor(save, 410.0);
            pane.getChildren().addAll(nameLabel,name,save);
            save.setOnAction(eventAction ->{
                    String playerName=name.getText();
                User newUser=new User(playerName,1,0);
                try {
                    FileOutputStream fop = new FileOutputStream("Players.txt");
                    ObjectOutputStream oos = new ObjectOutputStream(fop);
                    users.add(newUser);
                    oos.writeObject(String.valueOf(users));
                    nextMenu(stage,newUser);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
        choosePlayer.setOnAction(actionEvent -> {
            VBox vBox=new VBox();
            TableView<User> table = new TableView<>();
            ObservableList<User> data = FXCollections.observableArrayList(users);
            TableColumn nameCol = new TableColumn("First Name");
            nameCol.setMinWidth(100);
            nameCol.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
            TableColumn numberCol = new TableColumn("Number of games");
            numberCol.setMinWidth(100);
            numberCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("gamesNumber"));
            TableColumn highScoreCol = new TableColumn("High Score");
            highScoreCol.setMinWidth(200);
            highScoreCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("highScore"));
            table.setItems(data);
            table.getColumns().addAll(nameCol, numberCol, highScoreCol);
            vBox.setSpacing(5);
            vBox.getChildren().addAll(table);
            AnchorPane.setTopAnchor(vBox, 60.0);
            AnchorPane.setLeftAnchor(vBox, 440.0);
            AnchorPane.setRightAnchor(vBox, 40.0);
            AnchorPane.setBottomAnchor(vBox, 100.0);
            Button submit=new Button("Submit");
            AnchorPane.setTopAnchor(submit, 460.0);
            AnchorPane.setLeftAnchor(submit, 440.0);
            AnchorPane.setRightAnchor(submit, 470.0);
            AnchorPane.setBottomAnchor(submit, 50.0);
            pane.getChildren().addAll(vBox,submit);
            submit.setOnAction(actionEvent1 -> {
                User chosen=table.getSelectionModel().getSelectedItem();
                for(int j=0;j<users.size();j++){
                    if(users.get(j).equals(chosen)){
                        users.get(j).setGamesNumber(chosen.getGamesNumber()+1);
                        try {
                            FileOutputStream fop = new FileOutputStream("Players.txt");
                            ObjectOutputStream oos = new ObjectOutputStream(fop);
                            oos.writeObject(String.valueOf(users));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                nextMenu(stage,chosen);
            });
        });
    }

    public void nextMenu(Stage stage,User user){
        stage.close();
        Canvas thirdCanvas = new Canvas(548, 500);
        GraphicsContext gc3 = thirdCanvas.getGraphicsContext2D();
        AnchorPane secondPane = new AnchorPane(thirdCanvas);
        Scene thirdScene = new Scene(secondPane);
        Stage newWindow = new Stage();
        new ThirdMenu(gc3, secondPane, newWindow,user);
        newWindow.setTitle("Third menu");
        newWindow.setScene(thirdScene);
        newWindow.show();
    }
}
