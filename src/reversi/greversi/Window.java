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

		// メインフレームの設定
		setTitle("Reversi");
		setBounds(50, 50, windowWidth, windowHeight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 先後選択ラジオボタンの設定
		JLabel colorLabel = new JLabel("石の色を選択");
		JRadioButton blackButton = new JRadioButton("黒（先手）", true);
		JRadioButton whiteButton = new JRadioButton("白（後手）");

		// 2つのボタンを同じグループにまとめる
		ButtonGroup colorButtons = new ButtonGroup();
		colorButtons.add(blackButton);
		colorButtons.add(whiteButton);

		JPanel selectColor = new JPanel();
		selectColor.add(colorLabel);
		selectColor.add(blackButton);
		selectColor.add(whiteButton);

		Container contentPane = getContentPane();
		contentPane.add(selectColor, BorderLayout.EAST);

		// COMレベル選択ラジオボタンの設定
		JLabel levelLabel = new JLabel("COMのレベルを選択");
		JRadioButton level1Button = new JRadioButton("レベル1", true);
		JRadioButton level2Button = new JRadioButton("レベル2", true);

		// 2つのボタンを同じグループにまとめる
		ButtonGroup levelButtons = new ButtonGroup();
		levelButtons.add(level1Button);
		levelButtons.add(level2Button);

		JPanel selectLevel = new JPanel();
		selectLevel.add(levelLabel);
		selectLevel.add(level1Button);
		selectLevel.add(level2Button);

		contentPane.add(selectLevel, BorderLayout.EAST);
	}
}
