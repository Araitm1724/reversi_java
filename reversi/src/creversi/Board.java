package creversi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
	int[][] boardStatus = new int[10][10]; // リバーシ盤
	final int SPACE = 0, BLACK = 1, WHITE = BLACK * -1, WALL = 9; // マスの状態

	final int[][] squareValue = {
			{ 45, -11, 4, -1, -1, 4, -11, 45 },
			{ -11, -16, 1, -3, -3, -1, -16, -11 },
			{ 4, -1, 2, -1, -1, 2, -1, 4 },
			{ -1, -3, -1, 0, 0, -1, -3, -1 },
			{ -1, -3, -1, 0, 0, -1, -3, -1 },
			{ 4, -1, 2, -1, -1, 2, -1, 4 },
			{ -11, -16, 1, -3, -3, -1, -16, -11 },
			{ 45, -11, 4, -1, -1, 4, -11, 45 }
	}; // マスの評価値

	char[] squareStatus = { '　', '●', '○' }; // 表示されるマスの状態

	int y, x, nextY, nextX, furtherY, furtherX, previousY, previousX; // マスを調べる
	List<int[]> validSquares = new ArrayList<int[]>(); // 打てるマスの一覧
	int[] coordinate = new int[2]; // マスの座標

	int[] stoneCount = new int[2]; // 石の数

	Board() {
		for (y = 0; y < boardStatus.length; y++) {
			for (x = 0; x < boardStatus[0].length; x++) {
				if (x == 0 || x == 10 || y == 0 || y == 10) {
					boardStatus[y][x] = 9;
				} else {
					boardStatus[y][x] = 0;
				}
			}
		}

		boardStatus[4][4] = BLACK;
		boardStatus[5][5] = BLACK;
		boardStatus[4][5] = WHITE;
		boardStatus[5][4] = WHITE;
	}

	void drawBoard() {
		System.out.println("  １ ２ ３ ４ ５ ６ ７ ８");

		for (int y = 1; y <= 8; y++) {
			System.out.println(" +ー+ー+ー+ー+ー+ー+ー+ー+");
			System.out.print(y);

			for (int x = 1; x <= 8; x++) {
				switch (boardStatus[y][x]) {
				case SPACE -> System.out.print("|" + squareStatus[0]);
				case BLACK -> System.out.print("|" + squareStatus[1]);
				case WHITE -> System.out.print("|" + squareStatus[2]);
				default -> throw new IllegalArgumentException("Unexpected value: " + boardStatus[y][x]);
				}
			}

			System.out.println("|");
		}

		System.out.println(" +ー+ー+ー+ー+ー+ー+ー+ー+");

		return;
	}

	void countStones() {
		stoneCount[0] = 0;
		stoneCount[1] = 0;

		for (y = 1; y <= 8; y++) {
			for (x = 1; x <= 8; x++) {
				if (boardStatus[y][x] == BLACK) {
					stoneCount[0]++;
				} else if (boardStatus[y][x] == WHITE) {
					stoneCount[1]++;
				}
			}
		}

		return;
	}

	void checkSquares(int stoneColor) {
		validSquares.clear();

		for (y = 1; y <= 8; y++) {
			for (x = 1; x <= 8; x++) {
				if (boardStatus[y][x] == SPACE) {
					for (nextY = -1; nextY <= 1; nextY++) {
						for (nextX = -1; nextX <= 1; nextX++) {
							if (boardStatus[y + nextY][x + nextX] == stoneColor * -1) {
								furtherY = (y + nextY);
								furtherX = (x + nextX);

								while (true) {
									furtherY += nextY;
									furtherX += nextX;

									if (boardStatus[furtherY][furtherX] == stoneColor) {
										coordinate[0] = y;
										coordinate[1] = x;
										validSquares.add(Arrays.copyOf(coordinate, 2));
									} else if (boardStatus[furtherY][furtherX] == (stoneColor * -1)) {
										continue;
									} else {
										break;
									}
								}
							}
						}
					}
				}
			}
		}

		return;
	}

	void reverseStone(int[] moveSquare, int stoneColor) {
		for (nextY = -1; nextY <= 1; nextY++) {
			for (nextX = -1; nextX <= 1; nextX++) {
				if (boardStatus[moveSquare[0] + nextY][moveSquare[1] + nextX] == stoneColor * -1) {
					furtherY = (moveSquare[0] + nextY);
					furtherX = (moveSquare[1] + nextX);

					while (true) {
						furtherY += nextY;
						furtherX += nextX;

						if (boardStatus[furtherY][furtherX] == stoneColor) {
							previousY = furtherY;
							previousX = furtherX;

							while (previousY != moveSquare[0] || previousX != moveSquare[1]) {
								previousY += (nextY * -1);
								previousX += (nextX * -1);

								boardStatus[previousY][previousX] = stoneColor;
							}
						} else if (boardStatus[furtherY][furtherX] == (stoneColor * -1)) {
							continue;
						} else {
							break;
						}
					}
				}
			}
		}

		boardStatus[moveSquare[0]][moveSquare[1]] = stoneColor;

		return;
	}
}
