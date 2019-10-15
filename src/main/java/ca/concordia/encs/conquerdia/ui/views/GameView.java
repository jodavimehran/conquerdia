package ca.concordia.encs.conquerdia.ui.views;

import javafx.scene.paint.Color;

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
