package view.gui.gameStage;

import controller.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.general.Board;
import model.general.Case;
import model.general.LevelLoader;
import utility.Observer;
import view.gui.levelSelectionStage.LevelSelectionStage;

class BoardPane extends HBox implements Observer {
	private final Image wallImage = new Image(String.valueOf(getClass().getClassLoader().getResource("resources/images/wall.png")));
	private final Image emptyImage = new Image(String.valueOf(getClass().getClassLoader().getResource("resources/images/empty.png")));
	private final Image targetImage = new Image(String.valueOf(getClass().getClassLoader().getResource("resources/images/target.png")));
	private final Image playerImage = new Image(String.valueOf(getClass().getClassLoader().getResource("resources/images/player.png")));
	private final Image crateImage = new Image(String.valueOf(getClass().getClassLoader().getResource("resources/images/crate.png")));
	private final GridPane boardGridPane;
	private final GameController controller;
	private final boolean isSolver;
	private final double height;
	private final ScorePane scorePane;
	private final GameStage gameStage;
	private final Board board;

	BoardPane(int level, boolean isSolver, GameStage gameStage) {
		this.isSolver = isSolver;
		this.gameStage = gameStage;

		height = gameStage.getMinHeight();
		scorePane = gameStage.getScorePane();

		setAlignment(Pos.CENTER);
		setMaxHeight(height / 2);

		board = new Board();

		board.setLevel(LevelLoader.loadFile(level, board));
		board.addObserver(this);

		boardGridPane = new GridPane();
		boardGridPane.setHgap(0);
		boardGridPane.setVgap(0);
		boardGridPane.setGridLinesVisible(false);
		boardGridPane.setAlignment(Pos.CENTER);

		controller = new GameController(board);

		getChildren().add(boardGridPane);
		update();
	}

	Board getBoard() {
		return board;
	}

	GameController getController() {
		return controller;
	}

	@Override
	public void update() {
		boardGridPane.getChildren().clear();

		if (!controller.isFinished()) {
			for (Case[] row : board.getLevel().getCaseArray()) {
				for (Case aCase : row) {
					ImageView caseImageView = new ImageView();
					caseImageView.setFitHeight((height - 330) / board.getLevel().getCaseArray().length);
					caseImageView.setPreserveRatio(true);

					if (aCase.getPawn() == null) {
						switch (aCase.getState()) {
							case WALL:
								caseImageView.setImage(wallImage);
								boardGridPane.add(caseImageView, aCase.getCoord()[1], aCase.getCoord()[0]);
								break;
							case EMPTY:
								caseImageView.setImage(emptyImage);
								boardGridPane.add(caseImageView, aCase.getCoord()[1], aCase.getCoord()[0]);
								break;
							case TARGET:
								caseImageView.setImage(targetImage);
								boardGridPane.add(caseImageView, aCase.getCoord()[1], aCase.getCoord()[0]);
								break;
						}
					} else {
						switch (aCase.getPawn().getType()) {
							case PLAYER:
								caseImageView.setImage(playerImage);
								boardGridPane.add(caseImageView, aCase.getCoord()[1], aCase.getCoord()[0]);
								break;
							case CRATE:
								caseImageView.setImage(crateImage);
								boardGridPane.add(caseImageView, aCase.getCoord()[1], aCase.getCoord()[0]);
								break;
						}
					}

					GridPane.setMargin(caseImageView, new Insets(0));
				}
			}
		} else if (isSolver) {
			getChildren().clear();

			Label doneLabel = new Label("Done !");
			doneLabel.setStyle(String.format("-fx-font-size : %d", (int) (getWidth() * 0.1)));

			getChildren().add(doneLabel);
		} else {
			Alert winAlert = new Alert(Alert.AlertType.INFORMATION);
			winAlert.setTitle("Congratulations !");
			winAlert.setHeaderText(null);
			winAlert.setContentText("Congratulations, you completed the game with " + scorePane.getScore().getStepCount() + " steps in " + scorePane.getScore().getDuration().toSeconds() + " seconds.");

			winAlert.setOnCloseRequest(event -> {
				new LevelSelectionStage(gameStage.getWidth(), gameStage.getHeight(), scorePane.getLogoImageView());
				this.gameStage.close();
			});

			winAlert.showAndWait();
		}
	}
}
