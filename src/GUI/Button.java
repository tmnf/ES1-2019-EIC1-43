package GUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

/**
 * Button represents all the buttons used in this software, with rounded
 * corners.
 */
public class Button extends JButton {

	private static final long serialVersionUID = 1269050536627782589L;

	/**
	 * Default radius to define button corners
	 */
	private static final int RADIUS_DEFAULT = 40;

	/**
	 * Controls corners in each buttons
	 */
	private int radius;

	/**
	 * Button constructor defining only text, and using default radius.
	 * 
	 * @param text text to show in the button
	 */
	public Button(String text) {
		super(text);
		this.radius = RADIUS_DEFAULT;

		applyCommons();
	}

	/**
	 * Button constructor defining text and custom corner radius
	 * 
	 * @param text   text to show in the button
	 * @param radius defines how rounded button corners will be
	 */
	public Button(String text, int radius) {
		super(text);
		this.radius = radius;

		applyCommons();
	}

	/**
	 * Common priorities used in both default and custom buttons
	 */
	private void applyCommons() {
		setFont(Popup.ARIAL_PLAIN_SMALL);
		setContentAreaFilled(false);
		setForeground(Color.WHITE);
	}

	/**
	 * Override method of JComponent's paintComponent()
	 */
	@Override
	protected void paintComponent(Graphics g) {
		if (getModel().isArmed()) {
			g.setColor(new Color(20, 20, 20, 220));
		} else if (getModel().isRollover()) {
			g.setColor(new Color(40, 40, 40, 220));
		} else
			g.setColor(new Color(50, 50, 50, 220));

		g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

		super.paintComponent(g);
	}

	/**
	 * Override method of AbstractButton's paintBorder()
	 */
	@Override
	protected void paintBorder(Graphics g) {
		g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
	}
}
