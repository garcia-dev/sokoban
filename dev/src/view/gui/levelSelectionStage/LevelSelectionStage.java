package view.gui.levelSelectionStage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import view.gui.MainMenuStage;
import view.gui.gameStage.GameStage;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;

public class LevelSelectionStage extends Stage {
	public LevelSelectionStage(double width, double height, ImageView logoImageView) {
		int numberOfLevels = 0;
		try {
			numberOfLevels = Objects.requireNonNull(new File(ClassLoader.getSystemResource("resources/levels").toURI()).listFiles()).length;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		Button[] levelButtonArray = new Button[numberOfLevels];
		int buttonSize = 120;
		int gridGap = 10;

		GridPane levelsGridPane = new GridPane();
		levelsGridPane.setHgap(gridGap);
		levelsGridPane.setVgap(gridGap);
		levelsGridPane.setAlignment(Pos.CENTER);

		HBox anytimeHBox = new HBox();
		anytimeHBox.setAlignment(Pos.CENTER);
		anytimeHBox.setSpacing(5);

		Label anytimeLabel = new Label("Anytime Mode :");
		SwitchButton anytimeSwitchButton = new SwitchButton();

		anytimeHBox.getChildren().addAll(anytimeLabel, anytimeSwitchButton);

		int row = 0;
		int col = 0;
		for (int ind = 0; ind < levelButtonArray.length; ind++) {
			levelButtonArray[ind] = new Button(String.valueOf(ind + 1));
			levelButtonArray[ind].setStyle(String.format("-fx-font-size: %d;", (int) (0.45 * buttonSize)));

			if (col == 4) {
				levelsGridPane.add(levelButtonArray[ind], col, row);
				row++;
				col = 0;
			} else {
				levelsGridPane.add(levelButtonArray[ind], col, row);
				col++;
			}

			int finalInd = ind;
			levelButtonArray[ind].setOnAction(event -> {
				new GameStage(width, height, finalInd + 1, anytimeSwitchButton.getSwitchedOnValue(), logoImageView);
				close();
			});
		}

		Button returnButton = new Button("Return");
		returnButton.setStyle(String.format("-fx-font-size: %d;", (int) (0.45 * 40)));
		returnButton.setOnAction(event -> {
			new MainMenuStage(width, height);
			close();
		});

		BorderPane buttonPane = new BorderPane();
		buttonPane.setLeft(returnButton);
		buttonPane.setRight(anytimeHBox);
		BorderPane.setAlignment(anytimeHBox, Pos.CENTER);

		BorderPane mainPane = new BorderPane();
		mainPane.setPadding(new Insets(24));
		mainPane.setTop(logoImageView);
		mainPane.setCenter(levelsGridPane);
		mainPane.setBottom(buttonPane);

		setWidth(width);
		setHeight(height);
		setMinWidth(logoImageView.getFitWidth() + 50);
		setMinHeight(400);
		setScene(new Scene(mainPane));
		show();
	}
}
