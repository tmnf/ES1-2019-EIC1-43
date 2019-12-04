package GUI;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainFrame() {
		URL url = MainFrame.class.getResource("/icon.png");
		ImageIcon ic = new ImageIcon(url);
		setIconImage(ic.getImage());
	}
}
