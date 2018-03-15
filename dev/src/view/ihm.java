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

import static model.Direction.*;

public class ihm extends Application{

    public Group groupe;

    private Scene scene;

    public ihm(){
        this.groupe = new Group();
        this.scene =  new Scene(groupe, 800, 600, Color.BEIGE);
    }

    @Override
    public void start(Stage primaryStage) {

        Plateau plateau = new Plateau(new Board(),this);
        plateau.getBoard().setLevel(LevelLoader.loadFile("level1",plateau.getBoard()));
        plateau.update(this);
        GameController gameController = new GameController(plateau.getBoard());

        plateau.getBoard().addObserver(plateau);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Sokoban");

        scene.setOnKeyPressed(event ->{
            switch (event.getCode()) {
                case UP:
                    gameController.move(plateau.getBoard().getLevel().getPlayerCase().getPawn(), UP);
                    break;
                case DOWN:
                    gameController.move(plateau.getBoard().getLevel().getPlayerCase().getPawn(), DOWN);
                    break;
                case LEFT:
                    gameController.move(plateau.getBoard().getLevel().getPlayerCase().getPawn(), LEFT);
                    break;
                case RIGHT:
                    gameController.move(plateau.getBoard().getLevel().getPlayerCase().getPawn(), RIGHT);
                    break;
            }
        });

        primaryStage.show();
        }



    public static void main(String[] args) {
        launch(args);
    }

}
