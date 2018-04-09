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
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Board;
import model.LevelLoader;

import static model.Direction.*;

public class MainGUI extends Application {

	private static final ObservableList levels = FXCollections.observableArrayList();

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
		this.scene = new Scene(groupe, 1000, 600, Color.BEIGE);
		this.sceneMenu = new Scene(groupeMenu, 800, 600, Color.BEIGE);
		this.sceneNextLevel = new Scene(groupeNextLevel, 300, 300, Color.GRAY);
	}

	@Override
	public void start(Stage primaryStage) {

		Boolean quitGame = Boolean.FALSE;

		BoardGUI boardGUI = new BoardGUI(new Board(), this);

		GameController gameController = new GameController(boardGUI.getBoard());

		boardGUI.getBoard().addObserver(boardGUI);

		levels.addAll("level1","level2","level3","level4");

		ChoiceBox listLevels = new ChoiceBox(levels);
		listLevels.getSelectionModel().selectFirst();
		listLevels.setLayoutX(330);
		listLevels.setLayoutY(450);
		listLevels.setPrefSize(150,25);

		Button menuBtn = new Button();
		menuBtn.setMinWidth(300);
		menuBtn.setLayoutX(0);
		menuBtn.setLayoutY(200);
		menuBtn.setText("Retour au menu principal");
		menuBtn.setOnAction(event -> primaryStage.setScene(sceneMenu));

		Canvas sideCanvas = new Canvas(200,600);
		GraphicsContext gcSide = sideCanvas.getGraphicsContext2D();
		gcSide.setFill(Color.web("#f0f4c3",1.0));
		gcSide.fillRect(0,0,sideCanvas.getWidth(), sideCanvas.getHeight());
		sideCanvas.setLayoutX(800);
		gcSide.setStroke(Color.BLACK);
		gcSide.setLineWidth(2);
		gcSide.strokeRect(0,0,sideCanvas.getWidth(), sideCanvas.getHeight());
		gcSide.strokeText("Score :", 85, 100);
		gcSide.strokeText("Appuyer sur ECHAP pour\nrevenir au menu principal", 55 ,550, 100);
		gcSide.strokeText("Compteur de pas :\n" + "5" ,50,200);


		Button startBtn = new Button();
		startBtn.setMinWidth(800);
		startBtn.setLayoutX(0);
		startBtn.setLayoutY(500);
		startBtn.setText("Start");
		startBtn.setOnAction(event -> {
			boardGUI.getBoard().setLevel(LevelLoader.loadFile(listLevels.getValue().toString(), boardGUI.getBoard()));
			primaryStage.setScene(scene);
			boardGUI.middle(boardGUI.getBoard());
			boardGUI.update(this);
			this.groupe.getChildren().clear();
			this.groupe.getChildren().addAll(boardGUI.canvas, sideCanvas);

		});

		groupeNextLevel.getChildren().add(menuBtn);

		Canvas canvasMenu = new Canvas(800, 600);
		GraphicsContext gcMenu = canvasMenu.getGraphicsContext2D();
        Image logoMenu = new Image(getClass().getResource("../logo.png").toString());
        gcMenu.drawImage(logoMenu, 230, 25);
		this.groupeMenu.getChildren().addAll(canvasMenu,startBtn,listLevels);

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
				case ESCAPE:
					boardGUI.gc.clearRect(0,0,boardGUI.canvas.getWidth(),boardGUI.canvas.getHeight());
					primaryStage.setScene(sceneMenu);
					break;
			}

			if (!boardGUI.getBoard().getLevel().isFinished() && quitGame) {
				primaryStage.setScene(scene);
			} else if (boardGUI.getBoard().getLevel().isFinished()) {
				boardGUI.gc.clearRect(0,0,boardGUI.canvas.getWidth(),boardGUI.canvas.getHeight());
				primaryStage.setScene(sceneNextLevel);
			}

		});

		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}

}
