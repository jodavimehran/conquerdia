package ca.concordia.encs.conquerdia.ui;

import java.awt.Dimension;

import ca.concordia.encs.conquerdia.ui.views.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ConquerdiaJavaFxUI extends Application {
	
	public static void start() {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		initUI(primaryStage);
	}

	private void initUI(Stage primaryStage) {

		GameView canvas = new GameView(600, 600);
		canvas.drawMap();
		Pane root = new Pane();
		root.getChildren().add(canvas);
		Dimension screen = getScreenDimension(primaryStage);
		Scene scene = new Scene(root, screen.getWidth(), screen.getHeight());

		primaryStage.setTitle("Conquerdia");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

//	public static void main(String[] args) {
//		start();
//	}

	public Dimension getScreenDimension(Stage stage) {
		return new Dimension((int) stage.getWidth(), (int) stage.getHeight());
		// return Toolkit.getDefaultToolkit().getScreenSize();
	}
}
