package view.gui;

/**
 * MainGUI's class
 * <p>
 * The class MainGUI is used to print a Graphical User Interface using JavaFX.
 * </p>
 *
 * @author DE OLIVEIRA Dylan, GARCIA Romain, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-04-10
 */

import controller.GameController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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
import javafx.util.Duration;
import model.general.Board;
import model.general.LevelLoader;


import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;

import static model.general.Direction.*;

public class MainGUI extends Application {

	private static final ObservableList<Integer> levels = FXCollections.observableArrayList();

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

		ScoreGUI scoreGUI = new ScoreGUI(boardGUI.getBoard());
		scoreGUI.getBoard().addObserver(scoreGUI);

		Timeline timeline = new Timeline(new KeyFrame(
				Duration.millis(1000),
				ae -> {
					scoreGUI.timer++;
					scoreGUI.update();
				}));
		timeline.setCycleCount(Animation.INDEFINITE);

		int numberOfLevels = 0;
		try {
			numberOfLevels = Objects.requireNonNull(new File(ClassLoader.getSystemResource("resources/levels").toURI()).listFiles()).length;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		for (int levelNumber = 1; levelNumber <= numberOfLevels; levelNumber++) levels.add(levelNumber);

		ChoiceBox<? extends Integer> listLevels = new ChoiceBox<>(levels);
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

		Button startBtn = new Button();
		startBtn.setMinWidth(800);
		startBtn.setLayoutX(0);
		startBtn.setLayoutY(500);
		startBtn.setText("Start");
		startBtn.setOnAction(event -> {
			timeline.play();
			boardGUI.getBoard().setLevel(LevelLoader.loadFile(listLevels.getValue(), boardGUI.getBoard()));
			primaryStage.setScene(scene);
			boardGUI.middle(boardGUI.getBoard());
			boardGUI.update();
			scoreGUI.update();
			this.groupe.getChildren().clear();
			this.groupe.getChildren().addAll(boardGUI.canvas, scoreGUI.getPanel());

		});

		groupeNextLevel.getChildren().add(menuBtn);

		Canvas canvasMenu = new Canvas(800, 600);
		GraphicsContext gcMenu = canvasMenu.getGraphicsContext2D();
        Image logoMenu = new Image(getClass().getResource("/resources/images/logo.png").toString());
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

			if (!gameController.isFinished() && quitGame) {
				primaryStage.setScene(scene);
			} else if (gameController.isFinished()) {
				boardGUI.gc.clearRect(0,0,boardGUI.canvas.getWidth(),boardGUI.canvas.getHeight());
				primaryStage.setScene(sceneNextLevel);

			}
			scoreGUI.update();

		});

		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}

}
