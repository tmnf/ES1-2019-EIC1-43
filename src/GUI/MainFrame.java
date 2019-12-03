package GUI;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainFrame() {
		ImageIcon ic = new ImageIcon("images/icon.png");
		setIconImage(ic.getImage());
	}
}
