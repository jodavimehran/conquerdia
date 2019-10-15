package ca.concordia.encs.conquerdia.ui;

import javafx.application.*;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class Main extends Application {

	@SuppressWarnings("restriction")
	@Override
	public void start(Stage primaryStage) throws Exception {

		Group root = new Group();
		Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());// setting color to the scene
		scene.setFill(Color.BROWN);
		primaryStage.setTitle("Conquerdia");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
