package view.gui;

import controller.GameController;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.general.Board;
import model.general.LevelLoader;

import static model.general.Direction.*;

public class Game {
	private final Scene scene;
	private final Group group;
	private final Canvas scoreCanvas;
	private final MainGUI mainGUI;
	private Scene sceneNextLevel;
	private boolean anyTime;
	private int level;
	private Stage stage;

	public Game(boolean anyTime, int level, Stage stage, Scene scene, Scene sceneNextLevel, Group group, Canvas scoreCanvas, MainGUI mainGUI) {
		this.anyTime = anyTime;
		this.level = level;
		this.stage = stage;
		this.scene = scene;
		this.sceneNextLevel = sceneNextLevel;
		this.group = group;
		this.scoreCanvas = scoreCanvas;
		this.mainGUI = mainGUI;

		initGame();
	}

	public void initGame(){
		BoardGUI[] boards;
		if (anyTime) {
			boards = new BoardGUI[2];
			boards[0] = new BoardGUI(new Board(), mainGUI, 500, 300);
			boards[1] = new BoardGUI(new Board(), mainGUI, 500, 300);
		} else {
			boards = new BoardGUI[1];
			boards[0] = new BoardGUI(new Board(), mainGUI, 400, 600);
		}

		GameController[] controllers = new GameController[boards.length];

		for (int index = 0; index < controllers.length; index++)
			controllers[index] = new GameController(boards[index].getBoard());

		for (BoardGUI board : boards) {
			board.getBoard().addObserver(board);
			board.getBoard().setLevel(LevelLoader.loadFile(level, board.getBoard()));
			board.update();
		}

		stage.setScene(scene);

		Pane[] panes = new Pane[boards.length + 1];

		for (Pane pane : panes) pane = new Pane();

		for (int index = 0; index < boards.length; index++)
			panes[index].getChildren().add(boards[index]);

		panes[panes.length - 1].getChildren().add(scoreCanvas);

		boards[0].update();
		this.group.getChildren().clear();
		this.group.getChildren().addAll(boards[0].getCanvas(), boards[1].getCanvas(), scoreCanvas);

		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
				case UP:
					controllers[0].move(boards[0].getBoard().getLevel().getPlayerCase().getPawn(), UP);
					break;
				case DOWN:
					controllers[0].move(boards[0].getBoard().getLevel().getPlayerCase().getPawn(), DOWN);
					break;
				case LEFT:
					controllers[0].move(boards[0].getBoard().getLevel().getPlayerCase().getPawn(), LEFT);
					break;
				case RIGHT:
					controllers[0].move(boards[0].getBoard().getLevel().getPlayerCase().getPawn(), RIGHT);
					break;
				case ESCAPE:
					boards[0].gc.clearRect(0, 0, boards[0].canvas.getWidth(), boards[0].canvas.getHeight());
					stage.setScene(scene);
					break;
			}

			if (!controllers[0].isFinished()) {
				stage.setScene(scene);
			} else if (controllers[0].isFinished()) {
				boards[0].gc.clearRect(0, 0, boards[0].canvas.getWidth(), boards[0].canvas.getHeight());
				stage.setScene(sceneNextLevel);
			}

		});
	}
}
