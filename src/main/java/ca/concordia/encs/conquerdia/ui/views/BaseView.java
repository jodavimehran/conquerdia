package ca.concordia.encs.conquerdia.ui.views;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

@SuppressWarnings({ "restriction" })
public class BaseView extends Canvas {
	public BaseView(double width, double height) {
		super(width, height);
	}

	protected GraphicsContext gc;
}
