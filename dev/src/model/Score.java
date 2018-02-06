package model;

public class Score {

    private String playerName;
    private int score;
    private int stepCount;
    private double gameLength;
    private int triesCount;

    public Score(){
        this.playerName = "";
        this.score = 0;
        this.stepCount = 0;
        this.gameLength = 0;
        this.triesCount = 0;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public int getStepCount() {
        return stepCount;
    }

    public double getGameLength() {
        return gameLength;
    }

    public int getTriesCount() {
        return triesCount;
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }

}
