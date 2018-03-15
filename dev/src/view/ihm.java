package view;

/**
 * ihm's class
 * <p>
 * The class ihm is used to print the Board's level with JavaFX.
 * </p>
 *
 * @author GARCIA Romain, DE OLIVEIRA Dylan, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-02-04
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import model.*;
import controller.GameController;

public class ihm extends Application{

    public Group groupe;

    public Scene scene;

    public ihm(){
        this.groupe = new Group();
        this.scene =  new Scene(groupe, 800, 600, Color.BEIGE);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Plateau plateau = new Plateau(new Board(),this);
        plateau.getBoard().setLevel(LevelLoader.loadFile("level1",plateau.getBoard()));
        plateau.update(this);
        GameController gameController = new GameController(plateau.getBoard());

        plateau.getBoard().addObserver(plateau);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Sokoban");

        primaryStage.show();
        }



    public static void main(String[] args) {
        launch(args);
    }

}
