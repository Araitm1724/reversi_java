package creversi;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		boolean isPlay;
		int outcome, retry;

		Game game = new Game();
		isPlay = game.start();

		while (isPlay) {
			outcome = game.playGame();

			if (outcome == game.WIN) {
				System.out.println("貴方の勝ち");
			} else if (outcome == game.LOSE) {
				System.out.println("貴方の負け");
			} else {
				System.out.println("引き分け");
			}

			do {
				System.out.print("もう一局打ちますか?\nYES：1、NO：-1>");
				retry = game.inputNumber();
			} while (retry != 1 && retry != -1);

			if (retry == -1) {
				isPlay = false;
			}
		}

		System.out.println("終了します。ありがとうございました。");
	}
}
