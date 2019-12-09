package GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * MainPanel represents all panels from this software's graphical interface
 * defining the default background
 */

public class MainPanel extends JPanel {

	private static final long serialVersionUID = -8160458471287768735L;

	/**
	 * Default background image
	 */
	private Image background;

	/**
	 * MainPanel's constructor to create a new JPanel, with a default background
	 * 
	 * @param layout sets what organization layout will be used in this panel
	 */
	public MainPanel(LayoutManager layout) {
		super(layout);
		try {
			URL url = MainPanel.class.getResource("/background.jpg");
			background = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Override method of JComponent's paintComponent()
	 * */
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
	}

}
