package GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = -8160458471287768735L;

	private Image background;

	public MainPanel(LayoutManager layout) {
		super(layout);
		try {
			URL url = MainPanel.class.getResource("/background.jpg");
			background = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
	}

}
