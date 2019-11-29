package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;

public class Button extends JButton {

	private static final int RADIUS_DEFAULT = 40;

	private int radius;

	public Button(String text) {
		super(text);
		this.radius = RADIUS_DEFAULT;

		applyFormat();
	}

	private void applyFormat() {
		setContentAreaFilled(false);
		setForeground(Color.WHITE);
		setFont(new Font("Arial", Font.PLAIN, 14));
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (getModel().isArmed()) {
			g.setColor(new Color(20, 20, 20, 220));
		} else if (getModel().isRollover()) {
			g.setColor(new Color(40, 40, 40, 220));
		} else
			g.setColor(new Color(60, 60, 60, 220));

		g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

		super.paintComponent(g);
	}

	@Override
	protected void paintBorder(Graphics g) {
		g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
	}

}
