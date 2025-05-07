package reversi;

import java.awt.FlowLayout;

import javax.swing.JPanel;

public abstract class RightPanels extends JPanel {
	protected RightPanels(String name) {
		setName(name);
		setLayout(new FlowLayout());
	}
}
