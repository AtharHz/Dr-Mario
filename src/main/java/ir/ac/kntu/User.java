package ir.ac.kntu;

import java.io.Serializable;

public class User implements Serializable {

    private String name;

    private int gamesNumber;

    private int highScore;

    public User(String name, int gamesNumber, int highScore) {
        this.name = name;
        this.gamesNumber = gamesNumber;
        this.highScore = highScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGamesNumber() {
        return gamesNumber;
    }

    public void setGamesNumber(int gamesNumber) {
        this.gamesNumber = gamesNumber;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void getValues(){

    }

    @Override
    public String toString() {
        return name+";" +gamesNumber+ ";"+ highScore;
    }
}
