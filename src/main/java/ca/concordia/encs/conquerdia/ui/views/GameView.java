package ca.concordia.encs.conquerdia.ui.views;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

@SuppressWarnings({ "restriction" })
public class GameView extends BaseView {

	public GameView(double width, double height) {
		super(width, height);
		gc = getGraphicsContext2D();
	}

	public void drawMap() {
		gc.setFill(Color.WHITESMOKE);
		gc.fillRect(0, 0, getWidth(), getHeight());
	}

	@Override
	public boolean isResizable() {

		return true;
	}
}
