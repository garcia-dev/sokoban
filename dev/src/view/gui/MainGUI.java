package view.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainGUI extends Application {
	@Override
	public void start(Stage primaryStage) {
		new MainMenuStage(800, 600);
	}

	public static void main(String[] args) {
		launch(args);
	}
}