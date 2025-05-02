package reversi.creversi;

import java.io.IOException;

import reversi.Game;

public class Creversi {

	public static void main(String[] args) throws IOException {
		int outcome, retry;

		Cgame cgame = new Cgame();
		System.out.println("ようこそ\nここではリバーシをプレイできます。");

		do {
			cgame.start();
			outcome = cgame.playGame();

			switch (outcome) {
			case Game.WIN -> System.out.println("貴方の勝ち");
			case Game.LOSE -> System.out.println("貴方の負け");
			case Game.DRAW -> System.out.println("引き分け");
			default -> throw new IllegalArgumentException("Unexpected value: " + outcome);
			}

			do {
				System.out.print("もう一局打ちますか?\nYES:1、NO:-1>");
				retry = cgame.inputNumber();
			} while (retry != 1 && retry != -1);
		} while (retry == 1);

		System.out.println("終了します。");
	}

}
