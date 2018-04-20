package view.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.gui.levelSelectionStage.LevelSelectionStage;

public class MainMenuStage extends Stage {
	public MainMenuStage(double width, double height) {
		Image logoImage = new Image(String.valueOf(getClass().getClassLoader().getResource("resources/images/logo.png")));
		ImageView logoImageView = new ImageView(logoImage);
		logoImageView.setFitWidth(width / 2);
		logoImageView.setPreserveRatio(true);

		int buttonSize = 50;

		Button choseLevelButton = new Button("Select a level");
		choseLevelButton.setStyle(String.format("-fx-font-size: %dpx;", (int)(0.45 * buttonSize)));

		Button quitGameButton = new Button("Quit game");
		quitGameButton.setStyle(String.format("-fx-font-size: %dpx;", (int)(0.45 * (buttonSize - 10))));

		choseLevelButton.setOnAction(event -> {
			new LevelSelectionStage(width, height, logoImageView);
			close();
		});

		quitGameButton.setOnAction(event -> System.exit(0));

		BorderPane mainPane = new BorderPane();
		mainPane.setPadding(new Insets(24));
		BorderPane.setAlignment(logoImageView, Pos.CENTER);
		mainPane.setTop(logoImageView);
		mainPane.setCenter(choseLevelButton);
		mainPane.setBottom(quitGameButton);
		BorderPane.setAlignment(quitGameButton, Pos.CENTER);

		setWidth(width);
		setHeight(height);
		setMinWidth(logoImageView.getFitWidth() + 50);
		setMinHeight(350);
		setScene(new Scene(mainPane));
		show();
	}
}
