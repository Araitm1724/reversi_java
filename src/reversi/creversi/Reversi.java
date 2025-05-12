package reversi.creversi;

public class Reversi {
	public static void main(String[] args) {
		int outcome, retry = outcome = 0;
		Cgame cgame = new Cgame();

		System.out.println("ようこそ\nここではリバーシをプレイできます。");

		do {
			outcome = cgame.playGame();

			switch (outcome) {
				case Cgame.WIN -> System.out.println("貴方の勝ち");
				case Cgame.LOSE -> System.out.println("貴方の負け");
				case Cgame.DRAW -> System.out.println("引き分け");
				default -> throw new IllegalArgumentException("Unexpected value: " + outcome);
			}

			do {
				System.out.print("もう一局打ちますか?\nYES:1、NO:-1>");
				retry = cgame.playerInput();
			} while (retry != 1 && retry != -1);
		} while (retry == 1);

		System.out.println("終了します。");
	}

}
