package reversi.creversi;

import reversi.Board;

/**
 * リバーシ盤クラス
 */
public class Cboard extends Board {
	// 盤の状態（マスの描画に使用）
	private char[] squareStatus = { '　', '●', '○' };

	@Override
	protected void drawBoard() {
		System.out.println("  １ ２ ３ ４ ５ ６ ７ ８");

		for (y = 1; y <= 8; y++) {
			System.out.println(" +ー+ー+ー+ー+ー+ー+ー+ー+");
			System.out.print(y);

			for (x = 1; x <= 8; x++) {
				switch (boardStatus[y][x]) {
				case SPACE -> System.out.print("|" + squareStatus[0]);
				case BLACK -> System.out.print("|" + squareStatus[1]);
				case WHITE -> System.out.print("|" + squareStatus[2]);
				default -> throw new IllegalArgumentException("Unexpected value: "
						+ boardStatus[y][x]);
				}
			}

			System.out.println("|");
		}

		System.out.println(" +ー+ー+ー+ー+ー+ー+ー+ー+");

		countStones();
		System.out.println("黒：" + blackCount + " 白：" + whiteCount);
	}
}
