package reversi.greversi;

import java.awt.FlowLayout;

import javax.swing.JPanel;

public abstract class RightPanels extends JPanel {
	JPanel menuPanel, buttonPanel;

	RightPanels(String name) {
		setName(name);
		setLayout(new FlowLayout());

		menuPanel = new JPanel();
		menuPanel.setName(name);
		add(menuPanel);

		buttonPanel = new JPanel();
		buttonPanel.setName(name);
		add(buttonPanel);
	}

	protected abstract void setButton(String name);
}
