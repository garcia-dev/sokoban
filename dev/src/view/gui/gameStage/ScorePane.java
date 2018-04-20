package view.gui.gameStage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import model.general.Board;
import model.general.Score;
import utility.Observer;
import view.gui.levelSelectionStage.LevelSelectionStage;

class ScorePane extends BorderPane implements Observer {
	private final BorderPane scoreBorderPane;
	private final ImageView logoImageView;

	private Score score;
	private int timer;

	ScorePane(double width, double height, ImageView logoImageView, GameStage parentStage) {
		this.logoImageView = logoImageView;

		timer = 0;

		setMinHeight(height);
		setPrefWidth(width * 0.25);
		setPadding(new Insets(10));

		setStyle("-fx-background-color: #E0E0E0");

		logoImageView.setFitHeight(40);
		logoImageView.setPreserveRatio(true);

		setTop(logoImageView);
		BorderPane.setAlignment(logoImageView, Pos.CENTER);

		scoreBorderPane = new BorderPane();
		scoreBorderPane.setStyle("-fx-padding: 200 0");

		setCenter(scoreBorderPane);

		Button returnButton = new Button("Return");
		returnButton.setStyle(String.format("-fx-font-size: %d;", (int) (0.45 * 40)));
		returnButton.setOnAction(event -> {
			new LevelSelectionStage(width, height, logoImageView);
			parentStage.close();
		});

		setBottom(returnButton);
		BorderPane.setAlignment(returnButton, Pos.CENTER);
		BorderPane.setMargin(returnButton, new Insets(10));
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	Score getScore() {
		return score;
	}

	@Override
	public void update() {
		scoreBorderPane.setTop(null);

		Label stepLabel = new Label("Steps : " + score.getStepCount());

		scoreBorderPane.setTop(stepLabel);
		BorderPane.setAlignment(stepLabel, Pos.CENTER);

		Label timerLabel = new Label("Time : " + timer + "s");

		scoreBorderPane.setBottom(timerLabel);
		BorderPane.setAlignment(timerLabel, Pos.CENTER);
	}

	public void setBoard(Board board) {
		score = board.getLevel().getScore();
		score.addObserver(this);

		update();
	}

	ImageView getLogoImageView() {
		return logoImageView;
	}
}
