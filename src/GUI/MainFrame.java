package GUI;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * MainFrame represents every frame in graphical interface defining a default
 * icon.
 */

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 3858229905442780638L;

	/**
	 * MainFrame's constructor that loads an icon image and sets the frame's icon
	 */
	public MainFrame() {
		URL url = MainFrame.class.getResource("/icon.png");
		ImageIcon ic = new ImageIcon(url);
		setIconImage(ic.getImage());
	}
}
