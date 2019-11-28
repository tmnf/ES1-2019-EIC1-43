package GUI;

import java.awt.Graphics;

import javax.swing.JButton;

import com.sun.prism.paint.Color;

public class Button extends JButton {

	private int corners;

	public Button(String text, int corners) {
		super(text);

		this.corners = corners;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	@Override
	protected void paintBorder(Graphics g) {
		super.paintBorder(g);
		g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, corners, corners);
	}

}
