package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Score extends AbstractModel {
    private String playerName;
    private int score;
    private int stepCount;
    private double gameLength;
    private int triesCount;
    private Duration duration;

    public Score(){
        playerName = "";
        score = 0;
        stepCount = 0;
        gameLength = 0;
        triesCount = 0;
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

    public void addStepCount(){
        stepCount++;
    }

    public Duration getDuration(){
    	return duration;
    }

    public void setDuration(LocalDateTime startDate, LocalDateTime endDate){
    	duration = Duration.between(startDate, endDate);
    }
}
