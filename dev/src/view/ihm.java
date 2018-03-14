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

    @Override
    public void start(Stage primaryStage) throws Exception {

        Plateau plateau = new Plateau(new Board());
        plateau.getBoard().setLevel(LevelLoader.loadFile("level1",plateau.getBoard()));
        GameController gameController = new GameController(plateau.getBoard());

        plateau.getBoard().addObserver(plateau);

        Group groupe = new Group();
        Scene scene = new Scene(groupe, 800, 600, Color.BEIGE);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sokoban");
        scene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case UP:
                        gameController.move(plateau.getBoard().getLevel().getPlayerCase().getPawn(), Direction.UP);
                        break;
                    case DOWN:
                        gameController.move(plateau.getBoard().getLevel().getPlayerCase().getPawn(), Direction.RIGHT);
                        break;
                    case LEFT:
                        gameController.move(plateau.getBoard().getLevel().getPlayerCase().getPawn(), Direction.DOWN);
                        break;
                    case RIGHT:
                        gameController.move(plateau.getBoard().getLevel().getPlayerCase().getPawn(), Direction.LEFT);
                        break;
                }
            });
            primaryStage.show();
        }



    public static void main(String[] args) {
        launch(args);
    }

}
