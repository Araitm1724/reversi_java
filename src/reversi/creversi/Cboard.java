package reversi.creversi;

import reversi.Board;

public class Cboard extends Board {
	@Override
	public void drawBoard() {
		System.out.println("  １ ２ ３ ４ ５ ６ ７ ８");

		for (y = 1; y <= 8; y++) {
			System.out.println(" +ー+ー+ー+ー+ー+ー+ー+ー+");
			System.out.print(y);

			for (x = 1; x <= 8; x++) {
				switch (bStatus[y][x]) {
				case SPACE:
					System.out.print("|" + squareStatus[0]);
					break;
				case BLACK:
					System.out.print("|" + squareStatus[1]);
					break;
				case WHITE:
					System.out.print("|" + squareStatus[2]);
					break;
				default:
					break;
				}
			}

			System.out.println("|");
		}

		System.out.println(" +ー+ー+ー+ー+ー+ー+ー+ー+");

		countStones();
		System.out.println("黒：" + bCount + " 白：" + wCount);
	}
}
