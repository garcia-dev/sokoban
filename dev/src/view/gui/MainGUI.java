package view.gui;

/**
 * MainGUI's class
 * <p>
 * The class MainGUI is used to print a Graphical User Interface using JavaFX.
 * </p>
 *
 * @author GARCIA Romain, DE OLIVEIRA Dylan, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-03-21
 */

import controller.GameController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Board;
import model.LevelLoader;

import static model.Direction.*;

public class MainGUI extends Application {

	public Group groupe;

	private Group groupeMenu;

	private Group groupeNextLevel;

	private Scene scene;

	private Scene sceneMenu;

	private Scene sceneNextLevel;


	public MainGUI() {
		this.groupe = new Group();
		this.groupeMenu = new Group();
		this.groupeNextLevel = new Group();
		this.scene = new Scene(groupe, 800, 600, Color.BEIGE);
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

		Canvas canvasMenu = new Canvas(800, 600);
		GraphicsContext gcMenu = canvasMenu.getGraphicsContext2D();
//        Image logoMenu = new Image(getClass().getResource("logo.png").toString());
//        gcMenu.drawImage(logoMenu, 230, 25);
		this.groupeMenu.getChildren().add(canvasMenu);

		groupeMenu.getChildren().add(startBtn);


		BoardGUI boardGUI = new BoardGUI(new Board(), this);
		boardGUI.getBoard().setLevel(LevelLoader.loadFile("level1", boardGUI.getBoard()));
		boardGUI.update(this);
		GameController gameController = new GameController(boardGUI.getBoard());

		boardGUI.getBoard().addObserver(boardGUI);

		primaryStage.setScene(sceneMenu);
		primaryStage.setTitle("Sokoban");

		scene.setOnKeyPressed(event -> {
			switch (event.getCode()) {
				case UP:
					gameController.move(boardGUI.getBoard().getLevel().getPlayerCase().getPawn(), UP);
					break;
				case DOWN:
					gameController.move(boardGUI.getBoard().getLevel().getPlayerCase().getPawn(), DOWN);
					break;
				case LEFT:
					gameController.move(boardGUI.getBoard().getLevel().getPlayerCase().getPawn(), LEFT);
					break;
				case RIGHT:
					gameController.move(boardGUI.getBoard().getLevel().getPlayerCase().getPawn(), RIGHT);
					break;
			}

			if (!boardGUI.getBoard().getLevel().isFinished()) {
				primaryStage.setScene(scene);
			} else if (boardGUI.getBoard().getLevel().isFinished()) {
				primaryStage.setScene(sceneNextLevel);
			}

		});

		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}

}
