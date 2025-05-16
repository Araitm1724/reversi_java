package reversi.greversi;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

public abstract class RightPanels extends JPanel {
	JPanel menuPanel, buttonPanel;
	private int panelWidth, panelHeight;

	RightPanels(String name, int screenWidth, int gamePanelWidth, int screenHeight) {
		panelWidth = screenWidth - gamePanelWidth;
		panelHeight = screenHeight;
		setPreferredSize(new Dimension(panelWidth, panelHeight));

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
