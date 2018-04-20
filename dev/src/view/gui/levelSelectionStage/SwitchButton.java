package view.gui.levelSelectionStage;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;

class SwitchButton extends Label {
	private final SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(true);

	SwitchButton(){
		Button switchBtn = new Button();
		switchBtn.setOnAction(event -> switchedOn.set(!switchedOn.get()));

		setGraphic(switchBtn);

		switchedOn.addListener((observable, oldValue, newValue) -> {
			if (oldValue){
				setText("OFF");
				setStyle("-fx-background-color: grey;-fx-text-fill:black;");
				setContentDisplay(ContentDisplay.LEFT);
			} else {
				setText("ON");
				setStyle("-fx-background-color: green;-fx-text-fill:white;");
				setContentDisplay(ContentDisplay.RIGHT);
			}
		});

		switchedOn.set(false);
	}

	boolean getSwitchedOnValue() {
		return switchedOn.get();
	}
}
