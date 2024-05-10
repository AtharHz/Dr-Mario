package ir.ac.kntu;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameScene {

    private GameState gameState;

    private int virusNumber;

    private int score;

    private ArrayList<Pill> fallenPills =new ArrayList<>();

    private ArrayList<Image> pills=new ArrayList<>();

    private ArrayList<Image> virusImages =new ArrayList<>();

    private ArrayList<Virus> viruses=new ArrayList<>();

    private int timer=500;

    public GameScene(GraphicsContext gc, AnchorPane pane, Stage stage,int y1,int y2,int y3,User user){
        if(y2==2){
            timer=400;
        }else if (y2==3){
            timer=300;
        }
        gameState=GameState.RUNNING;
        Pill pill=new Pill(132.0,102.0, randomPill());
        score=0;
        virusNumber=y1*4+4;
        generateVirus();
        draw(gc,user,y1,y2,pane);
        pill.draw(gc);
        stage.addEventHandler(KeyEvent.KEY_PRESSED, e ->{
            if (e.getCode().equals(KeyCode.RIGHT)){
                if (pill.getX()<190) {
                    pill.setX(pill.getX() + 16.5);
                }
            }
        });
        stage.addEventHandler(KeyEvent.KEY_PRESSED, e ->{
            if (e.getCode().equals(KeyCode.LEFT)){
                if (pill.getX()>66) {
                    pill.setX(pill.getX() - 16.5);
                }
            }
        });
        stage.addEventHandler(KeyEvent.KEY_PRESSED, e ->{
            if (e.getCode().equals(KeyCode.DOWN)){
                if (pill.getY()<418) {
                    pill.setY(pill.getY() + 5);
                }
            }
        });
        new AnimationTimer() {
            long time = System.currentTimeMillis();
            int i=0;
            @Override
            public void handle(long l) {
                if (System.currentTimeMillis() - time >timer) {
                    if (gameState.equals(GameState.RUNNING)) {
                        i++;
                        runningGameScene(gc, user, y1, y2, pane);
                        int flag =isColliding(pill);
                        if (pill.getY() < 418 && flag == 0) {
                            pill.setY(pill.getY() + 5);
                            pill.draw(gc);
                        } else {
                            fallenPills.add(new Pill(pill.getX(), pill.getY(),pill.getImage()));
                            if (pill.getY() < 112.0) {
                                gameState = GameState.FINISHED;
                            }
                            pill.renew(102, 132,randomPill());
                        }
                    } else if (gameState.equals(GameState.FINISHED)) {
                        gameState = GameState.PAUSE;
                        losingScene(gc, user, y1, y2, pane);
                        stage.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
                            if (e.getCode().equals(KeyCode.ENTER)) {
                                settingMenu(stage, user);
                            }
                        });
                    }
                    drawViruses(gc, i);
                    time = System.currentTimeMillis();
                }
            }
        }.start();
    }

    public void draw(GraphicsContext gc,User user,int y1,int y2,AnchorPane pane){
        String dir = System.getProperty("user.dir");
        gc.drawImage(new Image(dir.concat("\\src\\main\\resources\\images\\MainScene2.png"),670,585,true,true),0,0);
        Text level = new Text("LEVEL\n  "+y1),virus=new Text("VIRUS\n  " + virusNumber),speed=new Text("SPEED\n  HI"),scoreText=new Text(),highScore=new Text();
        Font font=Font.font("Comic Sans MS", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 24);
        level.setFont(font);speed.setFont(font);virus.setFont(font);highScore.setFont(font);scoreText.setFont(font);
        highScore.setText("TOP\n  "+user.getHighScore());scoreText.setText("SCORE\n  "+ score);
        if(y1<10){
            level.setText("LEVEL\n  "+0+y1);
        }
        if (y2==1){
            speed.setText("SPEED\n  LOW");
        }else if (y2==2){
            speed.setText("SPEED\n  MED");
        }
        if(virusNumber<10) {
            virus.setText("VIRUS\n  " + 0 + virusNumber);
        }
        AnchorPane.setTopAnchor(highScore, 100.0);AnchorPane.setLeftAnchor(highScore, 70.0);
        AnchorPane.setTopAnchor(scoreText, 180.0);
        AnchorPane.setLeftAnchor(scoreText, 70.0);
        AnchorPane.setTopAnchor(level, 330.0);
        AnchorPane.setLeftAnchor(level, 500.0);
        AnchorPane.setTopAnchor(speed, 400.0);
        AnchorPane.setLeftAnchor(speed, 500.0);
        AnchorPane.setTopAnchor(virus, 480.0);
        AnchorPane.setLeftAnchor(virus, 500.0);
        pane.getChildren().addAll(highScore, scoreText,level,speed,virus);

    }

    public void losingScene(GraphicsContext gc,User user,int y1,int y2,AnchorPane pane){
        draw(gc,user,y1,y2,pane);
        String dir = System.getProperty("user.dir");
        Image doctor=new Image(dir.concat("\\src\\main\\resources\\images\\DoctorLosing.png"),120,120,true,true);
        gc.drawImage(doctor,470,170);
        Image gameOver=new Image(dir.concat("\\src\\main\\resources\\images\\GameOver.png"),140,140,true,true);
        gc.drawImage(gameOver,270,250);
        Text startTest=new Text("Start");
        startTest.setFill(Color.rgb(225,225,225));
        startTest.setFont(Font.font("Comic Sans MS", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 24));
        AnchorPane.setTopAnchor(startTest, 480.0);
        AnchorPane.setLeftAnchor(startTest, 300.0);
        pane.getChildren().addAll(startTest);
    }

    public void runningGameScene(GraphicsContext gc,User user,int y1,int y2,AnchorPane pane){
        draw(gc,user,y1,y2,pane);
        for(int i = 0; i< fallenPills.size(); i++){
            fallenPills.get(i).draw(gc);
        }
        for (int j=0;j<virusNumber;j++){
            viruses.get(j).draw(gc);
        }
//        viruses.get(0).draw(gc);
    }

    public void settingMenu(Stage stage, User user){
        stage.close();
        Canvas canvas2 = new Canvas(548, 500);
        GraphicsContext gc2 = canvas2.getGraphicsContext2D();
        AnchorPane secondPane = new AnchorPane(canvas2);
        Scene thirdScene = new Scene(secondPane);
        Stage newWindow = new Stage();
        new ThirdMenu(gc2, secondPane, newWindow,user);
        newWindow.setTitle("Third menu");
        newWindow.setScene(thirdScene);
        newWindow.show();
    }

    public void drawRedViruses(GraphicsContext gc, int i){
        String dir = System.getProperty("user.dir");
        Image redVirus1=new Image(dir.concat("\\src\\main\\resources\\images\\RedVirus1.png"),60,60,true,true);
        Image redVirus2=new Image(dir.concat("\\src\\main\\resources\\images\\ResVirus2.png"),60,60,true,true);
        Image redVirus3=new Image(dir.concat("\\src\\main\\resources\\images\\RedVirus3.png"),60,60,true,true);
        Image redVirus4=new Image(dir.concat("\\src\\main\\resources\\images\\ResVirus2.png"),60,60,true,true);
        ArrayList<Image> redViruses=new ArrayList<>();
        redViruses.add(redVirus1);
        redViruses.add(redVirus2);
        redViruses.add(redVirus3);
        redViruses.add(redVirus4);
        gc.drawImage(redViruses.get(i%4),120,420);
    }

    public void drawBlueViruses(GraphicsContext gc, int i){
        String dir = System.getProperty("user.dir");
        Image blueVirus1 =new Image(dir.concat("\\src\\main\\resources\\images\\BlueVirus1.png"),60,60,true,true);
        Image blueVirus2 =new Image(dir.concat("\\src\\main\\resources\\images\\BlueVirus2.png"),60,60,true,true);
        Image blueVirus3 =new Image(dir.concat("\\src\\main\\resources\\images\\BlueVirus3.png"),60,60,true,true);
        Image blueVirus4 =new Image(dir.concat("\\src\\main\\resources\\images\\BlueVirus2.png"),60,60,true,true);
        ArrayList<Image> redViruses=new ArrayList<>();
        redViruses.add(blueVirus1);
        redViruses.add(blueVirus2);
        redViruses.add(blueVirus3);
        redViruses.add(blueVirus4);
        gc.drawImage(redViruses.get(i%4),50,410);
    }

    public void drawYellowViruses(GraphicsContext gc, int i){
        String dir = System.getProperty("user.dir");
        Image yellowVirus1 =new Image(dir.concat("\\src\\main\\resources\\images\\YelloVirus1.png"),60,60,true,true);
        Image yellowVirus2 =new Image(dir.concat("\\src\\main\\resources\\images\\YelloVirus2.png"),60,60,true,true);
        Image yellowVirus3 =new Image(dir.concat("\\src\\main\\resources\\images\\YelloVirus3.png"),60,60,true,true);
        Image yellowVirus4 =new Image(dir.concat("\\src\\main\\resources\\images\\YelloVirus2.png"),60,60,true,true);
        ArrayList<Image> redViruses=new ArrayList<>();
        redViruses.add(yellowVirus1);
        redViruses.add(yellowVirus2);
        redViruses.add(yellowVirus3);
        redViruses.add(yellowVirus4);
        gc.drawImage(redViruses.get(i%4),90,340);
    }

    public void drawViruses(GraphicsContext gc,int i){
        drawYellowViruses(gc,i);
        drawBlueViruses(gc,i);
        drawRedViruses(gc,i);
    }

    public Image randomPill(){
        addAllPills();
        Random random=new Random();
        int ran=random.nextInt(6);
//        System.out.println("aa"+ran);
        return pills.get(ran);
    }

    public void addAllPills(){
        String dir = System.getProperty("user.dir");
        Image rPill=new Image(dir.concat("\\src\\main\\resources\\images\\RPill.png"),36,16,true,true);
        Image ybPill=new Image(dir.concat("\\src\\main\\resources\\images\\YBPill.png"),36,16,true,true);
        Image rbPill=new Image(dir.concat("\\src\\main\\resources\\images\\RBPill.png"),36,16,true,true);
        Image yPill=new Image(dir.concat("\\src\\main\\resources\\images\\YPill.png"),36,16,true,true);
        Image bPill=new Image(dir.concat("\\src\\main\\resources\\images\\BPill.png"),36,16,true,true);
        Image ryPill=new Image(dir.concat("\\src\\main\\resources\\images\\RYPill.png"),36,16,true,true);
        pills.add(rbPill);
        pills.add(rPill);
        pills.add(ybPill);
        pills.add(yPill);
        pills.add(bPill);
        pills.add(ryPill);
    }

    public void addAllViruses(){
        String dir = System.getProperty("user.dir");
        Image blueVirus=new Image(dir.concat("\\src\\main\\resources\\images\\BlueVirus.png"),28,14,true,true);
        Image redVirus=new Image(dir.concat("\\src\\main\\resources\\images\\RedVirus.png"),28,14,true,true);
        Image yellowVirus=new Image(dir.concat("\\src\\main\\resources\\images\\YellowVirus.png"),28,14,true,true);
        virusImages.add(blueVirus);
        virusImages.add(redVirus);
        virusImages.add(yellowVirus);
    }

    public void generateVirus(){
        addAllViruses();
        Random random=new Random();
        viruses.add(new Virus(random.nextInt(10)*(16.5)+252,random.nextInt(20)*10+307,virusImages.get(0)));
        viruses.add(new Virus(random.nextInt(10)*(16.5)+252,random.nextInt(20)*10+307,virusImages.get(1)));
        viruses.add(new Virus(random.nextInt(10)*(16.5)+252,random.nextInt(20)*10+307,virusImages.get(2)));
        for(int i=0;i<virusNumber-3;i++){
            viruses.add(new Virus(random.nextInt(10)*(16.5)+252,random.nextInt(20)*10+307,virusImages.get(random.nextInt(3))));
//            System.out.println("ax"+viruses.get(i).getX());
//            System.out.println("by"+viruses.get(i).getY());
        }
//        viruses.add(new Virus(252,317,virusImages.get(random.nextInt(3))));
//        System.out.println("done");
    }

    public int isColliding(Pill pill){
        int flag=0;
        for (int j = 0; j < fallenPills.size(); j++) {
            if ((fallenPills.get(j).getY() - 15) == pill.getY() && (fallenPills.get(j).getX() < (pill.getX() + 32) &&
                    fallenPills.get(j).getX()+32>pill.getX())) {
                flag = 1;
                break;
            }
        }
        for (int j = 0; j < viruses.size(); j++) {
            if ((viruses.get(j).getY()-(pill.getY()+85)<20)&&(viruses.get(j).getX()<(pill.getX()+16+190)&&
                    ((viruses.get(j).getX()+16)>(pill.getX()+190)))){
                flag = 1;
                break;
            }
        }
        return flag;
    }
}