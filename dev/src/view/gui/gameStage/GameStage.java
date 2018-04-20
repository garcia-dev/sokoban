package view.gui.gameStage;

import controller.GameController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.general.Board;
import model.general.Direction;
import model.general.LevelLoader;
import model.solver.Solver;

import java.util.ArrayList;
import java.util.Objects;

public class GameStage extends Stage {
	private final ScorePane scorePane;

	public GameStage(double width, double height, int level, boolean anyTime, ImageView logoImageView) {
		setMinWidth(width);
		setMinHeight(height);

		BorderPane mainPane = new BorderPane();

		GridPane boardGridPane = new GridPane();
		boardGridPane.setMinHeight(height);
		boardGridPane.setAlignment(Pos.CENTER);
		boardGridPane.setVgap(20);

		scorePane = new ScorePane(width, height, logoImageView, this);
		mainPane.setRight(scorePane);

		BoardPane playerBoardPane = new BoardPane(level, false, this);

		scorePane.setBoard(playerBoardPane.getBoard());

		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ae -> {
			scorePane.setTimer(scorePane.getTimer() + 1);
			scorePane.update();
		}));
		timeline.setCycleCount(Animation.INDEFINITE);

		BoardPane solverBoardPane = null;

		boardGridPane.add(playerBoardPane, 0, 0);

		ArrayList<Direction> solverMoves = new ArrayList<>();

		if (anyTime) {
			solverBoardPane = new BoardPane(level, true, this);

			Board board = new Board();
			board.setLevel(LevelLoader.loadFile(level, board));
			Solver solver = new Solver(board);

			solverMoves = solver.solveBoard(new GameController(board));

			boardGridPane.add(solverBoardPane, 0, 1);
		}

		mainPane.setCenter(boardGridPane);
		mainPane.setRight(scorePane);

		setScene(new Scene(mainPane));

		show();

		playerBoardPane.requestFocus();

		GameController playerController = playerBoardPane.getController();

		final BoardPane finalSolverBoardPane = solverBoardPane;
		final ArrayList<Direction> finalSolverMoves = solverMoves;

		timeline.play();

		getScene().setOnKeyPressed(event -> {
			if (!playerBoardPane.getController().isFinished()) {
				switch (event.getCode()) {
					case W:
						if (playerController.move(playerBoardPane.getBoard().getLevel().getPlayerCase().getPawn(), Direction.UP)) {
							if (anyTime && !finalSolverBoardPane.getController().isFinished()) {
								Objects.requireNonNull(finalSolverBoardPane).getController().move(finalSolverBoardPane.getBoard().getLevel().getPlayerCase().getPawn(), finalSolverMoves.get(0));
								finalSolverMoves.remove(0);
							}
						}
						break;
					case S:
						if (playerController.move(playerBoardPane.getBoard().getLevel().getPlayerCase().getPawn(), Direction.DOWN)) {
							if (anyTime && !finalSolverBoardPane.getController().isFinished()) {
								Objects.requireNonNull(finalSolverBoardPane).getController().move(finalSolverBoardPane.getBoard().getLevel().getPlayerCase().getPawn(), finalSolverMoves.get(0));
								finalSolverMoves.remove(0);
							}
						}
						break;
					case A:
						if (playerController.move(playerBoardPane.getBoard().getLevel().getPlayerCase().getPawn(), Direction.LEFT)) {
							if (anyTime && !finalSolverBoardPane.getController().isFinished()) {
								Objects.requireNonNull(finalSolverBoardPane).getController().move(finalSolverBoardPane.getBoard().getLevel().getPlayerCase().getPawn(), finalSolverMoves.get(0));
								finalSolverMoves.remove(0);
							}
						}
						break;
					case D:
						if (playerController.move(playerBoardPane.getBoard().getLevel().getPlayerCase().getPawn(), Direction.RIGHT)) {
							if (anyTime && !finalSolverBoardPane.getController().isFinished()) {
								Objects.requireNonNull(finalSolverBoardPane).getController().move(finalSolverBoardPane.getBoard().getLevel().getPlayerCase().getPawn(), finalSolverMoves.get(0));
								finalSolverMoves.remove(0);
							}
						}
						break;
				}

				playerBoardPane.requestFocus();
			} else
				timeline.stop();
		});
	}

	ScorePane getScorePane() {
		return scorePane;
	}
}