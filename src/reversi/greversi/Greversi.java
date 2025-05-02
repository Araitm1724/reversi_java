package reversi.greversi;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Greversi {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		JFrame mainFrame = new JFrame("Reversi"); // メインフレームの生成
		mainFrame.setBounds(50, 50, 1280, 720);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton button = new JButton("push"); // 「push」ボタンの生成

		//		「push」ボタンをメインフレーム上部に配置
		mainFrame.getContentPane().add(button, BorderLayout.NORTH);

		mainFrame.setVisible(true);
	}

}
