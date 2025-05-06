package reversi.greversi;

import java.awt.*;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Window extends JFrame {
	Window() {
		// スクリーンサイズを取得
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int windowWidth = (int) (screenSize.getWidth() * 0.8);
		int windowHeight = (int) (screenSize.getHeight() * 0.8);
		int windowStartX = (int) (screenSize.getWidth() * 0.05);
		int windowStartY = (int) (screenSize.getHeight() * 0.05);
		int fontSize = (int) (windowWidth * 0.025);
		System.out.println(windowWidth);
		System.out.println(windowHeight);

		// メインフレームの設定
		setTitle("Reversi");
		setBounds(windowStartX, windowStartY, windowWidth, windowHeight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// メインパネル
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout());

		// 盤パネル
		JPanel boardPanel = new JPanel();
		mainPanel.add(boardPanel);

		// メニューパネル
		JPanel menuPanel = new JPanel();
		mainPanel.add(menuPanel);

		// 対局情報パネル
		JPanel gamePanel = new JPanel();

		// 終局パネル
		JPanel endPanel = new JPanel();

		getContentPane().add(mainPanel, BorderLayout.CENTER);
	}
}
