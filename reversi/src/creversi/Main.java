package creversi;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		boolean isPlay;
		int result, retry;

		Game game = new Game();

		isPlay = game.start();

		while (isPlay) {
			result = game.playGame();

			if (result == game.WIN) {
				System.out.println("貴方の勝ち");
			} else if (result == game.LOSE) {
				System.out.println("貴方の負け");
			} else {
				System.out.println("引き分け");
			}

			do {
				System.out.print("もう一局打ちますか?\nyes：1、no：-1＞");
				retry = game.inputNumber();
			} while (retry != 1 && retry != -1);

			if (game.inputNumber() == -1) {
				isPlay = false;
			}
		}

		System.out.println("終了します。ありがとうございました。");
	}
}
