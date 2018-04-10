package view.gui;

/**
 * ScoreGUI's class
 * <p>
 * The class ScoreGUI is used to print a gui's version of the actual score using JavaFX.
 * </p>
 *
 * @author DE OLIVEIRA Dylan, GARCIA Romain, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-04-10
 */

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.general.Board;
import utility.Observer;


public class ScoreGUI implements Observer {

    private Board board;

    private Pane panel;

    protected int timer;

    public ScoreGUI(Board board){
        this.board = board;
        this.panel = new Pane();
        this.timer = 0;
    }

    public Board getBoard() {
        return board;
    }

    public Pane getPanel() {
        return panel;
    }

    public int getTimer() {
        return timer;
    }

    @Override
    public void update() {
        panel.getChildren().clear();
        panel.setStyle("-fx-background-color: #ffe082;");
        panel.setPrefSize(200,600);
        panel.setLayoutX(800);
        panel.setLayoutY(0);

        ImageView logoMenu = new ImageView(new Image(getClass().getResource("/resources/images/logo.png").toString()));
        logoMenu.setFitWidth(200);
        logoMenu.setFitHeight(45);
        logoMenu.setLayoutY(25);

        Text stepCounter = new Text();
        stepCounter.setLayoutX(55);
        stepCounter.setLayoutY(100);
        stepCounter.setText("Nombre de pas :\n         " + String.valueOf(board.getLevel().getScore().getStepCount()));

        Text timeCounter = new Text();
        timeCounter.setLayoutX(75);
        timeCounter.setLayoutY(250);
        timeCounter.setText("Temps :\n     " + timer  + "s");

        Text quitGame = new Text();
        quitGame.setText("   Appuyer sur ECHAP pour\nretourner au menu principal");
        quitGame.setLayoutX(25);
        quitGame.setLayoutY(550);
        panel.getChildren().addAll(stepCounter,quitGame, timeCounter, logoMenu);

    }
}
