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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import model.*;
import controller.GameController;

import static model.Direction.*;

public class ihm extends Application{

    public Group groupe;

    private Group groupeMenu;

    private Group groupeNextLevel;

    private Scene scene;

    private Scene sceneMenu;

    private Scene sceneNextLevel;


    public ihm(){
        this.groupe = new Group();
        this.groupeMenu = new Group();
        this.groupeNextLevel = new Group();
        this.scene =  new Scene(groupe, 800, 600, Color.BEIGE);
        this.sceneMenu = new Scene(groupeMenu, 800, 600, Color.BEIGE);
        this.sceneNextLevel = new Scene(groupeNextLevel, 300, 300, Color.GRAY);
    }

    @Override
    public void start(Stage primaryStage) {

        Button startBtn = new Button();
        startBtn.setMinWidth(800);
        startBtn.setLayoutX(0);
        startBtn.setLayoutY(500);
        startBtn.setText("Start");
        startBtn.setOnAction(event -> primaryStage.setScene(scene));

        Canvas canvasMenu = new Canvas(800,600);
        GraphicsContext gcMenu = canvasMenu.getGraphicsContext2D();
        Image logoMenu = new Image(getClass().getResource("logo.png").toString());
        gcMenu.drawImage(logoMenu, 230, 25);
        this.groupeMenu.getChildren().add(canvasMenu);

        groupeMenu.getChildren().add(startBtn);


        Plateau plateau = new Plateau(new Board(),this);
        plateau.getBoard().setLevel(LevelLoader.loadFile("level1",plateau.getBoard()));
        plateau.update(this);
        GameController gameController = new GameController(plateau.getBoard());

        plateau.getBoard().addObserver(plateau);

        primaryStage.setScene(sceneMenu);
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

            if (!plateau.getBoard().getLevel().isFinished()) {
                primaryStage.setScene(scene);
            } else if (plateau.getBoard().getLevel().isFinished()){
                primaryStage.setScene(sceneNextLevel);
            }

        });

        primaryStage.show();
        }



    public static void main(String[] args) {
        launch(args);
    }

}
